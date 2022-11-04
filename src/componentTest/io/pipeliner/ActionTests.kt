package io.pipeliner

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.isSuccessful
import io.pipeliner.extensions.ApplicationTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(ApplicationTest::class)
internal class ActionTests {

    @Test
    fun `Should properly query actions by service ID`() {
        val (_, res, _) = Fuel.get("http://localhost:7070/actions/by-service/ABCDEFG")
            .header("Authorization", "secret")
            .response()
        assertTrue(res.isSuccessful)
        assertEquals("[]", String(res.data))
    }
}
