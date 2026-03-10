package org.example.factorymethod.ex4viewmodelfactory

import kotlin.reflect.KClass

private const val AndroidNewsId: Long = 1

class NewsDetailViewModel(
    private val id: Long,
) : ViewModel() {

    private val _news: MutableList<String> = mutableListOf()
    val news: List<String> = _news

    init {
        println("NewsDetailViewModel init | hash: ${hashCode()} | id:$id")
        loadNews(id)
    }

    fun loadNews(id: Long) {
        _news.add("Android News:$id")
    }

    companion object {
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: KClass<T>): T {
                return NewsDetailViewModel(AndroidNewsId) as T
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("NewsDetailViewModel onCleared | hash: ${hashCode()} | id:$id")
    }
}