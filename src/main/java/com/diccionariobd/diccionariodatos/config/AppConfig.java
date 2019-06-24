package com.diccionariobd.diccionariodatos.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.vaadin.spring.annotation.EnableVaadin;

@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
@EntityScan("com.diccionariobd.diccionariodatos.datos")
@EnableVaadin
public class AppConfig {
	
	@Autowired
    private Environment env;
 
    @Value("${app.datasource.driver-class-name}") 
    String driverClassName;
    @Value("${app.datasource.url}") 
    String url;
    @Value("${app.datasource.username}") 
    String username;
    @Value("${app.datasource.password}") 
    String password;
    
    
	 @Bean
	 public DataSource mysqlDataSource() {
		 DriverManagerDataSource dataSource = new DriverManagerDataSource();
	     /*dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
	     dataSource.setUrl(env.getProperty("jdbc.url"));
	     dataSource.setUsername(env.getProperty("jdbc.username"));
	     dataSource.setPassword(env.getProperty("jdbc.password"));*/
		 
		 dataSource.setDriverClassName(driverClassName);
	     dataSource.setUrl(url);
	     dataSource.setUsername(username);
	     dataSource.setPassword(password);
		 
	     return dataSource;  
	 }
	 
	 @Bean
	 public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()	  {
	        return new PropertySourcesPlaceholderConfigurer();
	 }    
	 
	 @Bean
	 public JdbcTemplate jdbcTemplate(DataSource dataSource) {
	        return new JdbcTemplate(dataSource);
	 }
	 
	 @Bean
	 public PlatformTransactionManager transactionManager(DataSource dataSource) {
	        return new DataSourceTransactionManager(dataSource);
	 }



    /*@Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }*/

}
