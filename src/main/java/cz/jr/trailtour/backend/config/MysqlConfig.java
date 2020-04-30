package cz.jr.trailtour.backend.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by Jiří Rýdel on 2/28/20, 11:10 AM
 */
@Configuration
public class MysqlConfig {

    @Bean
    @Primary
    public HikariDataSource configureHikari() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(Constants.DATABASE_URL);
        hikariConfig.setUsername(Constants.DATABASE_USER);
        hikariConfig.setPassword(Constants.DATABASE_PASSWORD);
        hikariConfig.setPoolName(Constants.DATABASE_NAME);
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(1);
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");

        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        hikariConfig.addDataSourceProperty("useLocalSessionState", "true");
        hikariConfig.addDataSourceProperty("useLocalTransactionState", "true");
        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");
        hikariConfig.addDataSourceProperty("cacheResultSetMetadata", "true");
        hikariConfig.addDataSourceProperty("cacheServerConfiguration", "true");
        hikariConfig.addDataSourceProperty("elideSetAutoCommits", "true");
        hikariConfig.addDataSourceProperty("maintainTimeStats", "false");
        hikariConfig.addDataSourceProperty("characterEncoding", "UTF-8");
        hikariConfig.addDataSourceProperty("useUnicode", "true");
        hikariConfig.addDataSourceProperty("useSSL", "false");
        hikariConfig.addDataSourceProperty("allowPublicKeyRetrieval", "true");
        hikariConfig.addDataSourceProperty("serverTimezone", Constants.TIMEZONE);

        return new HikariDataSource(hikariConfig);
    }
}
