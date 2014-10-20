package lst.medo.config;

import lst.medo.dao.ArticleDao;
import lst.medo.dao.AuthorDao;
import lst.medo.dao.MediaDao;
import lst.medo.dao.impl.JooqArticleDao;
import lst.medo.dao.impl.JooqAuthorDao;
import lst.medo.dao.impl.JooqMediaDao;
import lst.medo.model.DateRange;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.util.EnumSet;

@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties
public class Config  {
    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return (ServletContext servletContext) -> {
            final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
            characterEncodingFilter.setEncoding("UTF-8");
            characterEncodingFilter.setForceEncoding(false);

            servletContext.addFilter("characterEncodingFilter", characterEncodingFilter).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");
        };
    }

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        DatabaseEnvConfig config = new DatabaseEnvConfig();

        builder.driverClassName("org.postgresql.Driver");
        builder.username("postgres");//config.getUserName());
        builder.password(config.getPassword());
        builder.url("jdbc:postgresql://localhost:5432/medo");//config.getUrl());

        return builder.build();
    }

    @Bean DSLContext dslContext(DataSource dataSource) {
        return DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    @Bean ArticleDao articleDao(DSLContext context) {
        return new JooqArticleDao(context);
    }

    @Bean MediaDao mediaDao(DSLContext context) {
        return new JooqMediaDao(context);
    }

    @Bean AuthorDao authorDao(DSLContext context) {
        return new JooqAuthorDao(context);
    }

    @Bean(name = "urlCreator") UrlCreator urlCreator() {
        return new UrlCreator();
    }

    @Bean DateRangeFormatter.DateRangeAnnotationFormatterFactory dateRangeAnnotationFormatterFactory() {
        return new DateRangeFormatter.DateRangeAnnotationFormatterFactory();
    }

    @Bean DateRangeFormatter dateRangeFormatter() {
        return new DateRangeFormatter(new DateFormatter());
    }

}
