# Prototype
기존 객체를 복사하여 새로운 객체를 생성하는 생성 패턴

즉, 유사하면서도 조금 다른 객체를 그때그때 목적에 맞게 생성하기 위해 사용합니다.

## 구조
<img width="500" height="400" alt="프로토타입" src="https://github.com/user-attachments/assets/dd41a0a4-7159-4d28-89d4-1a4d10812702" />

<br>

## 언제 사용하는가?
- 객체 생성에 많은 비용이 드는 경우
- 비슷하지만 조금씩 다른 객체를 생성하느라 비슷한 코드를 매번 반복하고 싶지 않은 경우

<br>

## 어덯게 객체를 복사하는가?

### 깊은 복사
유저 data class가 있다고 가정해보겠습니다.
```kotlin
enum class Role {
    ADMIN,
    SUPER_ADMIN,
    NORMAL
}

data class User(
    val name: String,
    val role: Role,
    private val permission: Set<String> //
    // 또는 새로운 필드가 추가된다면? => 기존의 코드가 변경되어야 한다
    // val tasks: List<String> 이 추가된다면?
)
```
새로운 사용자를 만들 때 동일한 역학(role)을 갖는 사용자라면 동일한 권한(permission)을 부여한다고 가정해보겠습니다.
```kotlin
val allUsers = mutableListOf<User>()

fun createUser(name: String, role: Role) {
    for (u in allUsers) {
        if (u.role == role) {
            //allUsers += User(name, role, u.permission)
            // error => 기존 유저의 permission이 private
            // 또 만약 새로운 필드가 생긴다면? 이 함수의 코드 또한 수정되어야 한다 => tasks도 명시를 해주게 수정해야 함
        }
    }
}
```
- permission 필드가 private 이기 때문에 접근할 수가 없습니다.
- 또 User에 새로운 필드가 추가된다면 createUser 함수의 코드 또한 변경되어야 합니다.

이와 같은 문제를 어떻게 해결할가요? data class 내에 새로운 객체를 생성하는 함수를 작성하면 됩니다. <br>
클래스 내의 자기 자신의 필드는 접근이 가능하기 때문입니다.
```kotlin
data class User2(
    val name: String,
    val role: Role,
    private val permission: Set<String> //
) {
    fun createNewUser(name: String, role: Role): User2 {
        return User2(
            name = name,
            role = role,
            permission = permission.toSet() // 자기 자신의 필드니까 접근 가능
        )
    }
}
```

<br>

### 얕은 복사
Kotlin의 data class는 여러 메서드를 자동으로 제공해줍니다.
- toString
- equals
- componentN
- copy

바로 copy 메서드를 제공해주는데요 얕은 복사를 하게 됩니다.
```kotlin
data class Address(var city: String, var street: String)
data class People(var name: String, val address: Address)

val p1 = People("DK", Address("서울", "강남대로"))
val p2 = p1.copy()
```
p1의 address와 p2의 address는 같은 참조를 가리키게 됩니다.  <br>
만약 둘 중 하나의 address의 city를 변경하게 되면 어떻게 될가요?

```kotlin
println(p1.address === p2.address) // true
p2.address.city = "경기"
println(p1.address.city) // p1 까지 경기로 바뀌게 됨
println(p1.address === p2.address) // true
```
즉 얕은 복사일 때 가변 필드가 존재한다면 원본이 변경될 수 있음을 인지하고 사용하는 것이 중요합니다. <br>
불변 객체의 경우 상관없지만 Address같이 같은 가변 객체의 경우 원본이 변경될 수 있음을 유의해야 합니다.

<br>

## 예제
- []()
