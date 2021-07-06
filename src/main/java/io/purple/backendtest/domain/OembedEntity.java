package io.purple.backendtest.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OembedEntity {

    private String title;
    private String type;
    private String version;
    private String providerName;
    private String providerUrl;
    private String authorName;
    private String authorUrl;
    private int isPlus;
    private String html;
    private int width;
    private int height;
    private Long duration;
    private String description;
    private String thumbnailUrl;
    private int thumbnailWidth;
    private int thumbnailHeight;
    private String thumbnailUrlWithPlayButton;
    private String uploadDate;
    private Long videoId;
    private String uri;
}
