package org.example.factorymethod.ex4viewmodelfactory

import kotlin.reflect.KClass

fun main() {
    val viewModelStoreOwner = object: ViewModelStoreOwner {
        override val viewModelStore: ViewModelStore = ViewModelStore()
    }
    println("새로운 NewsDetail 화면 진입")
    // newsDetailViewModel 객체를 생성합니다.
    val newsDetailViewModel: NewsDetailViewModel = viewModel(viewModelStoreOwner, null, NewsDetailViewModel.Factory)

    // NewsDetail 화면을 벗어낫다 가정하고 ViewModel 범위를 벗어났다고 가정해봅시다
    viewModelStoreOwner.viewModelStore.clear()

    println("NewsDetail 화면 벗어남\n")

    // 다시 뉴스 디테일 화면에 들어왔다고 가정해봅시다
    println("새로운 NewsDetail 화면 진입")
    val sameNewsDetailViewModel1: NewsDetailViewModel = viewModel(viewModelStoreOwner, "keys", NewsDetailViewModel.Factory)
    val sameNewsDetailViewModel2: NewsDetailViewModel = viewModel(viewModelStoreOwner, "keys", NewsDetailViewModel.Factory)
    // 두 뷰모델 인스턴스의 참조는 같을가요?
    println(sameNewsDetailViewModel1 === sameNewsDetailViewModel2)

    // 서로 다른 뷰모델 객체를 얻으려면? key를 다르게 하면 된다
    println("\nsameNewsDetailViewModel과 다른 뷰모델 객체 생성")
    val diffNewsDetailViewModel: NewsDetailViewModel = viewModel(viewModelStoreOwner, "diff", NewsDetailViewModel.Factory)
    println("diffNewsList: ${diffNewsDetailViewModel.news}")

    println("\n 화면 벗어남")
    viewModelStoreOwner.viewModelStore.clear()

}

inline fun <reified VM : ViewModel> viewModel(
    viewModelStoreOwner: ViewModelStoreOwner,
    key: String? = null,
    factory: ViewModelProvider.Factory
): VM {
    return viewModelStoreOwner.get(
        modelClass = VM::class,
        key = key,
        factory = factory
    )
}

fun <VM : ViewModel> ViewModelStoreOwner.get(
    modelClass: KClass<VM>,
    key: String? = null,
    factory: ViewModelProvider.Factory
): VM {
    val provider = ViewModelProvider(this.viewModelStore, factory)
    return if (key != null) {
        provider[key, modelClass]
    } else {
        provider[modelClass]
    }
}