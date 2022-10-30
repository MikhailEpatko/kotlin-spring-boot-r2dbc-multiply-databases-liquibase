package ru.epatko.application.app.config

import javax.sql.DataSource
import liquibase.integration.spring.SpringLiquibase
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class ApplicationLiquibaseConfig(
    private val env: Environment,
) {

    private val db = "app.db.application"
    private val user = env.getProperty("$db.user")
    private val password = env.getProperty("$db.password")
    private val driver = env.getProperty("$db.driver")
    private val host = env.getProperty("$db.host")
    private val port = env.getProperty("$db.port")
    private val database = env.getProperty("$db.database")

    @Bean(name = ["applicationDatasource"])
    fun applicationDataSource(): DataSource = DataSourceBuilder
        .create()
        .username(user)
        .password(password)
        .url("jdbc:$driver://$host:$port/$database")
        .build()

    @Bean
    fun applicationLiquibase(
        @Qualifier("applicationDatasource") applicationDatasource: DataSource,
    ): SpringLiquibase = SpringLiquibase().apply {
        this.dataSource = applicationDatasource
        this.changeLog = env.getProperty("$db.liquibase.change-log")
        this.defaultSchema = env.getProperty("$db.liquibase.liquibaseSchema")
        this.setShouldRun(env.getProperty("$db.liquibase.enabled").toBoolean())
    }
}
