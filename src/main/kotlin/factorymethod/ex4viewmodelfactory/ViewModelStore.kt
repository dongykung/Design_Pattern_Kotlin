package org.example.factorymethod.ex4viewmodelfactory

class ViewModelStore {
    val map: MutableMap<String, ViewModel> = mutableMapOf()

    public operator fun get(key: String): ViewModel? {
         return map[key]
    }

    public fun put(key: String, viewModel: ViewModel) {
        val oldViewModel = map.put(key, viewModel)
        oldViewModel?.clear()
    }

    public fun clear() {
        for (vm in map.values) {
            vm.clear()
        }
        map.clear()
    }
}