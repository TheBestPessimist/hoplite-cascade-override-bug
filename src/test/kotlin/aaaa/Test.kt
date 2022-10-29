package aaaa

import com.sksamuel.hoplite.ConfigLoader
import com.sksamuel.hoplite.ExperimentalHoplite
import com.sksamuel.hoplite.addResourceOrFileSource
import com.sksamuel.hoplite.internal.CascadeMode
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalHoplite::class)
val config: Config = ConfigLoader.builder()
    .addResourceOrFileSource("/application.properties") // the contents of this file should be ignored, but is not (see google credentials)
    .addResourceOrFileSource("/a1.properties")
    .addResourceOrFileSource(".env.properties") // must always be last as it contains the super secret stuff and is ignored by git.
    .withCascadeMode(CascadeMode.Override)
    .build()
    .loadConfigOrThrow()


class bug {
    @Test
    fun `bug`() {
        println("actual: $config")

        val expected = Config(
            GoogleCredentials(".env.properties", ".env.properties"),
            Database("a1.properties", "a1.properties")
        )

        assertEquals(expected, config)
    }
}

data class Config(
    val googleCredentials: GoogleCredentials,
    val database: Database,
)

data class GoogleCredentials(
    val clientId: String,
    val clientSecret: String,
)

data class Database(
    val jdbcUrl: String,
    val driverClassName: String,
)
