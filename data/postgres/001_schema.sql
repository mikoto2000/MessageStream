-- Message Stream 汎用SNSログイン・認証情報付き スキーマ

-- ユーザー（OIDCのsubと紐づけ用途）
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    subject VARCHAR(128) NOT NULL UNIQUE, -- OIDC sub
    display_name VARCHAR(64)
);

-- サービス種別 (例: Mastodon, Bluesky, Gmail)
CREATE TABLE IF NOT EXISTS services (
    id SERIAL PRIMARY KEY,
    service_name VARCHAR(32) NOT NULL UNIQUE
);

-- サービスごとのユーザーアカウント
CREATE TABLE IF NOT EXISTS accounts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    service_id INTEGER NOT NULL REFERENCES services(id) ON DELETE CASCADE,
    account_identifier VARCHAR(128) NOT NULL, -- サービス側IDやメールアドレス等
    last_message_timestamp TIMESTAMP WITH TIME ZONE, -- 重複取得防止(最終取得メッセージ時刻)
    UNIQUE(user_id, service_id, account_identifier)
);

-- 外部サービスAPI認証やログイン情報（認可トークン, Cookie等格納可、他SNSも拡張容易な汎用設計）
CREATE TABLE IF NOT EXISTS user_service_credentials (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    service_id INTEGER NOT NULL REFERENCES services(id) ON DELETE CASCADE,
    account_id INTEGER REFERENCES accounts(id) ON DELETE CASCADE,
    access_token TEXT,
    refresh_token TEXT,
    expires_at TIMESTAMP WITH TIME ZONE,
    extra_data JSONB, -- スキーマに収まらないAPIレスポンス等を保持
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, service_id)
);