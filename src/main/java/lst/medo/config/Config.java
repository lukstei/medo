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
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
@ComponentScan
public class Config  {
    @Bean
    @ConfigurationProperties(prefix = "medo.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
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
}
