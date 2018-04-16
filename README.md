

[![Build Status](https://travis-ci.org/willedwards/springboot-jwt-starter.svg?branch=master)](https://travis-ci.org/willedwards/springboot-jwt-starter)
### Spring boot + jwt authentication

<p align="center">
    <img width="800" alt="Springboot JWT Starter" src="https://cloud.githubusercontent.com/assets/12819525/24693784/23c8af14-1994-11e7-9984-ebf612f740ec.png">
</p>


> A Springboot token-based security starter kit featuring [AngularJS](https://angularjs.org/) and [Springboot](https://projects.spring.io/spring-boot/) ([JSON Web Token](https://jwt.io/))

> Authentication is the most common scenario for using JWT. Once the user is logged in, each subsequent request will include the JWT, allowing the user to access routes, services, and resources that are permitted with that token. Single Sign On is a feature that widely uses JWT nowadays, because of its small overhead and its ability to be easily used across different domains.

This project extends and improves the original one by adding the following:
1) An endpoint to register a new user
2) A system test to cover off the registration and subsequent login of that user.
3) Refactoring the code into a simpler service
4) Simplifying the database (as this is not about dbs)
5) Production grade ids.


### Quick start
**Make sure you have Maven and Java 1.7 or greater**

```bash
# clone our repo
# --depth 1 removes all but one .git commit history
git clone --depth 1 https://github.com/willedwards/springboot-jwt-starter.git

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


# Table of Contents
* [File Structure](#file-structure)
* [Configuration](#configuration)
* [JSON Web Token](#json-web-token)

### Configuration
- **WebSecurityConfig.java**: The server-side authentication configurations.
- **application.yml**: Application level properties i.e the token expire time, token secret etc. You can find a reference of all application properties [here](http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html).
- **JWT token TTL**: JWT Tokens are configured to expire after 10 minutes, you can get a new token by signing in again.
- **The database** is purposely left to be as simple as possible, because the purpose of this is not to demonstrate DB functionality.


### JSON Web Token
> JSON Web Tokens are an open, industry standard RFC 7519 method for representing claims securely between two parties.
for more info, checkout https://jwt.io/

### Contributing
Happy to accept code that improves this code by adding simplicity along with tests.

### Key classes

A lot of this comes for free via spring provided a few key classes are used.

- The DefaultUserDetails class implements UserDetails, which is used by Springs Authentication mechanism:
 user the UserService to find the matching user in the database, 
 
 ```
 UserService.loadByUsername(), which is overridden by our DefaultUserService to provide the required functionality.
 ```
 
 then check the the header, the token, the jwt, the expiry, and the password.
 
  
 - The config package contains everything required for Spring to wire up the beans. The other classes are pure java.
 - The Application specifically scans only a few packages, for 2 reasons: The start time is faster, and the Spring beans are all co-located.
 - The UserService interface uses the domain object DefaultUserDetails.
 - Entities are bound to the lower layers and do not leak out.
 - Request and Response objects are bound to the Controller layers and do not go deeper, except in the most trivial cases.
 - Converters are all found in one class.
 
 - The System tests cover off the most pertinent parts of the Controllers. 


This project is inspried by
- [spring-boot-jwt-starter](https://github.com/bfwg/springboot-jwt-starter)

___

# License
 [MIT](/LICENSE)

