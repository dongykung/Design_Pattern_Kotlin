package org.example.prototype

/**
 * 프로토타입 패턴이란 즉 기존의 객체를 복사하여 객체를 생성하는 패턴입니다.
 * 어떻게 Ex1에서 마주한 문제들을 해결할 수 있을가요?
 * data class의 경우 copy를 사용할 수 있습니다.
 * data class의 경우 equals, toString, copy, componentN 메서드를 자동으로 정의해줍니다.
 */

data class ProtoUser(
    val name: String,
    val role: Role,
    private val permissions: Set<String>,
    val tasks: List<String>
)

val allProtoUsers = mutableListOf<ProtoUser>()

fun createProtoUser(name: String, role: Role) {
    for (u in allProtoUsers) {
        if (u.role == role) {
            allProtoUsers += u.copy(permissions = setOf("BasicPermission"))
            return
        }
    }
}
/**
 * data class의 copy 메서드를 통해 쉽게 기존 객체를 복사하여 비슷한 새 객체를 생성할 수 있습니다.
 */


data class Address(var city: String, var street: String)
data class People(var name: String, val address: Address)

fun main() {
    val p1 = People("DK", Address("서울", "강남대로"))
    val p2 = p1.copy()
    println(p1 === p2) // false
    println(p1.name === p2.name) // true

    // 하지만 Address는 같은 객체
    println(p1.address === p2.address) // true

    p2.address.city = "경기"
    println(p1.address.city) // p1 까지 경기로 바뀌게 됨

    // String은 불변 객체
    p2.name = "Bob"
    // p1의 name은 변경되지 않음
    println(p1.name) // DK
}

/**
 * 즉, 위의 결과로 정리해보자면 객체의 필드가 모두 불변이라면 데이터를 공유해도 변경이 불가능하기 때문에 안전하다.
 * 하지만 가변 필드가 존재하는 경우(불변 객체가 아닐 경우) 원본 데이터가 오염될 수 있다는 점을 주의하고 사용해야 한다.
 */