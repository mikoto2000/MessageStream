import { useEffect, useState } from 'react'

import { useAuth } from "react-oidc-context";

import { BlueskyControllerApi, MastodonControllerApi, type Message } from './api';

import './App.css'
import { createConfig } from './ApiConfig';

function App() {
  const auth = useAuth();

  const [messages, setMessages] = useState<Message[]>([])

  var initialized = false;
  useEffect(() => {
    if (!initialized) {
      initialized = true;
      fetchMessages();
      const interval = setInterval(fetchMessages, 5 * 60 * 1000);
      return () => {
        clearInterval(interval);
      };
    }
  }, [auth]);

  const fetchMessages = () => {
    if (auth?.user?.access_token) {
      const blueskyApi = new BlueskyControllerApi(createConfig(auth?.user?.access_token));
      const mastodonApi = new MastodonControllerApi(createConfig(auth?.user?.access_token));
      (async () => {
        const blueskyMessages = await blueskyApi.getHomeTimeline1();
        const mastodonMessages = await mastodonApi.getPublicTimeline();
        const mixedMessages = [...blueskyMessages.data, ...mastodonMessages.data].sort(postSortFunc);
        setMessages(mixedMessages);
      })()
    }
  }

  const postSortFunc = (a: Message, b: Message) => {
    if (a.postedAt && b.postedAt) {
      return Date.parse(b.postedAt) - Date.parse(a.postedAt)
    } else {
      return 0;
    }
  }

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
        <h1>Welcome, {auth.user?.profile.name}!</h1>
        <h2>Messages</h2>
        <ul>
          {messages.map((message, index) => (
            <li key={index}>
              <div>
                <div><img src={message.iconUrl} style={{ width: "32px", height: "32px" }} /><strong>{message.poster}</strong> on {message.serviceName}</div>
                <div>{message.text}</div>
                <div><a href={message.link} ><small>Posted at: {message.postedAt}</small></a></div>
              </div>
            </li>
          ))}
        </ul>
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
        <div>
          {
            import.meta.env.DEV
            ?
              <pre>
                {JSON.stringify(messages, null, 2)}
              </pre>
            :
              <></>
          }
        </div>
      </>
    )
  }
}

export default App
