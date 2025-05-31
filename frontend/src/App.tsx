import { useAuth } from "react-oidc-context";

import './App.css'
import TimelinePage from "./pages/TimelinePage";

function App() {
  const auth = useAuth();

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
        <TimelinePage
          user={auth?.user?.profile?.name}
          accessToken={auth.user?.access_token || ""}
        />
      </>
    )
  }
}

export default App
