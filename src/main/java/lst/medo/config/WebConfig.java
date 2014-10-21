package lst.medo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import java.util.EnumSet;

@Configuration
@ConditionalOnWebApplication
@Import(Config.class)
public class WebConfig {
    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return (ServletContext servletContext) -> {
            CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
            characterEncodingFilter.setEncoding("UTF-8");
            characterEncodingFilter.setForceEncoding(false);

            servletContext.addFilter("characterEncodingFilter", characterEncodingFilter).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");
        };
    }

    @Bean DateRangeFormatter.DateRangeAnnotationFormatterFactory dateRangeAnnotationFormatterFactory() {
        return new DateRangeFormatter.DateRangeAnnotationFormatterFactory();
    }

    @Bean DateRangeFormatter dateRangeFormatter() {
        return new DateRangeFormatter(new DateFormatter());
    }
}
