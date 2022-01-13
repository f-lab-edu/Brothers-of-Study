package kr.bos.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import kr.bos.utils.DataSourceType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * DataSourceConfig. DB Replication 위한 설정.
 *
 * @since 1.0.0
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    @Value("${spring.datasource.master.url}")
    private String masterUrl;

    @Value("${spring.datasource.master.username}")
    private String masterUsername;

    @Value("${spring.datasource.master.password}")
    private String masterPassword;

    @Value("${spring.datasource.slave.url}")
    private String slaveUrl;

    @Value("${spring.datasource.slave.username}")
    private String slaveUsername;

    @Value("${spring.datasource.slave.password}")
    private String slavePassword;

    /**
     * MasterDataSource Bean 등록.
     *
     * @since 1.0.0
     */
    @Bean
    public DataSource masterDataSource() {
        return DataSourceBuilder.create()
            .url(masterUrl)
            .username(masterUsername)
            .password(masterPassword)
            .build();
    }

    /**
     * SlaveDataSource Bean 등록.
     *
     * @since 1.0.0
     */
    @Bean
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create()
            .url(slaveUrl)
            .username(slaveUsername)
            .password(slavePassword)
            .build();
    }

    /**
     * 트랜잭션의 readOnly 여부를 확인 후 Master, Slave Datasource 선택.
     *
     * @since 1.0.0
     */
    @Bean
    public DataSource routingDataSource(DataSource masterDataSource, DataSource slaveDataSource) {
        AbstractRoutingDataSource routingDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                    ? DataSourceType.SLAVE : DataSourceType.MASTER;
            }
        };

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.MASTER, masterDataSource);
        targetDataSources.put(DataSourceType.SLAVE, slaveDataSource);

        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        return routingDataSource;
    }

    /**
     * 쿼리가 발생하는 시점에 DataSource의 Connection을 가져오기 위한 트랜잭션의 LazyConnectionDataSourceProxy.
     *
     * @since 1.0.0
     */
    @Bean
    public DataSource proxyDataSource(DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    /**
     * 트랜잭션 매니저에 LazyConnectionDataSourceProxy 등록.
     *
     * @since 1.0.0
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource proxyDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(proxyDataSource);
        return transactionManager;
    }
}