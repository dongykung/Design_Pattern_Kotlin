package org.example.factorymethod.ex4viewmodelfactory

abstract class ViewModel {
    // viewModelImpl 및 AutoCloseable Logic...

    protected open fun onCleared() {}

    internal fun clear() {
        // impl.clear
        onCleared()
    }
}