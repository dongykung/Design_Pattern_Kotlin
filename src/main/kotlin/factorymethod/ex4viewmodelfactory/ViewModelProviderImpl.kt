package org.example.factorymethod.ex4viewmodelfactory

import kotlin.reflect.KClass

class ViewModelProviderImpl(
    private val store: ViewModelStore,
    private val factory: ViewModelProvider.Factory,
    // private val defaultExtras: CreationExtras
) {
    private val lock = Any()

    internal fun <T : ViewModel> getViewModel(
        modelClass: KClass<T>,
        key: String = modelClass.simpleName.toString()
    ): T {
        return synchronized(lock) {
            val viewModel = store[key]

            if (viewModel != null) {
                return@synchronized  viewModel as T
            }

            return@synchronized factory.create(modelClass).also { vm ->
                store.put(key, vm)
            }
        }
    }
}

