import { Fragment, type ReactNode } from 'react';

/**
 * Converts URLs in text into an array of React nodes with clickable links.
 *
 * @param text Plain text possibly containing URLs.
 * @returns Array of ReactNode where URLs are replaced by <a> elements.
 */
export function linkifyText(text: string): ReactNode[] {
  const urlRegex = /(https?:\/\/[^\s]+)/g;
  const parts = text.split(urlRegex);
  return parts.map((part, index) => {
    if (urlRegex.test(part)) {
      return (
        <a
          key={index}
          href={part}
          target="_blank"
          rel="noopener noreferrer"
        >
          {part}
        </a>
      );
    }
    return <Fragment key={index}>{part}</Fragment>;
  });
}