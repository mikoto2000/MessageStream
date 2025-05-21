# Message Stream (Backend)

## 概要
Message Stream とは、メールや各種 SNS のメッセージをひとつのタイムラインに流す Web アプリケーションのバックエンドです。

## 技術スタック
- Java 21
- Spring Boot 3.4.4
- Spring Security, Spring MVC, Spring Validation, Spring Data REST, Lombok
- DDD / Clean Architecture

## セットアップ（ローカル）

1. 依存関係インストール:
   ```sh
   ./mvnw clean install
   ```

2. Spring Boot アプリケーション起動:
   ```sh
   ./mvnw spring-boot:run
   ```

## ディレクトリ構成

src/main/java/dev/mikoto2000/messagestream

## ライセンス
MIT