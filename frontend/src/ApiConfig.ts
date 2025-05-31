import { Configuration } from "./api/configuration";

export const createConfig = (accessToken: string) => {
  return new Configuration({
    basePath: 'http://localhost:8081',
    baseOptions: {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    },
  });
}

