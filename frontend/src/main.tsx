import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'

import { AuthProvider } from 'react-oidc-context'

import './index.css'
import App from './App.tsx'

const oidcConfig = {
    authority:"http://keycloak:8080/realms/myrealm",
    client_id: "myrealm-id",
    client_secret: "myrealm-secret",
    redirect_uri: "http://localhost:5173",
    onSigninCallback: () => {
        // セッションストレージから元居たパスを取得セッションストレージ内のデータは削除
        const redirectLocation = sessionStorage.getItem('path');
        sessionStorage.removeItem('path');

        if (redirectLocation) {
            // ローカルストレージにパスがあったらそこにリダイレクト
            window.location.replace(redirectLocation);
        } else {
            // クエリパラメータに色々な情報が載っているので、それを削除する
            window.history.replaceState(
                {},
                document.title,
                window.location.pathname
            );
        }
    }
}

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <AuthProvider {...oidcConfig}>
      <App />
    </AuthProvider>
  </StrictMode>,
)
