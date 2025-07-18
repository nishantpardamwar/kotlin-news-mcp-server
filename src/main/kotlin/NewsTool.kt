package com.nishantpardamwar

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.server.util.url
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.TextContent
import io.modelcontextprotocol.kotlin.sdk.Tool
import io.modelcontextprotocol.kotlin.sdk.server.RegisteredTool
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonObject

val httpClient = HttpClient()
val NEWS_API_KEY = System.getenv("NEWS_API_KEY")
    ?: throw IllegalStateException("NEWS_API_KEY is Missing, Get the API KEY from https://newsapi.org/register and set it in the Environment Variable.")

val topHeadlineTool = RegisteredTool(
    Tool(
        name = "get-top-headline", description = "Get Top headlines by category", inputSchema = Tool.Input(
            properties = buildJsonObject {
                putJsonObject("category") {
                    put("type", "string")
                    put(
                        "description",
                        "category, Possible options: business,entertainment,general,health,science,sports,technology"
                    )
                }
            }, required = listOf("category")
        )
    )
) { request ->
    val category = request.arguments["category"]?.jsonPrimitive?.content ?: return@RegisteredTool CallToolResult(
        content = listOf(TextContent("Required field 'category' is missing"))
    )

    val url = url {
        protocol = URLProtocol.HTTPS
        host = "newsapi.org"
        path("v2", "top-headlines")
        parameters.append("category", category)
        parameters.append("apiKey", NEWS_API_KEY)
    }

    val result = runBlocking {
        httpClient.get(url).body<String>()
    }

    CallToolResult(content = listOf(TextContent(result)))
}

val searchNewsTool = RegisteredTool(
    Tool(
        name = "search-news-query", description = "Search News by query", inputSchema = Tool.Input(
            properties = buildJsonObject {
                putJsonObject("query") {
                    put("type", "string")
                    put(
                        "description",
                        """
                            Keywords or phrases to search for in the article title and body.

                            Advanced search is supported here:
                                Surround phrases with quotes (") for exact match.
                                Prepend words or phrases that must appear with a + symbol. Eg: +bitcoin
                                Prepend words that must not appear with a - symbol. Eg: -bitcoin
                                Alternatively you can use the AND / OR / NOT keywords, and optionally group these with parenthesis. Eg: crypto AND (ethereum OR litecoin) NOT bitcoin.
                        """.trimIndent()
                    )
                }
                putJsonObject("fromDate") {
                    put("type", "string")
                    put(
                        "description",
                        "A date and optional time for the oldest article allowed. This should be in ISO 8601 format (e.g. 2025-07-18 or 2025-07-18T18:32:19) "
                    )
                }
                putJsonObject("toDate") {
                    put("type", "string")
                    put(
                        "description",
                        "A date and optional time for the newest article allowed. This should be in ISO 8601 format (e.g. 2025-07-18 or 2025-07-18T18:32:19) "
                    )
                }
                putJsonObject("sortBy") {
                    put("type", "string")
                    put(
                        "description", """
                            The order to sort the articles in. Possible options: relevancy, popularity, publishedAt.
                            relevancy = articles more closely related to query come first.
                            popularity = articles from popular sources and publishers come first.
                            publishedAt = newest articles come first. 
                        """.trimIndent()
                    )
                }
            }, required = listOf("query")
        )
    )
) { request ->
    val query = request.arguments["query"]?.jsonPrimitive?.content ?: return@RegisteredTool CallToolResult(
        content = listOf(TextContent("Required field 'query' is missing"))
    )

    val fromDate = request.arguments["fromDate"]?.jsonPrimitive?.content
    val toDate = request.arguments["toDate"]?.jsonPrimitive?.content
    val sortBy = request.arguments["sortBy"]?.jsonPrimitive?.content
    val url = url {
        protocol = URLProtocol.HTTPS
        host = "newsapi.org"
        path("v2", "everything")
        parameters.append("q", query)
        if (!fromDate.isNullOrBlank()) parameters.append("from", fromDate)
        if (!toDate.isNullOrBlank()) parameters.append("to", toDate)
        if (!sortBy.isNullOrBlank()) parameters.append("sortBy", sortBy)

        parameters.append("apiKey", NEWS_API_KEY)
    }

    val result = runBlocking {
        httpClient.get(url).body<String>()
    }

    CallToolResult(content = listOf(TextContent(result)))
}