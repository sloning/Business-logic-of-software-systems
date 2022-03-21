package com.bloss.config

import org.postgresql.xa.PGXADataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter

@Configuration
@EnableJpaRepositories(
    basePackages = ["com.bloss.repository"],
    entityManagerFactoryRef = "postEntityManager",
    transactionManagerRef = "transactionManager"
)
class PostConfig(
    @Value("\${spring.datasource.url}")
    private val dbUrl: String,
    @Value("\${spring.datasource.username}")
    private val dbUsername: String,
    @Value("\${spring.datasource.password}")
    private val dbPassword: String,
) {
    @Bean(initMethod = "init", destroyMethod = "close")
    fun postDatasource() =
        AtomikosDataSourceBean().apply {
            xaDataSource = PGXADataSource().apply {
                setURL(dbUrl)
                user = dbUsername
                password = dbPassword
            }
        }

    @Bean
    fun postEntityManager() =
        LocalContainerEntityManagerFactoryBean().apply {
            dataSource = postDatasource()
            jpaVendorAdapter = HibernateJpaVendorAdapter().apply {
                setGenerateDdl(true)
            }
            setPackagesToScan("com.bloss.model")
        }
}
