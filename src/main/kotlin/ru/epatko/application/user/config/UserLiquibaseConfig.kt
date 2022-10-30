package ru.epatko.application.user.config

import javax.sql.DataSource
import liquibase.integration.spring.SpringLiquibase
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class UserLiquibaseConfig(
    private val env: Environment,
) {

    private val db = "app.db.user"
    private val user = env.getProperty("$db.user")
    private val password = env.getProperty("$db.password")
    private val driver = env.getProperty("$db.driver")
    private val host = env.getProperty("$db.host")
    private val port = env.getProperty("$db.port")
    private val database = env.getProperty("$db.database")

    @Bean(name = ["userDatasource"])
    fun userDataSource(): DataSource = DataSourceBuilder
        .create()
        .username(user)
        .password(password)
        .url("jdbc:$driver://$host:$port/$database")
        .build()

    @Bean
    fun userLiquibase(
        @Qualifier("userDatasource") userDatasource: DataSource,
    ): SpringLiquibase = SpringLiquibase().apply {
        this.dataSource = userDatasource
        this.changeLog = env.getProperty("$db.liquibase.change-log")
        this.defaultSchema = env.getProperty("$db.liquibase.liquibaseSchema")
        this.setShouldRun(env.getProperty("$db.liquibase.enabled").toBoolean())
    }
}
