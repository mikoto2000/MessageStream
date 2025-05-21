
# Message Stream

## 概要

**Message Stream** は、メールや各種 SNS のメッセージをひとつのタイムラインに流す Web アプリケーションです。


## 特徴

- Gmail, Mastodon, Bluesky 等のメッセージを統合表示


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

### フロントエンド

T.B.D.


## ライセンス

MIT

