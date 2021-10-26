package org.binchoo.env.propagation.config;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class JdbcDataSourceConfig {

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager jpm = new JpaTransactionManager(entityManagerFactory());
        jpm.setNestedTransactionAllowed(true);
        return jpm;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("org.binchoo.env.propagation.entities");
        factoryBean.setDataSource(jdbcDataSource());
        factoryBean.setJpaProperties(eclipseLinkProperties());
        factoryBean.setJpaDialect(new EclipseLinkJpaDialect());
        factoryBean.setJpaVendorAdapter(new EclipseLinkJpaVendorAdapter());
        factoryBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    @Bean
    public DataSource jdbcDataSource() {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL("jdbc:h2:~/test");
        return jdbcDataSource;
    }

    @Bean
    public Properties eclipseLinkProperties() {
        Properties eclipseLinkProperties = new Properties();
        eclipseLinkProperties.put("eclipselink.ddl-generation", "drop-and-create-tables");
        eclipseLinkProperties.put("eclipselink.ddl-generation.output-mode", "database");
        eclipseLinkProperties.put("eclipselink.weaving", "false");
        return eclipseLinkProperties;
    }
}
