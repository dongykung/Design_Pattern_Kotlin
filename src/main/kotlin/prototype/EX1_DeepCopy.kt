package org.example.prototype

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

val allUsers = mutableListOf<User>()

fun createUser(name: String, role: Role) {
    for (u in allUsers) {
        if (u.role == role) {
            allUsers += User(name, role, u.permission)
            // error => 기존 유저의 permission이 private
            // 또 만약 새로운 필드가 생긴다면? 이 함수의 코드 또한 수정되어야 한다 => tasks도 명시를 해주게 수정해야 함
        }
    }
}

/**
 * 위와 같은 문제를 해결하기 위해선 User 클래스 내에서 새로운 User 객체를 생성하는 함수를 작성해주면 해결이 가능하다
 * 자기 자신의 필드는 접근이 가능하기 때문이다
 */


data class User2(
    val name: String,
    val role: Role,
    private val permission: Set<String> //
    // 또는 새로운 필드가 추가된다면? => 기존의 코드가 변경되어야 한다
    // val tasks: List<String> 이 추가된다면?
) {
    fun createNewUser(name: String, role: Role): User2 {
        return User2(
            name = name,
            role = role,
            permission = permission // 자기 자신의 필드니까 접근 가능
        )
    }
}
