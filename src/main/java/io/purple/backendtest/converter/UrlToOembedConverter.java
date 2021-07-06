package io.purple.backendtest.converter;

import io.purple.backendtest.domain.OembedEntity;
import io.purple.backendtest.exception.NotUrlTypeException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.regex.Pattern;


@Component
@RequiredArgsConstructor
public class UrlToOembedConverter implements Converter<String, OembedEntity> {

    private final ModelMapper modelMapper;
    private final MessageSource messageSource;
    private Map<String, Object> result;

    @Value("${api.oembed.youtube}")
    private String YOUTUBE_API_URL;

    @Value("${api.oembed.instagram}")
    private String INSTAGRAM_API_URL;

    @Value("${api.oembed.twitter}")
    private String TWITTER_API_URL;

    @Value("${api.oembed.vimeo}")
    private String VIMEO_API_URL;


    private final String YOUTUBE = "youtube";
    private final String INSTAGRAM = "instagram";
    private final String TWITTER = "twitter";
    private final String VIMEO = "vimeo";

    private final RestTemplate restTemplate = new RestTemplate();


    @Override
    public OembedEntity convert(String source) {

        String domain = "";
        try {
            domain = getDomain(source);
        } catch (URISyntaxException e) {
            throw new NotUrlTypeException(messageSource.getMessage("wrong.url", new Object[]{source}, null, null));
        }


        switch (domain) {
            case YOUTUBE:
                return convertYoutubeOembed(source);
            case INSTAGRAM:
                return convertInstagramOembed(source);
            case TWITTER:
                return convertTwitterOembed(source);
            case VIMEO:
                return convertVimeoOembed(source);
        }


        return new OembedEntity();
    }

    private OembedEntity convertVimeoOembed(String url) {
        if (!isVimeoUrl(url)) {
            throw new NotUrlTypeException(messageSource.getMessage("wrong.vimeo.url", new Object[]{url}, null, null));
        }
        getOembedResult(url, VIMEO_API_URL);
        return modelMapper.map(result, OembedEntity.class);
    }

    private boolean isVimeoUrl(String url) {
        return url.startsWith("https://vimeo.com");
    }

    private OembedEntity convertTwitterOembed(String url) {
        if (!isTwitterUrl(url)) {
            throw new NotUrlTypeException(messageSource.getMessage("wrong.twitter.url", new Object[]{url}, null, null));
        }
        getOembedResult(url, TWITTER_API_URL);
        return modelMapper.map(result, OembedEntity.class);
    }

    private boolean isTwitterUrl(String url) {
        return Pattern.compile("(https://twitter.com/.*/status/.*?)").matcher(url).find();
    }

    private OembedEntity convertInstagramOembed(String url) {
        if (!isInstagramUrl(url)) {
            throw new NotUrlTypeException(messageSource.getMessage("wrong.instagram.url", new Object[]{url}, null, null));
        }
        getOembedResult(url, INSTAGRAM_API_URL);
        return modelMapper.map(result, OembedEntity.class);
    }

    private OembedEntity convertYoutubeOembed(String url) {
        if (!isYoutubeUrl(url)) {
            throw new NotUrlTypeException(messageSource.getMessage("wrong.youtube.url", new Object[]{url}, null, null));
        }
        getOembedResult(url, YOUTUBE_API_URL);
        return modelMapper.map(result, OembedEntity.class);
    }

    private void getOembedResult(String url, String instagram_api_url) {
        try {
            result = restTemplate.getForObject(String.format(instagram_api_url, url), Map.class);
        } catch (Exception e) {
            throw new NotUrlTypeException(messageSource.getMessage("wrong.url", new Object[]{url}, null, null));
        }
    }


    private String getDomain(String url) throws URISyntaxException {
        if (!isUrlFormat(url)) {
            throw new NotUrlTypeException(messageSource.getMessage("wrong.url", new Object[]{url}, null, null));
        }

        URI uri = new URI(url);
        String domain = uri.getHost();

        if (domain.startsWith("www.")) domain = domain.substring(4);
        if (domain.endsWith(".com")) domain = domain.substring(0, domain.length() - 4);
        else throw new NotUrlTypeException(messageSource.getMessage("wrong.url", new Object[]{url}, null, null));
        return domain;
    }

    private boolean isYoutubeUrl(String url) {
        return url.startsWith("https://www.youtube.com/watch?v=");
    }

    private boolean isInstagramUrl(String url) {
        return Pattern.compile("(https://www.instagram.com/p/.*?)").matcher(url).find();
    }

    private boolean isUrlFormat(String url) {
        return Pattern.compile("^((http|https)://)?(www.)?([a-zA-Z0-9]+)\\.[a-z]+([a-zA-Z0-9.?#]+)?").matcher(url).find();
    }


}
