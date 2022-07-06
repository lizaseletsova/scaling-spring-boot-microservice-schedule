package com.scaling.config;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * The Shed lock config.
 */
@Configuration
@EnableSchedulerLock(defaultLockAtMostFor = "1m")
public class ShedLockConfig {


    /**
     *  The Lock provider.
     *  Specifying usingDbTime () the block provider will use UTC time based on the DB server time.
     *  If you do not specify this option, the current time on the client will be used (the time may differ between clients).
     *  If you need to specify a schema, you can set it in the table name using the usual dot notation
     *  new JdbcTemplateLockProvider(datasource, "my_schema.shedlock")
     *
     * @param dataSource the data source
     * @return the lock provider
     */
    @Bean
    public LockProvider lockProvider(DataSource dataSource) {
        return new JdbcTemplateLockProvider(
                JdbcTemplateLockProvider.Configuration.builder()
                        .withJdbcTemplate(new JdbcTemplate(dataSource))
                        .withTableName("SHEDLOCK")
                        .usingDbTime()
                        .build()
        );
    }
}
