[\[中文版\]](README_cn.md)

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

> A Springboot token-based security starter kit featuring [AngularJS](https://angularjs.org/) and [Springboot](https://projects.spring.io/spring-boot/) ([JSON Web Token](https://jwt.io/))

> If you're looking for using Angular 4 for frontend implementation, please checkout [angular-spring-starter](https://github.com/bfwg/angular-spring-starter), a fullstack starter kit featuring [Angular 4](https://angular.io), [Router](https://angular.io/docs/ts/latest/guide/router.html), [Forms](https://angular.io/docs/ts/latest/guide/forms.html),
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


### Quick start
**Make sure you have Maven and Java 1.7 or greater**

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

# the app will be running on port 8080
# there are two built-in user accounts to demonstrate the differing levels of access to the endpoints:
# - User - user:123
# - Admin - admin:123
```


### File Structure
```
springboot-jwt-starter/
 ├──src/                                                        * our source files
 │   ├──main
 │   │   ├──java.com.bfwg
 │   │   │   ├──config
 │   │   │   │   └──WebSecurityConfig.java                      * config file for filter, custom userSerivce etc.
 │   │   │   ├──model
 │   │   │   │   ├──Authority.java
 │   │   │   │   ├──UserTokenState.java                         * JWT model
 │   │   │   │   └──User.java                                   * our main User model.
 │   │   │   ├──repository                                      * repositories folder for accessing database
 │   │   │   │   └──UserRepository.java
 │   │   │   ├──rest                                            * rest endpoint folder
 │   │   │   │   ├──AuthenticationController.java               * auth related REST controller, refresh token endpoint etc.
 │   │   │   │   └──UserController.java                         * REST controller to handle User related requests
 │   │   │   ├──security                                        * Security related folder(JWT, filters)
 │   │   │   │   ├──auth
 │   │   │   │   │   ├──JwtAuthenticationRequest.java           * login request object, contains username and password
 │   │   │   │   │   ├──RestAuthenticationEntryPoint.java       * handle auth exceptions, like invalid token etc.
 │   │   │   │   │   ├──TokenAuthenticationFilter.java          * the JWT token filter, configured in WebSecurityConfig
 │   │   │   │   │   └──TokenBasedAuthentication.java           * this is our custom Authentication class and it extends AbstractAuthenticationToken.
 │   │   │   │   └──TokenHelper.java                             * token helper class
 │   │   │   ├──service
 │   │   │   │   ├──impl
 │   │   │   │   │   ├──CustomUserDetailsService.java           * custom UserDatilsService implementataion, tells formLogin() where to check username/password
 │   │   │   │   │   └──UserServiceImpl.java
 │   │   │   │   └──UserService.java
 │   │   │   └──Application.java                                * Application main enterance
 │   │   └──recources
 │   │       ├──static                                          * static assets are served here(Angular and html templates)
 │   │       ├──application.yml                                 * application variables are configured here
 │   │       └──import.sql                                      * h2 database query(table creation)
 │   └──test                                                    * Junit test folder
 └──pom.xml                                                     * what maven uses to manage it's dependencies
```
# Table of Contents
* [File Structure](#file-structure)
* [Configuration](#configuration)
* [JSON Web Token](#json-web-token)

### Configuration
- **WebSecurityConfig.java**: The server-side authentication configurations.
- **application.yml**: Application level properties i.e the token expire time, token secret etc. You can find a reference of all application properties [here](http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html).
- **JWT token TTL**: JWT Tokens are configured to expire after 10 minutes, you can get a new token by signing in again.
- **Using a different database**: This Starter kit is using an embedded H2 database that is automatically configured by Spring Boot. If you want to connect to another database you have to specify the connection in the *application.yml* in the resource directory. Here is an example for a MySQL DB:

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

*Hint: For other databases like MySQL sequences don't work for ID generation. So you have to change the GenerationType in the entity beans to 'AUTO' or 'IDENTITY'.*

### JSON Web Token
> JSON Web Tokens are an open, industry standard RFC 7519 method for representing claims securely between two parties.
for more info, checkout https://jwt.io/

### Contributing
I'll accept pretty much everything so feel free to open a Pull-Request


This project is inspried by
- [Cerberus](https://github.com/brahalla/Cerberus)
- [jwt-spring-security-demo](https://github.com/szerhusenBC/jwt-spring-security-demo)

___

# License
 [MIT](/LICENSE)

