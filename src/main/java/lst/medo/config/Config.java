package lst.medo.config;

import lst.medo.dao.ArticleDao;
import lst.medo.dao.impl.JooqArticleDao;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class Config {
    @Bean
    @ConfigurationProperties(prefix="medo.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean DSLContext dslContext(DataSource dataSource) {
        return DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    @Bean ArticleDao articleDao(DSLContext context) {
        return new JooqArticleDao(context);
    }
}
