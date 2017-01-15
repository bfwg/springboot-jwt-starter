```
                _             _                 _       _          _         _             _            
 ___ _ __  _ __(_)_ __   __ _| |__   ___   ___ | |_    (_)_      _| |_   ___| |_ __ _ _ __| |_ ___ _ __ 
/ __| '_ \| '__| | '_ \ / _` | '_ \ / _ \ / _ \| __|   | \ \ /\ / / __| / __| __/ _` | '__| __/ _ \ '__|
\__ \ |_) | |  | | | | | (_| | |_) | (_) | (_) | |_    | |\ V  V /| |_  \__ \ || (_| | |  | ||  __/ |   
|___/ .__/|_|  |_|_| |_|\__, |_.__/ \___/ \___/ \__|  _/ | \_/\_/  \__| |___/\__\__,_|_|   \__\___|_|   
    |_|                 |___/                        |__/                                               
```
> An Springboot security JWT starter kit featuring [AngularJS](https://angularjs.org/) and [Springboot](https://projects.spring.io/spring-boot/) ([JSON Web Token](https://jwt.io/))
<p align="center">
    <img width="800" alt="Springboot JWT Starter" src="https://github.com/bfwg/storehouse/blob/master/springboot-jwt-starter.png?raw=true">
</p>

### Quick start
**Make sure you have Maven and Java 1.7 or greater**

```bash
# clone our repo
# --depth 1 removes all but one .git commit history
git clone --depth 1 https://github.com/bfwg/springboot-security-jwt.git

# change directory to our repo
cd springboot-security-jwt

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
springboot-security-jwt/
 ├──src/                                                             * our source files 
 |   ├──main                                                         
 │   │   ├──java.com.bfwg                                            
 |   │   │   ├──config                                               
 |   │   │   │   └──WebSecurityConfig.java                           * config file for filter, custom userSerivce etc.
 |   │   │   ├──model                                                
 |   │   │   │   ├──Authority.java                                   
 |   │   │   │   ├──CustomUserDetail.java                            * custom UserDetail implemtation
 |   │   │   │   └──User.java                                        * our main user model.
 |   │   │   ├──repository                                           * repositories folder for accessing database
 |   │   │   │   └──UserRepository.java                              
 |   │   │   ├──rest                                                 * rest endpoint folder
 |   │   │   │   └──UserController.java                              * REST controller to handle User related requests
 |   │   │   ├──security                                             * Security related folder(JWT, filters)
 |   │   │   │   ├──auth                                             
 |   │   │   │   │   ├──AuthenticationFailureHandler.java            * login fail handler, configrued in WebSecurityConfig
 |   │   │   │   │   ├──AuthenticationSuccessHandler.java            * login success handler, configrued in WebSecurityConfig
 |   │   │   │   │   ├──JwtLogoutHandler.java                        * logout handler, configrued in WebSecurityConfig
 |   │   │   │   │   ├──RestAuthenticationEntryPoint.java            * handle auth exceptions, like invalid token etc.
 |   │   │   │   │   ├──TokenAuthenticationFilter.java               * the JWT token filter, configured in WebSecurityConfig
 |   │   │   │   │   └──TokenBasedAuthentication.java                * this is our custom Authentication class and it extends AbstractAuthenticationToken.
 |   │   │   │   └──TokenUtils.java                                  * token helper class
 |   │   │   ├──service               
 |   │   │   │   ├──impl
 |   │   │   │   │   ├──CustomUserDetailsService.java                * custom UserDatilsService implementataion, tells formLogin() where to check username/password
 |   │   │   │   │   └──UserServiceImpl.java  
 |   │   │   │   └──UserService.java                                    
 |   │   │   └──Application.java                   * Application main enterance
 |   │   └──recources                                               
 |   │       ├──static                                               * static assets are served here(Angular and html templates)
 |   │       ├──application.yml                                      * application variables are configured here
 |   │       └──import.sql                                           * h2 database query(table creation)
 |   └──test                                                         * Junit test folder
 └──pom.xml                                                          * what maven uses to manage it's dependencies
```
# Table of Contents
* [File Structure](#file-structure)
* [Configuration](#configuration)
* [JSON Web Token](#json-web-token)

### Configuration
**WebSecurityConfig.java**
- handles all the server-side authentication configurations.
**application.yml**
- handles all the application variables i.e the token expire time, token secret etc.
- JWT Tokens are configured to expire after 10 minutes, you can get a new token by sign in again.

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

