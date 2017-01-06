## Springboot-jwt-demo

##About
Springboot-jwt-demo is a demonstration of a completely stateless and RESTful token-based authorization system using JSON Web Tokens (JWT) and Springboot.

##Requirements
Requires Maven and Java 1.7 or greater.

##Usage
To use start Springboot-jwt-demo, run in the terminal `mvn spring-boot:run`. Springboot-jwt-demo will now be running at `http://localhost:8080`

There are two built-in user accounts to demonstrate the differing levels of access to the endpoints:
```
User - user:123
Admin - admin:123
```
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
 ├──src/                       * our source files 
 |   ├──main.browser.ts        * our entry file for our browser environment
 │   │
 |   ├──index.html             * Index.html: where we generate our index page
 │   │
 |   ├──polyfills.ts           * our polyfills file
 │   │
 |   ├──vendor.browser.ts      * our vendor file
 │   │
 │   ├──app/                   * WebApp: folder
 │   │   ├──app.spec.ts        * a simple test of components in app.ts
 │   │   ├──app.e2e.ts         * a simple end-to-end test for /
 │   │   └──app.ts             * App.ts: a simple version of our App component components
 │   │
 │   └──assets/                * static assets are served here
 │       ├──icon/              * our list of icons from www.favicon-generator.org
 │       ├──service-worker.js  * ignore this. Web App service worker that's not complete yet
 │       ├──robots.txt         * for search engines to crawl your website
 │       └──humans.txt          * for humans to know who the developers are
 │
 │
 ├──tslint.json                * typescript lint config
 ├──typedoc.json               * typescript documentation generator
 ├──tsconfig.json              * config that webpack uses for typescript
 ├──package.json               * what npm uses to manage it's dependencies
 └──webpack.config.js          * webpack main configuration file

```

JWT Tokens are configured to expire after 10 minutes, you can get a new token by sign in again. They are saved in a http-only cookie instead of localstorage thi will prevent csrf attacks

The frontend code is based on [Spring Security and Angular JS](https://spring.io/guides/tutorials/spring-security-and-angular-js/)

[Stormpath article](https://stormpath.com/blog/token-auth-spa)

The project is heavyly inspired by [Cerberus](https://github.com/brahalla/Cerberus).
