package io.pipeliner.transport

import io.javalin.Javalin
import io.javalin.config.JavalinConfig
import io.pipeliner.infrastructure.ActionModule
import io.pipeliner.infrastructure.ConfigurationModule
import io.pipeliner.infrastructure.DatabaseModule
import io.pipeliner.infrastructure.ServiceModule
import io.pipeliner.infrastructure.environment.ServerEnvironmentVariables
import io.pipeliner.transport.action.ActionRouter
import io.pipeliner.transport.configuration.ExceptionHandler
import javax.sql.DataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named

object PipelinerApplication : KoinComponent {

    var javalin: Javalin? = null

    fun start(): Javalin {
        bootstrapDependencyInjection()
        establishDatabaseConnection()
        migrateDatabase()
        return startServer().also {
            this.javalin = it
        }
    }

    fun stop() {
        javalin?.stop()
    }

    private fun startServer(): Javalin {
        return Javalin.create {
            configureAccessManager(it)
        }.start(ServerEnvironmentVariables.serverPort).also {
            configureExceptionHandler(it)
            registerRoutes(it)
        }
    }

    private fun configureAccessManager(javalinConfig: JavalinConfig) {
        javalinConfig.accessManager(get())
    }

    private fun configureExceptionHandler(javalin: Javalin) {
        javalin.exception(Exception::class.java, ExceptionHandler::handle)
    }

    private fun registerRoutes(javalin: Javalin) {
        javalin.routes {
            get<ActionRouter>().registerRoutes()
        }
    }

    private fun migrateDatabase() {
        Flyway.configure().dataSource(get(named("main"))).load().also {
            it.migrate()
        }
    }

    private fun establishDatabaseConnection() {
        Database.connect(get<DataSource>(named("main")))
    }

    private fun bootstrapDependencyInjection() {
        startKoin {
            modules(
                DatabaseModule(),
                ConfigurationModule(),
                ServiceModule(),
                ActionModule(),
            )
        }
    }
}
