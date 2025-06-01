import { useEffect, useState } from 'react';

import { BlueskyControllerApi, MastodonControllerApi, type Message } from '../api';

import { createConfig } from '../ApiConfig';

type TimelinePageProps = {
  user: string | undefined;
  accessToken: string | undefined;
};

export const TimelinePage: React.FC<TimelinePageProps> = ({ user, accessToken }) => {

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
  }, [accessToken]);

  const fetchMessages = () => {
    if (accessToken) {
      const blueskyApi = new BlueskyControllerApi(createConfig(accessToken));
      const mastodonApi = new MastodonControllerApi(createConfig(accessToken));
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
  return (
    <>
      <h2>{user} timeline</h2>
      <ul>
        {messages.map((message, index) => (
          <li key={index}>
            <div style={{ display: "flex", flexDirection: "row" }}>
              <div>
                <div><img src={message.iconUrl} style={{ width: "32px", height: "32px" }} /><strong>{message.poster}</strong> on {message.serviceName}</div>
                <div>{message.text}</div>
                <div><a href={message.link} ><small>Posted at: {message.postedAt}</small></a></div>
              </div>
              <div>
                {
                  message.thumbnailUrls && message.imageUrls ?
                    message.thumbnailUrls.map((thumbnailUrl, imageIndex) => (
                      <a href={message.imageUrls[imageIndex]} target="_brank"><img key={imageIndex} src={thumbnailUrl} style={{ width: "64px", height: "64px" }} /></a>
                    ))
                  : <></>
                }
              </div>
            </div>
          </li>
        ))}
      </ul>
      <div>
        {
          import.meta.env.DEV
            ?
            <pre style={{ width: "100%", whiteSpace: "pre-wrap", wordBreak: "break-all" }}>
              {JSON.stringify(messages, null, 2)}
            </pre>
            :
            <></>
        }
      </div>
    </>
  )
}

export default TimelinePage;
