package io.pipeliner

import io.pipeliner.transport.PipelinerApplication
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

class ComponentTest : AfterAllCallback, BeforeAllCallback {

    override fun afterAll(context: ExtensionContext?) {
        PipelinerApplication.stop()
    }

    override fun beforeAll(context: ExtensionContext?) {
        PipelinerApplication.start()
    }
}
