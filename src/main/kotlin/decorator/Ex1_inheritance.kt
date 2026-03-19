package org.example.decorator

/**
data class News(
    val id: Long,
    val title: String,
    val content: String,
)

interface NewsApi {
    suspend fun fetchNews(id :Long): News
    suspend fun fetchNewsList(): List<News>
    suspend fun postNews(news: News)
}

open class NewsRepository(val api: NewsApi) {
    open suspend fun getNews(id: Long): News = api.fetchNews(id)
    open suspend fun getNewsList(): List<News> = api.fetchNewsList()
    open suspend fun saveNews(news: News) = api.postNews(news)
}

class LoggingNewsRepository(api: NewsApi) : NewsRepository(api) {
    override suspend fun getNews(id: Long): News {
        print("LoggingNewsRepository getNews(id=$id) 호출")
        return super.getNews(id)
    }
}

class CachingNewsRepository(api: NewsApi) : NewsRepository(api) {
    private val cache = mutableMapOf<Long, News>()

    override suspend fun getNews(id: Long): News {
        return cache.getOrPut(id) { super.getNews(id) }
    }
}
 */