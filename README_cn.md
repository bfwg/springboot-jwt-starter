```
                _             _                 _       _          _         _             _
 ___ _ __  _ __(_)_ __   __ _| |__   ___   ___ | |_    (_)_      _| |_   ___| |_ __ _ _ __| |_ ___ _ __
/ __| '_ \| '__| | '_ \ / _` | '_ \ / _ \ / _ \| __|   | \ \ /\ / / __| / __| __/ _` | '__| __/ _ \ '__|
\__ \ |_) | |  | | | | | (_| | |_) | (_) | (_) | |_    | |\ V  V /| |_  \__ \ || (_| | |  | ||  __/ |
|___/ .__/|_|  |_|_| |_|\__, |_.__/ \___/ \___/ \__|  _/ | \_/\_/  \__| |___/\__\__,_|_|   \__\___|_|
    |_|                 |___/                        |__/
```

[![npm](https://img.shields.io/badge/demo-online-ed1c46.svg)](http://jwt.fanjin.io/)
[![Build Status](https://travis-ci.org/bfwg/springboot-jwt-starter.svg?branch=master)](https://travis-ci.org/bfwg/springboot-jwt-starter)
[![License MIT](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/bfwg/springboot-jwt-starter/blob/master/LICENSE)

> 这是一个启动项目用到[Springboot](https://projects.spring.io/spring-boot/)，[AngularJS](https://angularjs.org/)和([JSON Web Token](https://jwt.io/)).


> 如果你想用Angular4+来做前端，请看这个项目：[angular-spring-starter](https://github.com/bfwg/angular-spring-starter), 这是一个全栈启动项目用到[Angular 4](https://angular.io), [Router](https://angular.io/docs/ts/latest/guide/router.html), [Forms](https://angular.io/docs/ts/latest/guide/forms.html),

[Http](https://angular.io/docs/ts/latest/guide/server-communication.html),
[Services](https://gist.github.com/gdi2290/634101fec1671ee12b3e#_follow_@AngularClass_on_twitter),
[Spring boot](https://projects.spring.io/spring-boot/),
[Json Web Token](https://jwt.io/)


### [Live Demo](http://jwt.fanjin.io)
<p align="center">
    <img width="800" alt="Springboot JWT Starter" src="https://cloud.githubusercontent.com/assets/12819525/24693784/23c8af14-1994-11e7-9984-ebf612f740ec.png">
</p>

> Authentication is the most common scenario for using JWT. Once the user is logged in, each subsequent request will include the JWT, allowing the user to access routes, services, and resources that are permitted with that token. Single Sign On is a feature that widely uses JWT nowadays, because of its small overhead and its ability to be easily used across different domains.

> -- <cite>Auth0</cite>


### 快速开始
**要用Maven跟Java 1.7或更高哦**

```bash
# clone our repo
# --depth 1 removes all but one .git commit history
git clone --depth 1 https://github.com/bfwg/springboot-jwt-starter.git

# change directory to our repo
cd springboot-jwt-starter

# install the repo with mvn
mvn install

# start the server
mvn spring-boot:run

# 后端Spring-boot会跑在端口 8080
# 有两个账户预设在内存:
# - User - user:123
# - Admin - admin:123
```


### 文件结构
```
springboot-jwt-starter/
 ├──src/                                                        * 源代码
 │   ├──main
 │   │   ├──java.com.bfwg
 │   │   │   ├──common/                                         * 一些简单的帮助class
 │   │   │   ├──config
 │   │   │   │   └──WebSecurityConfig.java                      * 配置文件，用来配置安全以及过滤组件
 │   │   │   ├──model
 │   │   │   │   ├──Authority.java
 │   │   │   │   ├──UserTokenState.java                         * 用来返还给用户的JSON object class
 │   │   │   │   └──User.java                                   * 用户模型 class
 │   │   │   ├──repository                                      * 访问数据库的interface
 │   │   │   ├──rest                                            
 │   │   │   │   ├──AuthenticationController.java               * 用来身份认证，刷新token
 │   │   │   │   └──UserController.java                         * 用来获取用户信息
 │   │   │   ├──security                                        
 │   │   │   │   ├──auth
 │   │   │   │   │   ├──JwtAuthenticationRequest.java           * 登录请求模型
 │   │   │   │   │   ├──RestAuthenticationEntryPoint.java       * 用来应付登录失败的端点
 │   │   │   │   │   ├──TokenAuthenticationFilter.java          * **JWT过滤器**，整个程序要依赖它来识别jwt跟用户。
 │   │   │   │   │   └──TokenBasedAuthentication.java           * 用来帮助JWT身份认证
 │   │   │   │   └──TokenHelper.java                            * 识别Token的帮助库
 │   │   │   ├──service
 │   │   │   │   ├──impl
 │   │   │   │   │   ├──CustomUserDetailsService.java           * 这个class会告诉authenticationManager哪里核对用户登录信息
 │   │   │   │   │   └──UserServiceImpl.java
 │   │   │   │   └──UserService.java
 │   │   │   └──Application.java                                * 入口文件
 │   │   └──recources
 │   │       ├──static                                          * 静态文件夹，所有AngularJS代码都在这里
 │   │       ├──application.yml                                 * 程序配置文件，如果你想添加自己的数据库请在这里配置
 │   │       └──import.sql                                      * 这是一个h2 database query，当程序运转时，这里的用户数据会被load到内存
 │   └──test                                                    * 单元测试
 └──pom.xml                                                     * Maven配置文件
```
# Table of Contents
* [文件结构](#文件结构)
* [配置](#配置)

### 配置
- **WebSecurityConfig.java**: 配置登录过滤器以及其他安全相关的配置。
- **application.yml**: 程序相关配置例如：token过期时间，token的秘密是什么。 在这里可以了解更多：[here](http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html).
- **JWT token TTL**: JWT默认配置是10分钟过期。
- **用别的数据库**: 这个项目是用自动配置的内存H2数据库。如果你想用一个真正的数据库例如PostreSql，MySql，你需要在**application**里配置。例如说如果你要用MySQL：

```
spring:
  jpa:
    hibernate:
      # possible values: validate | update | create | create-drop
      ddl-auto: create-drop
  datasource:
    url: jdbc:mysql://localhost/myDatabase
    username: myUser
    password: myPassword
    driver-class-name: com.mysql.jdbc.Driver
```



___

# License
 [MIT](/LICENSE)

