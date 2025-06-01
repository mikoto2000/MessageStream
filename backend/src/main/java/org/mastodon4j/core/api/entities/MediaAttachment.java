package org.mastodon4j.core.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representation of a media attachment in a Mastodon status.
 *
 * @param id         the attachment ID
 * @param type       the media type (e.g., "image", "video")
 * @param url        the full URL to the media
 * @param previewUrl the preview thumbnail URL
 * @param remoteUrl  the original remote URL, if any
 * @param textUrl    the URL to be inserted into status text
 */
public record MediaAttachment(
    String id,
    String type,
    String url,
    @JsonProperty("preview_url") String previewUrl,
    @JsonProperty("remote_url") String remoteUrl,
    @JsonProperty("text_url") String textUrl) {
}