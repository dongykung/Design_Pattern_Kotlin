# ViewModel.Factory
안드로이드에서 가장 쉽고 많이 접해본 Factory Method로 ViewModelProvider.Factory가 있습니다. <br>
주로 종속성이 있는 뷰모델을 생성할 때 아래와 같은 형식으로 많이 사용하셨을 텐데요
```kotlin
// xxViewModel
        companion object {

            val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    // Get the Application object from extras
                    val application = checkNotNull(extras[APPLICATION_KEY])
                    // Create a SavedStateHandle for this ViewModel from extras
                    val savedStateHandle = extras.createSavedStateHandle()

                    return MyViewModel(
                        (application as MyApplication).myRepository,
                        savedStateHandle
                    ) as T
                }
            }
        }
```
뷰모델을 생성할 때 생성자에 매개변수가 있는 뷰모델을 생성하기 위해서 왜 이런 코드를 만들어 주어야 할까요?

<br>

## ViewModel
Factory Method에서 학습한 Product로 볼 수 있습니다. <br>
Product는 제품을 추상화 한 것이였죠.
```kotlin
public actual abstract class ViewModel
```
때문에 JetPack의 ViewModel도 ViewModel 클래스를 추상 클래스로 선언한 것을 볼 수 있습니다. <br>

<br>

### xxViewModel
Android 개발을 하며 화면에 대한 xxViewModel(상태 홀더)를 만드는데요 예시로 뉴스 상세화면을 보여주는 NewsDetailViewModel 있다고 가정해보겠습니다.  <br>
```kotlin
class NewsDetailViewModel(
    private val id: Long,
) : ViewModel()
```
이렇게 화면에 대한 뷰모델을 정의하고 ViewModel() 클래스를 상속받습니다. <br>
즉 NewsDetailViewModel이 추상화된 제품을 구현하는 제품 구현체라고 볼 수 있습니다. 


<br>
<br>

## ViewModelProvider.Factory
최상위 공장 클래스로 볼 수 있습니다. 팩토리 메서드를 추상화하여 서브 클래스들이 구현하도록 합니다.
```kotlin
public actual open class ViewModelProvider
private constructor(private val impl: ViewModelProviderImpl) {
    ...
    public actual interface Factory {
        public fun <T : ViewModel> create(modelClass: Class<T>): T
        ...
    }
}
```

<br>

## xxViewModel.Factory
Factory Method에서 최상위 공장 클래스가 있으면 서브 공장 클래스들이 존재했고 그에 맞는 제품 객체를 반환하도록 추상 메서드를 재정의하였습니다. <br> 

그렇다면 Android에서 미리 만들어질 xxViewModel을 알 수 있을가요? 모릅니다 = 우리가 생성하기 때문이죠. <br>
그래서 우리가 ViewModel을 상속받은 xxViewModel 객체를 생성하는 Factory를 우리가 만들어 줘야 합니다.


이제 위에서 처음 보았던 Factory 코드를 왜 작성해야 하는지 알 수 있습니다. 

생성자 주입이 필요하지 않은 경우 Jetpack ViewModel에서 기본 VM 객체를 생성해 주지만 이처럼 생성자에 전달할 인자가 전달할 때는 Factory 코드를 작성해야 합니다.

<br>
<br>
<br>

# ViewModelProvider.Factory 직접 구현해보기
Factory Method를 공부해보았으니 이를 활용하여 직접 간단한 ViewModelProvider.Factory로 ViewModel을 생성하는 예제를 만들어 보았습니다.

- Android 의존성이 없는 환경에서 작성하였습니다.
- Factory Method 활용에 중점을 두어 Android 관련 코드는 제외하였습니다.

<br>

### [ViewModelStoreOnwer](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/factorymethod/ex4viewmodelfactory/ViewModelStoreOwner.kt)
Android 코드와 동일한데요 viewModelStore를 가집니다.
```kotlin
interface ViewModelStoreOwner {
    public val viewModelStore: ViewModelStore
}
```

<br>

### [ViewModelStore](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/factorymethod/ex4viewmodelfactory/ViewModelStore.kt)
뷰모델 인스턴스를 관리하는 클래스 입니다.

map을 통해 ViewModel 인스턴스들을 관리하고 ViewModelStoreOwner를 구현하는 범위가 종료될 때 clear()가 호출되어 모든 뷰모델 인스턴스를 정리합니다. 
```kotlin
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
```

<br>

### [ViewModelProvider](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/factorymethod/ex4viewmodelfactory/ViewModelProvider.kt) 
ViewModel을 요청하고 가져오는 클래스 입니다.

직접 ViewModel을 생성하지 않고, 내부의 ViewModelProviderImpl에 위임합니다. <br>
Factory 인터페이스를 통해 어떤 ViewModel이든 구체 타입을 몰라도 생성할 수 있습니다. (최상위 공장 추상화)
```kotlin
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
```

<br>

### [ViewModelProviderImpl](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/factorymethod/ex4viewmodelfactory/ViewModelProviderImpl.kt)
ViewModelProvider의 실제 동작을 담당하는 클래스 입니다. <br>
ViewModelStore에서 캐시된 ViewModel을 먼저 조회하고, 없으면 Factory를 통해 새로 생성하여 저장합니다.
```kotlin
class ViewModelProviderImpl(
    private val store: ViewModelStore,
    private val factory: ViewModelProvider.Factory,
    // private val defaultExtras: CreationExtras
) {
    private val lock = Any()

    internal fun <T : ViewModel> getViewModel(
        modelClass: KClass<T>,
        key: String = modelClass.simpleName.toString() // 실제 Android에서는 기본 key 값이 다릅니다
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
```

<br>

### [ViewModel](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/factorymethod/ex4viewmodelfactory/ViewModel.kt)
모든 ViewModel의 추상 클래스 입니다. <br>
Factory에 의해 생성되며, ViewModelStore에 저장되어 생명동안 유지됩니다.

추상화된 Product라고 비유할 수 있습니다.
```kotlin
abstract class ViewModel {
    // viewModelImpl 및 AutoCloseable Logic...

    protected open fun onCleared() {}

    internal fun clear() {
        // impl.clear
        onCleared()
    }
}
```

<br>

### [NewsDetailViewModel](https://github.com/dongykung/Design_Pattern_Kotlin/blob/master/src/main/kotlin/factorymethod/ex4viewmodelfactory/NewsDetailViewModel.kt)
ViewModel의 구체 Product 입니다. <br>
companion object에 Factory를 직접 정의하여, 생성자에 id 파라미터를 주입받아 생성됩니다.
```kotlin
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
```

<br>

### [viewModel 생성 함수](https://github.com/dongykung/Design_Pattern_Kotlin/blob/e07445a8b6a8508f90f5c4a1738e7ea2fa67c272/src/main/kotlin/factorymethod/ex4viewmodelfactory/Main.kt#L35)
Android Compose의 viewModel() 함수를 참고하여 간소화 해보았습니다. 
```kotlin
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
```

<br>

### [실행 시나리오](https://github.com/dongykung/Design_Pattern_Kotlin/blob/e07445a8b6a8508f90f5c4a1738e7ea2fa67c272/src/main/kotlin/factorymethod/ex4viewmodelfactory/Main.kt#L5)
1. 첫 NewsDetail 화면에서 뷰모델을 생성하고 init 되는지 확인합니다. 후 ViewModel 범위(화면)을 벗어낫다 가정하고 생성된 ViewModel이 onCleared가 호출되는지 확인합니다.

2. 다시 NewsDetail 화면을 진입했다 가정하고 동일한 key로 두 개의 뷰모델 인스턴서를 생성합니다. <br>
  2.1 두 뷰모델 객체가 같은지 비교합니다. <br>
  2.2 이전 key와 다른 key로 새로운 뷰모델 인스턴스를 생성합니다. <br>
  2.3 서로 다른 key로 만든 ViewModel의 hash 값을 비교해봅니다. <br>
  2.4 화면을 벗어낫다 가정하고 생성한 뷰모델 인스턴스가 clear 되는지 확인합니다.
```kotlin
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
```
