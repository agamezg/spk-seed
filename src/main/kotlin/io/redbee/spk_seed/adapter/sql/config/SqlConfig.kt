package io.redbee.spk_seed.adapter.sql.config

import io.r2dbc.pool.ConnectionPool
import io.r2dbc.pool.ConnectionPoolConfiguration
import io.r2dbc.pool.PoolingConnectionFactoryProvider.MAX_SIZE
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import java.time.Duration

@Configuration
@EnableR2dbcRepositories
class SqlConfig(@Value("\${spring.data.mssql.host}") private val host: String,
                @Value("\${spring.data.mssql.port}") private val port: Int,
                @Value("\${spring.data.mssql.database}") private val database: String,
                @Value("\${spring.data.mssql.username}") private val username: String,
                @Value("\${spring.data.mssql.password}") private val password: String
) : AbstractR2dbcConfiguration() {

    @Bean
    override fun connectionFactory(): ConnectionFactory {
        val connectionFactory = ConnectionFactories.get(
            ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, "pool")
                .option(
                    ConnectionFactoryOptions.PROTOCOL,
                    "mssql"
                ) // driver identifier, PROTOCOL is delegated as DRIVER by the pool.
                .option(ConnectionFactoryOptions.HOST, host)
                .option(ConnectionFactoryOptions.PORT, port)
                .option(ConnectionFactoryOptions.USER, username)
                .option(ConnectionFactoryOptions.PASSWORD, password)
                .option(ConnectionFactoryOptions.DATABASE, database)
                .option(MAX_SIZE, 30)
                .build()
        )
        val configuration = ConnectionPoolConfiguration.builder(connectionFactory)
            .maxIdleTime(Duration.ofMinutes(30))
            .initialSize(10)
            .maxSize(30)
            .maxCreateConnectionTime(Duration.ofSeconds(1))
            .build()


        return ConnectionPool(configuration)
    }
}
