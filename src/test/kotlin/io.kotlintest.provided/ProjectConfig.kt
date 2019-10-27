package io.kotlintest.provided

import Server
import io.kotlintest.AbstractProjectConfig
import kotlin.concurrent.thread

object ProjectConfig : AbstractProjectConfig() {

    private val server = Server("127.0.0.1", 8080)
    private lateinit var serverThread: Thread

    override fun beforeAll() {
        serverThread = thread { server.start() }
    }

    override fun afterAll() {
        server.stop()
        serverThread.interrupt()
    }

    override fun parallelism() = Runtime.getRuntime().availableProcessors()
}