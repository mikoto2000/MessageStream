import { useState, useEffect } from 'react';
import { BlueskyControllerApi } from '../api';
import type { BlueskyService } from '../api/api';
import { createConfig } from '../ApiConfig';

type BlueskyRegisterPageProps = {
  accessToken: string;
};

export const BlueskyRegisterPage: React.FC<BlueskyRegisterPageProps> = ({ accessToken }) => {
  const [instanceUrl, setInstanceUrl] = useState('');
  const [handle, setHandle] = useState('');
  const [password, setPassword] = useState('');
  const [statusMessage, setStatusMessage] = useState<string | null>(null);
  const [instances, setInstances] = useState<BlueskyService[]>([]);

  const handleDelete = async (id: string) => {
    try {
      const api = new BlueskyControllerApi(createConfig(accessToken));
      await api.deleteInstance1({ id });
      const response = await api.getInstances1();
      setInstances(response.data);
    } catch (error) {
      console.error('Failed to delete Bluesky instance', error);
    }
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const api = new BlueskyControllerApi(createConfig(accessToken));
    try {
      await api.addInstance1({ addBlueskyInstanceRequest: { instanceUrl, handle, password } });
      setStatusMessage('登録に成功しました。');
      setInstanceUrl('');
      setHandle('');
      setPassword('');
      api.getInstances1().then(response => {
        setInstances(response.data);
      }).catch(error => {
        console.error('Failed to fetch Bluesky instances', error);
      });
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
    const api = new BlueskyControllerApi(createConfig(accessToken));
    api.getInstances1().then(response => {
      setInstances(response.data);
    }).catch(error => {
      console.error('Failed to fetch Bluesky instances', error);
    });
  }, [accessToken]);

  return (
    <>
      <h2>Bluesky インスタンス登録</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>
            インスタンス URL:
            <input
              type="text"
              value={instanceUrl}
              onChange={(e) => setInstanceUrl(e.target.value)}
              placeholder="https://bsky.social"
              required
            />
          </label>
        </div>
        <div>
          <label>
            ハンドル:
            <input
              type="text"
              value={handle}
              onChange={(e) => setHandle(e.target.value)}
              placeholder="example.bsky.social"
              required
            />
          </label>
        </div>
        <div>
          <label>
            パスワード:
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </label>
        </div>
        <button type="submit">登録</button>
      </form>
      {statusMessage && <p>{statusMessage}</p>}
      <p>
        Bluesky のアプリケーションパスワードは以下の手順で取得してください:
      </p>
      <ol>
        <li>ブラウザで Bluesky のウェブサイト (bsky.app) にアクセスし、ログイン</li>
        <li>右上のプロフィールアイコンをクリックし、「設定とプライバシー」→「開発者設定」→「アプリケーションパスワード」を開く</li>
        <li>「新規アプリケーションパスワードを作成」を選択し、アプリ名を入力して作成</li>
        <li>表示されたパスワードをコピーし、以下のパスワード欄に貼り付ける</li>
      </ol>
      <h3>登録済みインスタンス一覧</h3>
      {instances.length === 0 ? (
        <p>登録されたインスタンスはありません。</p>
      ) : (
        <ul>
          {instances.map(inst => (
            <li key={inst.id}>
              {inst.url} ({inst.handle}){' '}
              <button onClick={() => inst.id && handleDelete(inst.id)}>削除</button>
            </li>
          ))}
        </ul>
      )}
    </>
  );
};

export default BlueskyRegisterPage;