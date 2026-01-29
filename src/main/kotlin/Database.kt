package dev.heinkel

import io.ktor.server.plugins.di.annotations.Property
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.conf.MappedSchema
import org.jooq.conf.RenderMapping
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import java.sql.DriverManager

fun provideDslContext(
    @Property("datasource.host") host: String,
    @Property("datasource.port") port: Int,
    @Property("datasource.db") db: String,
    @Property("datasource.username") username: String,
    @Property("datasource.password") password: String,
    @Property("datasource.schema") schema: String,
): DSLContext {
    val jdbcUrl = "jdbc:postgresql://$host:$port/$db"
    val connection = DriverManager.getConnection(jdbcUrl, username, password)
    val settings = Settings().withRenderMapping(
        RenderMapping().withSchemata(
            MappedSchema().withInput("public").withOutput(schema)
        )
    )
    return DSL.using(connection, SQLDialect.POSTGRES, settings)
}

