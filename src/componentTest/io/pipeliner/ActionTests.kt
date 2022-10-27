package io.pipeliner

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.isSuccessful
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(ComponentTest::class)
internal class ActionTests {

    @Test
    fun `Should run whatever`() {
        val (_, res, _) = Fuel.post("http://localhost:7070/actions").response()
        assertTrue { res.isSuccessful }
    }
}
