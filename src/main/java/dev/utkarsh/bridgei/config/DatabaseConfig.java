package dev.utkarsh.bridgei.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    // 1. PostgreSQL (Target & Spring Batch Metadata)
    @Primary
    @Bean(name = "postgresDataSource")
    @ConfigurationProperties(prefix = "app.datasource.postgres")
    public DataSource postgresDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    // 2. IBM i (Source)
    @Bean(name = "as400DataSource")
    @ConfigurationProperties(prefix = "app.datasource.as400")
    public DataSource as400DataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    // 3. JdbcTemplate for reading from AS/400 safely
    @Bean(name = "as400JdbcTemplate")
    public JdbcTemplate as400JdbcTemplate(DataSource as400DataSource) {
        return new JdbcTemplate(as400DataSource);
    }
}
