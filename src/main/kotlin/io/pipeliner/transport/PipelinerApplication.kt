package io.pipeliner.transport

import io.javalin.Javalin
import io.pipeliner.infrastructure.ActionModule
import io.pipeliner.infrastructure.DatabaseModule
import io.pipeliner.infrastructure.environment.ServerEnvironmentVariables
import io.pipeliner.transport.action.ActionRouter
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
        return Javalin.create().start(
            ServerEnvironmentVariables.serverPort
        ).also {
            registerRoutes(it)
        }
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
                ActionModule(),
            )
        }
    }
}
