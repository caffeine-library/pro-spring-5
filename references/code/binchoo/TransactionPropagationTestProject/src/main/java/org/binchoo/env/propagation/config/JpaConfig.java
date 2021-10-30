package org.binchoo.env.propagation.config;

import org.springframework.beans.factory.annotation.Autowired;
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
public class JpaConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory());
        transactionManager.setNestedTransactionAllowed(true);
        return transactionManager;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("org.binchoo.env.propagation.entities");
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaProperties(eclipseLinkProperties());
        factoryBean.setJpaDialect(new EclipseLinkJpaDialect());
        factoryBean.setJpaVendorAdapter(new EclipseLinkJpaVendorAdapter());
        factoryBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    @Bean
    public Properties eclipseLinkProperties() {
        Properties eclipseLinkProperties = new Properties();
        eclipseLinkProperties.put("eclipselink.ddl-generation", "drop-and-create-tables");
        eclipseLinkProperties.put("eclipselink.ddl-generation.output-mode", "database");
        eclipseLinkProperties.put("eclipselink.weaving", "false");
        eclipseLinkProperties.put("eclipselink.logging.level", "FINEST");
        eclipseLinkProperties.put("eclipselink.logging.level.sql", "FINEST");
        eclipseLinkProperties.put("eclipselink.logging.parameters", "true");
        return eclipseLinkProperties;
    }
}
