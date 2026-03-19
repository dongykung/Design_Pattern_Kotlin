package org.example.decorator

import kotlinx.coroutines.runBlocking

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

interface NewsRepository {
    suspend fun getNews(id: Long): News
    suspend fun getNewsList(): List<News>
    suspend fun saveNews(news: News)
}

class FakeNewsApi() : NewsApi {
    val news = News(1, "데코레이터 패턴", "구조 패턴 입니다.")
    override suspend fun fetchNews(id: Long): News {
        return news
    }

    override suspend fun fetchNewsList(): List<News> {
        return emptyList()
    }

    override suspend fun postNews(news: News) {

    }

}

class RemoteNewsRepository(
    private val api: NewsApi
) : NewsRepository {
    override suspend fun getNews(id: Long): News {
        println("getNews API 호출 id: $id")
        return api.fetchNews(id)
    }

    override suspend fun getNewsList(): List<News> {
        return api.fetchNewsList()
    }

    override suspend fun saveNews(news: News) {
        api.postNews(news)
    }
}

abstract class NewsRepositoryDecorator(
    private val repository: NewsRepository
) : NewsRepository {
    override suspend fun getNews(id: Long): News = repository.getNews(id)
    override suspend fun getNewsList(): List<News> = repository.getNewsList()
    override suspend fun saveNews(news: News) = repository.saveNews(news)
}

class LoggingNewsRepository(
    private val repository: NewsRepository
) : NewsRepositoryDecorator(repository) {
    override suspend fun getNews(id: Long): News {
        println("LoggingNewsRepository: getNews(id=$id) 호출")
        return repository.getNews(id).also {
            println("LoggingNewsRepository: getNews(id=$id) 완료: ${it.title}")
        }
    }
    // 나머지는 위임
}

class CachingNewsRepository(
    private val repository: NewsRepository
) : NewsRepositoryDecorator(repository) {
    private val cache = mutableMapOf<Long, News>()

    override suspend fun getNews(id: Long): News {
        return cache.getOrPut(id) { repository.getNews(id) }
    }
    // 나머지는 위임
}

class LoggingNewsRepositoryBy(
    private val repository: NewsRepository
) : NewsRepository by repository {
    override suspend fun getNews(id: Long): News {
        println("LoggingNewsRepository: getNews(id=$id) 호출")
        return repository.getNews(id).also {
            println("LoggingNewsRepository: getNews(id=$id) 완료: ${it.title}")
        }
    }
    // 나머지는 위임
}

class CachingNewsRepositoryBy(
    private val repository: NewsRepository
) : NewsRepository by repository {
    private val cache = mutableMapOf<Long, News>()

    override suspend fun getNews(id: Long): News {
        return cache.getOrPut(id) { repository.getNews(id) }
    }
    // 나머지는 위임
}

fun main() = runBlocking<Unit> {
    val api: NewsApi = FakeNewsApi()

    val repository = LoggingNewsRepository(
        CachingNewsRepository(
            RemoteNewsRepository(api)
        )
    )

    repository.getNews(1)

    println("====================")

    repository.getNews(1)
}