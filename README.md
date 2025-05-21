
# Message Stream

## 概要
**Message Stream** とは、メールや各種 SNS のメッセージをひとつのタイムラインに流す Web アプリケーションです。

## 特徴
- Gmail, Mastodon, Bluesky 等のメッセージを統合表示
- 認証に Keycloak を用いた OIDC 対応
- DDD / Clean Architecture に基づく設計

## サブプロジェクト構成

```
.
├── backend       # Spring Boot バックエンド
├── data          # DB 用データ (docker 用)
├── compose.yaml  # 開発用 docker compose
```

### Backend
- Java 21, Spring Boot 3.4.4, Spring Security, Spring Data REST, Lombok etc.
- ディレクトリ: `/backend`

### Frontend (将来追加予定)
- Vite, TypeScript, React, react-oidc-context etc.
- ディレクトリ: `/frontend` (未実装)

## セットアップ・開発

### 開発用コンテナ起動
```sh
docker compose -f compose.yaml up
```

### バックエンド起動
```sh
cd backend
./mvnw clean install
./mvnw spring-boot:run
```

### (フロントエンドは未実装)

## 認証
Keycloak (OpenID Connect) による認証を使用。

## 各種サービス連携
- Mastodon
- Bluesky
- Gmail

## 設計指標
- DDD (ドメイン駆動設計)
- クリーンアーキテクチャ
- Google Java/TypeScript Style Guide 準拠

## ライセンス
MIT
