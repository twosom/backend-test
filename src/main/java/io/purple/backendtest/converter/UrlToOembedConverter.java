package io.purple.backendtest.converter;

import io.purple.backendtest.domain.OembedEntity;
import io.purple.backendtest.exception.NotUrlTypeException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


@Component
@RequiredArgsConstructor
public class UrlToOembedConverter implements Converter<String, OembedEntity> {

    private final ModelMapper modelMapper;
    private final MessageSource messageSource;

    @Value("${api.oembed.youtube}")
    private String YOUTUBE_API_URL;


    private final String YOUTUBE = "youtube";


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
            // TODO 나머지 기능들도 구현
        }


        return new OembedEntity();
    }

    private OembedEntity convertYoutubeOembed(String url) {
        Map<String, Object> result = new HashMap<>();
        if (!isYoutubeUrl(url)) {
            throw new NotUrlTypeException(messageSource.getMessage("wrong.youtube.url", new Object[]{url}, null, null));
        }

        RestTemplate restTemplate = new RestTemplate();
        try {
            result = restTemplate.getForObject(String.format(YOUTUBE_API_URL, url), Map.class);
        } catch (Exception e) {
            throw new NotUrlTypeException(messageSource.getMessage("wrong.url", new Object[]{url}, null, null));
        }

        return modelMapper.map(result, OembedEntity.class);
    }

    private boolean isYoutubeUrl(String url) {
        return url.startsWith("https://www.youtube.com/watch?v=");
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

    private boolean isUrlFormat(String url) {
        return Pattern.compile("^((http|https)://)?(www.)?([a-zA-Z0-9]+)\\.[a-z]+([a-zA-Z0-9.?#]+)?").matcher(url).find();
    }
}
