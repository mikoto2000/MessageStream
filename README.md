
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

```bash
cd frontend
cp .env.example .env.local
npm install
npm run dev
```

フロントエンドは Vite を使った React アプリケーションです。.env.example を参照して `.env.local` を作成し、OIDC 設定用の環境変数を設定してください:

```dotenv
VITE_OIDC_AUTHORITY=
VITE_OIDC_CLIENT_ID=
VITE_OIDC_CLIENT_SECRET=
VITE_OIDC_REDIRECT_URI=
```


## ライセンス

MIT

