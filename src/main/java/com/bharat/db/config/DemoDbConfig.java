package com.bharat.db.config;
import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
/**
 * 
 * @author ErBharatp
 *
 */
@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(basePackages = "com.bharat.db.demo.repo", entityManagerFactoryRef = "demoEntityManager", transactionManagerRef = "demoTransactionManager")
public class DemoDbConfig {
	@Autowired
	private Environment env;

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean demoEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(demoDataSource());
		em.setPackagesToScan(new String[] { "com.bharat.db.demo.model" });

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("spring.jpa.properties.hibernate.dialect",
				env.getProperty("spring.jpa.properties.hibernate.dialect"));
		properties.put("spring.jpa.show-sql", env.getProperty("spring.jpa.show-sql"));

		// Bug de PostgreSQL:La fonction createClob() n'est pas encore implémentée.
		properties.put("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation",
				env.getProperty("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation"));

		properties.put("spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults = false",
				env.getProperty("spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults = false"));
		em.setJpaPropertyMap(properties);

		return em;
	}

	@Primary
	@Bean
	public DataSource demoDataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));

		return dataSource;
	}

	@Primary
	@Bean
	public PlatformTransactionManager demoTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(demoEntityManager().getObject());
		return transactionManager;
	}
}