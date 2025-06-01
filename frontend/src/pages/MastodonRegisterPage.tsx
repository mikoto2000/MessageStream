import { useState, useEffect } from 'react';
import { MastodonControllerApi } from '../api';
import type { MastodonService } from '../api/api';
import { createConfig } from '../ApiConfig';

type MastodonRegisterPageProps = {
  accessToken: string;
};

export const MastodonRegisterPage: React.FC<MastodonRegisterPageProps> = ({ accessToken }) => {
  const [instanceUrl, setInstanceUrl] = useState('');
  const [mastodonAccessToken, setMastodonAccessToken] = useState('');
  const [statusMessage, setStatusMessage] = useState<string | null>(null);
  const [instances, setInstances] = useState<MastodonService[]>([]);

  const handleDelete = async (id: string) => {
    try {
      const api = new MastodonControllerApi(createConfig(accessToken));
      await api.deleteInstance({ id });
      const response = await api.getInstances();
      setInstances(response.data);
    } catch (error) {
      console.error('Failed to delete Mastodon instance', error);
    }
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const api = new MastodonControllerApi(createConfig(accessToken));
    try {
      await api.addInstance({ addMastodonInstanceRequest: { instanceUrl, accessToken: mastodonAccessToken } });
      setStatusMessage('登録に成功しました。');
      setInstanceUrl('');
      setMastodonAccessToken('');
      api.getInstances()
        .then(response => setInstances(response.data))
        .catch(error => console.error('Failed to fetch Mastodon instances', error));
    } catch (error: unknown) {
      console.error(error);
      if (error instanceof Error) {
        setStatusMessage(`エラー: ${error.message}`);
      } else {
        setStatusMessage(`エラー: ${String(error)}`);
      }
    }
  };

  useEffect(() => {
    const api = new MastodonControllerApi(createConfig(accessToken));
    api.getInstances()
      .then(response => setInstances(response.data))
      .catch(error => console.error('Failed to fetch Mastodon instances', error));
  }, [accessToken]);

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
      <h3>登録済みインスタンス一覧</h3>
      {instances.length === 0 ? (
        <p>登録されたインスタンスはありません。</p>
      ) : (
        <ul>
          {instances.map(inst => (
            <li key={inst.id}>
              {inst.url}{' '}
              <button onClick={() => inst.id && handleDelete(inst.id)}>削除</button>
            </li>
          ))}
        </ul>
      )}
    </>
  );
};

export default MastodonRegisterPage;