# News MCP Server

A Ktor-based server written in Kotlin implementing the Model Context Protocol (MCP) with tools for fetching news headlines and searching news articles using the [NewsAPI](https://newsapi.org/).

## Features

- **Top Headlines Tool**: Fetches top news headlines by category (business, entertainment, general, health, science, sports, technology).
- **Search News Tool**: Searches news articles by query, with advanced search options and date/sort filters.

## Requirements

- **JDK 11+**
- **Gradle**
- **NewsAPI Key**: Register at [newsapi.org](https://newsapi.org/register) and set the `NEWS_API_KEY` environment variable.

## Getting Started

### 1. Clone the repository

```sh
git clone https://github.com/nishantpardamwar/kotlin-news-mcp-server.git
cd mcpserver
```

### 2. Set up your NewsAPI key

Set the `NEWS_API_KEY` environment variable:

**IntelliJ IDEA:**
1. Open the project in IntelliJ IDEA.
2. Go to **Run > Edit Configurations...**
3. Select your run configuration for this project (or create a new one).
4. In the **Environment variables** field, add:
   ```
   NEWS_API_KEY=your_api_key_here
   ```
5. Click **OK** to save.

### 3. Build and run the server

Hit Run  'Application.kt' (▶) on your AndroidStudio/IntelliJ IDE

The server will start on `http://localhost:3001`.

## Usage

The server exposes two MCP tools:

### 1. Get Top Headlines

- **Tool Name:** `get-top-headline`
- **Description:** Get top headlines by category.
- **Input:**
  - `category` (string): One of `business`, `entertainment`, `general`, `health`, `science`, `sports`, `technology`.

### 2. Search News

- **Tool Name:** `search-news-query`
- **Description:** Search news by query with advanced options.
- **Input:**
  - `query` (string): Keywords or phrases to search for.
  - `fromDate` (string, optional): Oldest article date (ISO 8601).
  - `toDate` (string, optional): Newest article date (ISO 8601).
  - `sortBy` (string, optional): `relevancy`, `popularity`, or `publishedAt`.

## Integration with Cursor

To use this MCP server with [Cursor](https://www.cursor.so/), you need to configure Cursor to connect to your local MCP server instance. Follow these steps:

1. **Start the MCP server** (see instructions above).
2. **Go to Cursor → Settings → Tools & Integrations → New MCP Server**.
3. **Add the following configuration:**

```json
{
    "mcpServers": {
        "news-mpc-server": {
            "url": "http://localhost:3001/sse"
        }
    }
}
```

You should now be able to use the tools provided by this server (such as `get-top-headline` and `search-news-query`) directly within Cursor's MCP tool interface.

## Project Structure

- `src/main/kotlin/Application.kt`: Main entry point, server setup.
- `src/main/kotlin/NewsTool.kt`: Tool implementations for news fetching/search.
- `build.gradle.kts`: Build configuration.

## Dependencies

- [Ktor](https://ktor.io/) (server, CIO engine)
- [Model Context Protocol Kotlin SDK](https://github.com/modelcontextprotocol/kotlin-sdk)
- [NewsAPI](https://newsapi.org/)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

⭐ **Star this repository if you found it helpful!** 

