package io.pipeliner.extensions

import io.pipeliner.transport.PipelinerApplication
import java.io.File
import java.util.function.Consumer
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.output.OutputFrame

class ApplicationTest : AfterAllCallback, BeforeAllCallback {

    private lateinit var container: DockerComposeContainer<*>

    override fun afterAll(context: ExtensionContext?) {
        PipelinerApplication.stop()
        container.stop()
    }

    override fun beforeAll(context: ExtensionContext?) {
        container = DockerComposeContainer(File("docker-compose.yml"))
            .withLogConsumer("localstack_1", LocalStackLogConsumer)
            .withLogConsumer("db_1", DatabaseLogConsumer)
            .also {
                it.start()
            }
        waitForContainersToBeReady()
        PipelinerApplication.start()
    }

    private fun waitForContainersToBeReady() {
        while(!DatabaseLogConsumer.databaseIsReady && !LocalStackLogConsumer.localStackIsReady) {
            Thread.sleep(2_500)
        }
    }

    object DatabaseLogConsumer : Consumer<OutputFrame> {

        var databaseIsReady = false

        override fun accept(t: OutputFrame) {
            val messageString = String(t.bytes)
            if (messageString.contains("database system is ready to accept connections")) {
                databaseIsReady = true
            }
        }
    }

    object LocalStackLogConsumer : Consumer<OutputFrame> {

        var localStackIsReady = false

        override fun accept(t: OutputFrame) {
            val messageString = String(t.bytes)
            if (messageString.contains("Execution of \"start_runtime_components\" took")) {
                localStackIsReady = true
            }
        }
    }
}
