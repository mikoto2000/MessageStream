import { useAuth } from "react-oidc-context";
import { Routes, Route, Link } from "react-router-dom";

import './App.css'

import { createConfig } from './ApiConfig';

import TimelinePage from "./pages/TimelinePage";
import BlueskyRegisterPage from "./pages/BlueskyRegisterPage";
import MastodonRegisterPage from "./pages/MastodonRegisterPage";
import { SigninControllerApi } from "./api";

function App() {
  const auth = useAuth();

  if (!auth.isAuthenticated) {
    return <div>
      <h1>Message Stream</h1>
      <button onClick={() => {
        // ログイン後に元居たページに戻れるように、
        // 今いるパスをセッションストレージに記憶しておく
        sessionStorage.setItem('path', location.pathname);

        // state の削除
        auth.clearStaleState();

        // 認可サーバーにアクセスしてログイン、
        // ログインに成功したら redirect_uri にリダイレクト
        auth.signinRedirect();
      }}>Log in</button>
    </div>
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
    const config = createConfig(auth.user?.access_token || "");
    const signinApi = new SigninControllerApi(config)
    signinApi.exists()
      .then((result) => {
        if (!result.data) {
          signinApi.register();
        }
      });

    const accessToken = auth.user?.access_token || "";
    return (
      <>
        <header style={{ display: "flex", justifyContent: "space-between", alignItems: "center", width: "100vw" }}>
          <h1>Message Stream</h1>
          <nav>
            <Link to="/">Timeline</Link>{" | "}
            <Link to="/bluesky">Bluesky インスタンス登録</Link>{" | "}
            <Link to="/mastodon">Mastodon インスタンス登録</Link>{" | "}
            <button onClick={() => {
              // セッションストレージ・ローカルストレージからユーザー情報を削除
              auth.removeUser();

              // OIDC 認可サーバーにセッションを破棄するように依頼して、
              // 破棄したら post_logout_redirect_uri にリダイレクトする
              auth.signoutRedirect({
                id_token_hint: auth.user?.id_token,
                post_logout_redirect_uri: window.location.origin
              });
            }}>Log out</button>
          </nav>
        </header>
        <main style={{ padding: "1rem" }}>
          <Routes>
            <Route path="/" element={<TimelinePage user={auth.user?.profile?.name} accessToken={accessToken} />} />
            <Route path="/bluesky" element={<BlueskyRegisterPage accessToken={accessToken} />} />
            <Route path="/mastodon" element={<MastodonRegisterPage accessToken={accessToken} />} />
          </Routes>
        </main>
      </>
    );
  }
}

export default App
