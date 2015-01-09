package lst.medo.config;

import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;

/**
 * A helper class for creating urls.
 */
public class UrlCreator {
    /**
     * Creates a function for building a url with a speciefied query string parameter.
     *
     * @param url the url
     * @param name the query string parameter to modify
     * @param <T> type of the query string value
     * @return the function
     */
    public static <T> Function<T, String> urlSettingQueryParam(String url, String name) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        return o -> builder.replaceQueryParam(name, o).toUriString();
    }

    /**
     * Creates a function for building a url with a speciefied query string parameter using the request url.
     *
     * @param request the request
     * @param name the query string parameter to modify
     * @param <T> type of the query string value
     * @return the function
     */
    public static <T> Function<T, String> urlSettingQueryParam(HttpServletRequest request, String name) {
        return urlSettingQueryParam(getFullURL(request), name);
    }

    /**
     * Gets the full url of the request (including query string parameters)
     * @param request the request
     * @return full url
     */
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
