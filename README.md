## Springboot-jwt-demo

##About
Springboot-jwt-demo is a demonstration of a completely stateless and RESTful token-based authorization system using JSON Web Tokens (JWT) and Springboot.

##Requirements
Requires Maven and Java 1.7 or greater.

# Table of Contents
* [File Structure](#file-structure)
* [Getting Started](#getting-started)
    * [Dependencies](#dependencies)
    * [Installing](#installing)
    * [Running the app](#running-the-app)
* [Configuration](#configuration)
* [JSON Web Token](#json-web-token)
```
springboot-security-jwt/
 ├──src/                                                             * our source files 
 |   ├──main                                                         * main application
 │   │   ├──java.com.bfwg                                            * java backend files
 |   │   │   ├──config                                               * our configuration folder
 |   │   │   │   └──WebSecurityConfig.java            
 |   │   │   ├──model                                                * model folder
 |   │   │   │   ├──Authority.java  
 |   │   │   │   ├──CustomUserDetail.java  
 |   │   │   │   └──User.java  
 |   │   │   ├──repository                                           * repositories folder for accessing database
 |   │   │   │   └──UserRepository.java  
 |   │   │   ├──rest                                                 * rest endpoint folder
 |   │   │   │   └──UserController.java  
 |   │   │   ├──security                                             * Security related folder(JWT, filters)
 |   │   │   │   ├──auth
 |   │   │   │   │   ├──AuthenticationFailureHandler.java  
 |   │   │   │   │   ├──AuthenticationSuccessHandler.java  
 |   │   │   │   │   ├──JwtLogoutHandler.java  
 |   │   │   │   │   ├──RestAuthenticationEntryPoint.java  
 |   │   │   │   │   ├──TokenAuthenticationFilter.java  
 |   │   │   │   │   └──TokenBasedAuthentication.java  
 |   │   │   │   └──TokenUtils.java  
 |   │   │   ├──service               
 |   │   │   │   ├──impl
 |   │   │   │   │   ├──CustomUserDetailsService.java  
 |   │   │   │   │   └──UserServiceImpl.java  
 |   │   │   │   └──UserService.java  
 |   │   │   └──SpringbootSecurtiyApplication.java                   * Application main enterance
 |   │   └── recources                                               * static assets are served here(Angular and html templates)
 |   └──test                                                         * Junit test folder
 └──pom.xml                                                          * what maven uses to manage it's dependencies
```

##Usage
To use start Springboot-jwt-demo, run in the terminal `mvn spring-boot:run`. Springboot-jwt-demo will now be running at `http://localhost:8080`

There are two built-in user accounts to demonstrate the differing levels of access to the endpoints:
```
User - user:123
Admin - admin:123
```
JWT Tokens are configured to expire after 10 minutes, you can get a new token by sign in again. They are saved in a http-only cookie instead of localstorage thi will prevent csrf attacks

The frontend code is based on [Spring Security and Angular JS](https://spring.io/guides/tutorials/spring-security-and-angular-js/)

[Stormpath article](https://stormpath.com/blog/token-auth-spa)

The project is heavyly inspired by [Cerberus](https://github.com/brahalla/Cerberus).
