package org.example.factorymethod.ex4viewmodelfactory

import kotlin.reflect.KClass

open class ViewModelProvider private constructor(private val impl: ViewModelProviderImpl) {

    public constructor(
        store: ViewModelStore,
        factory: Factory,
    ) : this(ViewModelProviderImpl(store, factory))

    public operator fun <T : ViewModel> get(modelClass: KClass<T>): T =
        impl.getViewModel(modelClass)

    public  operator fun <T : ViewModel> get(key: String, modelClass: KClass<T>): T =
        impl.getViewModel(modelClass, key)

    interface Factory {
        public fun <T : ViewModel> create(modelClass: KClass<T>): T // 기본 예제이므로 extracts 제거
    }
}