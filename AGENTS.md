# Message Stream

## 概要

Message Stream とは、メールや、各種 SNS のメッセージをひとつのタイムラインに流す Web アプリケーションです。



## バックエンド

### ソフトウェアスタック

- Spring Boot 3.4.4
- Maven
- Java 21
- Spring Security
- Spring MVC
- Spring Validation
- Spring Data REST
- Lombok

認証は、 Keycloak を使用した OpenID Connect で行う。


### プロジェクト情報

- group id: dev.mikoto2000
- artifact id: messagestream
- name: Message Stream
- description: Message Stream とは、メールや、各種 SNS のメッセージをひとつのタイムラインに流す Web アプリケーションです。
- package name: dev.mikoto2000.messagestream
- packaging: jar


### 設計指標

- DDD
- Clean Architecture
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)


## フロントエンド

### ソフトウェアスタック

- Vite
- TypeScript
- React
- Vitest
- react-oidc-context

認証は、 Keycloak を使用した OpenID Connect で行う。


### プロジェクト情報

- package name: message-stream
- description: Message Stream とは、メールや、各種 SNS のメッセージをひとつのタイムラインに流す Web アプリケーションです。
- entry point: index.js
- test command: vitest
- author: mikoto2000
- license: MIT


### 設計指標

- [Google TypeScript Style Guide](https://google.github.io/styleguide/tsguide.html)


## 対応メッセージ

- Mastodon
- Bluesky
- Gmail


