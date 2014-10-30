package lst.medo.config;

import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;

public class UrlCreator {
    public static <T> Function<T, String> urlSettingQueryParam(String url, String name) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        return o -> builder.replaceQueryParam(name, o).toUriString();
    }

    public static <T> Function<T, String> urlSettingQueryParam(HttpServletRequest request, String name) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getFullURL(request));
        return o -> builder.replaceQueryParam(name, o).toUriString();
    }

    public static String getFullURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
}
