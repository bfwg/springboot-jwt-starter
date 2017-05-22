```
                _             _                 _       _          _         _             _
 ___ _ __  _ __(_)_ __   __ _| |__   ___   ___ | |_    (_)_      _| |_   ___| |_ __ _ _ __| |_ ___ _ __
/ __| '_ \| '__| | '_ \ / _` | '_ \ / _ \ / _ \| __|   | \ \ /\ / / __| / __| __/ _` | '__| __/ _ \ '__|
\__ \ |_) | |  | | | | | (_| | |_) | (_) | (_) | |_    | |\ V  V /| |_  \__ \ || (_| | |  | ||  __/ |
|___/ .__/|_|  |_|_| |_|\__, |_.__/ \___/ \___/ \__|  _/ | \_/\_/  \__| |___/\__\__,_|_|   \__\___|_|
    |_|                 |___/                        |__/
```
[![Build Status](https://travis-ci.org/bfwg/springboot-jwt-starter.svg?branch=master)](https://travis-ci.org/bfwg/springboot-jwt-starter)
[![License MIT](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/bfwg/springboot-jwt-starter/blob/master/LICENSE)

> A Springboot token-based security starter kit featuring [AngularJS](https://angularjs.org/) and [Springboot](https://projects.spring.io/spring-boot/) ([JSON Web Token](https://jwt.io/))

> If you're looking for using Angular 4 for frontend implementation, please checkout [angular-spring-starter](https://github.com/bfwg/angular-spring-starter), a fullstack starter kit featuring [Angular 4](https://angular.io), [Router](https://angular.io/docs/ts/latest/guide/router.html), [Forms](https://angular.io/docs/ts/latest/guide/forms.html),
[Http](https://angular.io/docs/ts/latest/guide/server-communication.html),
[Services](https://gist.github.com/gdi2290/634101fec1671ee12b3e#_follow_@AngularClass_on_twitter),
[Spring boot](https://projects.spring.io/spring-boot/),
[Json Web Token](https://jwt.io/)

### [Live Demo](http://jwt.fanjin.computer)
<p align="center">
    <img width="800" alt="Springboot JWT Starter" src="https://cloud.githubusercontent.com/assets/12819525/24693784/23c8af14-1994-11e7-9984-ebf612f740ec.png">
</p>

> Token authentication is a more modern approach and is designed solve problems session IDs stored server-side can’t. Using tokens in place of session IDs can lower your server load, streamline permission management, and provide better tools for supporting a distributed or cloud-based infrastructure.
>
> -- <cite>Stormpath</cite>

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
 |   ├──main
 │   │   ├──java.com.bfwg
 |   │   │   ├──config
 |   │   │   │   └──WebSecurityConfig.java                      * config file for filter, custom userSerivce etc.
 |   │   │   ├──model
 |   │   │   │   ├──Authority.java
 |   │   │   │   ├──CustomUserDetail.java                       * custom UserDetail implemtation
 |   │   │   │   └──User.java                                   * our main user model.
 |   │   │   ├──repository                                      * repositories folder for accessing database
 |   │   │   │   └──UserRepository.java
 |   │   │   ├──rest                                            * rest endpoint folder
 |   │   │   │   └──UserController.java                         * REST controller to handle User related requests
 |   │   │   ├──security                                        * Security related folder(JWT, filters)
 |   │   │   │   ├──auth
 |   │   │   │   │   ├──AuthenticationFailureHandler.java       * login fail handler, configrued in WebSecurityConfig
 |   │   │   │   │   ├──AuthenticationSuccessHandler.java       * login success handler, configrued in WebSecurityConfig
 |   │   │   │   │   ├──AnonAuthentication.java                 * It creates Anonymous user authentication object. If the user doesn't have a token, we mark the user as an anonymous visitor.
 |   │   │   │   │   ├──RestAuthenticationEntryPoint.java       * handle auth exceptions, like invalid token etc.
 |   │   │   │   │   ├──TokenAuthenticationFilter.java          * the JWT token filter, configured in WebSecurityConfig
 |   │   │   │   │   └──TokenBasedAuthentication.java           * this is our custom Authentication class and it extends AbstractAuthenticationToken.
 |   │   │   │   └──TokenUtils.java                             * token helper class
 |   │   │   ├──service
 |   │   │   │   ├──impl
 |   │   │   │   │   ├──CustomUserDetailsService.java           * custom UserDatilsService implementataion, tells formLogin() where to check username/password
 |   │   │   │   │   └──UserServiceImpl.java
 |   │   │   │   └──UserService.java
 |   │   │   └──Application.java                                * Application main enterance
 |   │   └──recources
 |   │       ├──static                                          * static assets are served here(Angular and html templates)
 |   │       ├──application.yml                                 * application variables are configured here
 |   │       └──import.sql                                      * h2 database query(table creation)
 |   └──test                                                    * Junit test folder
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

This project is inspried by
- [Stormpath](https://stormpath.com/blog/token-auth-spa)
- [Cerberus](https://github.com/brahalla/Cerberus)
- [jwt-spring-security-demo](https://github.com/szerhusenBC/jwt-spring-security-demo)

___

# License
 [MIT](/LICENSE)

