package com.nishantpardamwar

import io.ktor.server.cio.CIO
import io.ktor.server.engine.*
import io.modelcontextprotocol.kotlin.sdk.Implementation
import io.modelcontextprotocol.kotlin.sdk.ServerCapabilities
import io.modelcontextprotocol.kotlin.sdk.server.Server
import io.modelcontextprotocol.kotlin.sdk.server.ServerOptions
import io.modelcontextprotocol.kotlin.sdk.server.mcp

fun main() {
    embeddedServer(CIO, port = 3001, host = "0.0.0.0") {
        mcp { return@mcp configureMPC() }
    }.start(wait = true)
}

private fun configureMPC(): Server {
    val server = Server(
        serverInfo = Implementation(
            name = "news-mpc-server", version = "0.0.1"
        ), options = ServerOptions(
            capabilities = ServerCapabilities(
                tools = ServerCapabilities.Tools(listChanged = true)
            )
        )
    )

    server.addTools(listOf(topHeadlineTool, searchNewsTool))

    return server
}