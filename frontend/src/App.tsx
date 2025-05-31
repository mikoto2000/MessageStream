import { useEffect, useState } from 'react'

import { useAuth } from "react-oidc-context";

import { BlueskyControllerApi, Configuration, MastodonControllerApi, type Message } from './api';

import './App.css'
import { createConfig } from './ApiConfig';

function App() {
  const auth = useAuth();

  const [messages, setMessages] = useState<Message[]>([])

  useEffect(() => {
    if (auth?.user?.access_token) {
      const blueskyApi = new BlueskyControllerApi(createConfig(auth?.user?.access_token));
      const mastodonApi = new MastodonControllerApi(createConfig(auth?.user?.access_token));
      (async () => {
        const blueskyMessages = await blueskyApi.getHomeTimeline1();
        const mastodonMessages = await mastodonApi.getHomeTimeline();
        setMessages([...blueskyMessages.data, ...mastodonMessages.data]);
      })()
    }
  }, [auth]);

  if (!auth.isAuthenticated) {
    return <button onClick={() => {
      // ログイン後に元居たページに戻れるように、
      // 今いるパスをセッションストレージに記憶しておく
      sessionStorage.setItem('path', location.pathname);

      // state の削除
      auth.clearStaleState();

      // 認可サーバーにアクセスしてログイン、
      // ログインに成功したら redirect_uri にリダイレクト
      auth.signinRedirect();
    }}>Log in</button>
  }

  if (auth.error) {
    return <div>Oops... {auth.error.message}</div>;
  }

  if (auth.isLoading) {
    return <>認証中</>;
  }

  switch (auth.activeNavigator) {
    case "signinSilent":
      return <div>Signing you in...</div>;
    case "signoutRedirect":
      return <div>Signing you out...</div>;
  }

  if (auth.isLoading) {
    return <div>Loading...</div>;
  }

  if (auth.isAuthenticated) {
    return (
      <>
        <div>
          <pre>
            {JSON.stringify(messages, null, 2)}
          </pre>
        </div>
        <button onClick={() => {
          // セッションストレージ・ローカルストレージからユーザー情報を削除
          auth.removeUser();

          // OIDC 認可サーバーにセッションを破棄するように依頼して、
          // 破棄したら post_logout_redirect_uri にリダイレクトする
          auth.signoutRedirect({
            id_token_hint: auth.user?.id_token,
            post_logout_redirect_uri: "http://localhost:5173"
          });
        }}>Log out</button>
      </>
    )
  }
}

export default App
