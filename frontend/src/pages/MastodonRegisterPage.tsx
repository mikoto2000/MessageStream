import { useState } from 'react';
import { MastodonControllerApi } from '../api';
import { createConfig } from '../ApiConfig';

type MastodonRegisterPageProps = {
  accessToken: string;
};

export const MastodonRegisterPage: React.FC<MastodonRegisterPageProps> = ({ accessToken }) => {
  const [instanceUrl, setInstanceUrl] = useState('');
  const [mastodonAccessToken, setMastodonAccessToken] = useState('');
  const [statusMessage, setStatusMessage] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const api = new MastodonControllerApi(createConfig(accessToken));
    try {
      await api.addInstance({ addMastodonInstanceRequest: { instanceUrl, accessToken: mastodonAccessToken } });
      setStatusMessage('登録に成功しました。');
      setInstanceUrl('');
      setMastodonAccessToken('');
    } catch (error: unknown) {
      console.error(error);
      if (error instanceof Error) {
        setStatusMessage(`エラー: ${error.message}`);
      } else {
        setStatusMessage(`エラー: ${String(error)}`);
      }
    }
  };

  return (
    <>
      <h2>Mastodon インスタンス登録</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>
            インスタンス URL:
            <input
              type="text"
              value={instanceUrl}
              onChange={(e) => setInstanceUrl(e.target.value)}
              placeholder="https://social.mikutter.hachune.net"
              required
            />
          </label>
        </div>
        <div>
          <label>
            アクセストークン:
            <input
              type="text"
              value={mastodonAccessToken}
              onChange={(e) => setMastodonAccessToken(e.target.value)}
              required
            />
          </label>
        </div>
        <button type="submit">登録</button>
      </form>
      {statusMessage && <p>{statusMessage}</p>}
      <p>
        Mastodon のアクセストークンは以下の手順で取得してください:
      </p>
      <ol>
        <li>お使いの Mastodon インスタンスにログイン</li>
        <li>「設定」→「開発」→「新しいアプリケーションを作成」を選択</li>
        <li>アプリ名・リダイレクト URI（任意）・必要な権限 (Read / Write など) を設定してアプリを作成</li>
        <li>表示されたアクセストークンをコピーし、以下のアクセストークン欄に貼り付ける</li>
      </ol>
    </>
  );
};

export default MastodonRegisterPage;