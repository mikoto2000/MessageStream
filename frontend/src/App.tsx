import { useEffect, useState } from 'react'

import { useAuth } from "react-oidc-context";

import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  const auth = useAuth();

  const [count, setCount] = useState(0)

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

  if (auth.isLoading || auth.error) {
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

  if (auth.error) {
    return <div>Oops... {auth.error.message}</div>;
  }

  if (auth.isAuthenticated) {
    return (
      <>
        <div>
          <a href="https://vite.dev" target="_blank">
            <img src={viteLogo} className="logo" alt="Vite logo" />
          </a>
          <a href="https://react.dev" target="_blank">
            <img src={reactLogo} className="logo react" alt="React logo" />
          </a>
        </div>
        <h1>Vite + React</h1>
        <div className="card">
          <button onClick={() => setCount((count) => count + 1)}>
            count is {count}
          </button>
          <p>
            Edit <code>src/App.tsx</code> and save to test HMR
          </p>
        </div>
        <p className="read-the-docs">
          Click on the Vite and React logos to learn more
        </p>
      </>
    )
  }
}

export default App
