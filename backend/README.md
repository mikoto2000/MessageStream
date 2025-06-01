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

## 環境変数

以下の環境変数で設定を上書きできます。指定しない場合はデフォルト値が使用されます。

| プロパティ | 環境変数名 | デフォルト値 |
|---|---|---|
| spring.datasource.url | SPRING_DATASOURCE_URL | jdbc:postgresql://postgres:5432/public |
| spring.datasource.username | SPRING_DATASOURCE_USERNAME | admin |
| spring.datasource.password | SPRING_DATASOURCE_PASSWORD | password |
| spring.datasource.driver-class-name | SPRING_DATASOURCE_DRIVER_CLASS_NAME | org.postgresql.Driver |
| spring.jpa.hibernate.ddl-auto | SPRING_JPA_HIBERNATE_DDL_AUTO | update |
| spring.jpa.show-sql | SPRING_JPA_SHOW_SQL | true |
| spring.jpa.database-platform | SPRING_JPA_DATABASE_PLATFORM | org.hibernate.dialect.PostgreSQLDialect |
| spring.security.oauth2.resourceserver.jwt.issuer-uri | SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI | http://keycloak:8080/realms/myrealm |
| server.port | SERVER_PORT | 8081 |
| logging.level.org.springframework | LOGGING_LEVEL_ORG_SPRINGFRAMEWORK | INFO |
| logging.level.org.hibernate.SQL | LOGGING_LEVEL_ORG_HIBERNATE_SQL | DEBUG |
| messagestream.cors.allowed-origin-patterns | MESSAGESTREAM_CORS_ALLOWED_ORIGIN_PATTERNS | * |
| messagestream.cors.allowed-methods | MESSAGESTREAM_CORS_ALLOWED_METHODS | * |
| messagestream.cors.allowed-headers | MESSAGESTREAM_CORS_ALLOWED_HEADERS | * |
| messagestream.cors.allow-credentials | MESSAGESTREAM_CORS_ALLOW_CREDENTIALS | true |
| messagestream.cors.mapping-path | MESSAGESTREAM_CORS_MAPPING_PATH | /** |

## ディレクトリ構成

src/main/java/dev/mikoto2000/messagestream

## ライセンス
MIT