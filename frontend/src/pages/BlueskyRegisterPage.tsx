import { useState } from 'react';
import { BlueskyControllerApi } from '../api';
import { createConfig } from '../ApiConfig';

type BlueskyRegisterPageProps = {
  accessToken: string;
};

export const BlueskyRegisterPage: React.FC<BlueskyRegisterPageProps> = ({ accessToken }) => {
  const [instanceUrl, setInstanceUrl] = useState('');
  const [handle, setHandle] = useState('');
  const [password, setPassword] = useState('');
  const [statusMessage, setStatusMessage] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const api = new BlueskyControllerApi(createConfig(accessToken));
    try {
      await api.addInstance1({ addInstanceRequest: { instanceUrl, handle, password } });
      setStatusMessage('登録に成功しました。');
      setInstanceUrl('');
      setHandle('');
      setPassword('');
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
      <h2>Bluesky インスタンス登録</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>
            インスタンス URL:
            <input
              type="text"
              value={instanceUrl}
              onChange={(e) => setInstanceUrl(e.target.value)}
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
    </>
  );
};

export default BlueskyRegisterPage;