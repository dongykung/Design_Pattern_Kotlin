package org.example.proxy

// ServiceInterface
interface Database {
    fun query(sql: String): String
}

// Service
class RealDatabase : Database {
    override fun query(sql: String): String {
        println("실제 DB 쿼리 실행 $sql")
        return "$sql 결과"
    }
}

// Proxy
class DatabaseProxy(
    private val realService: RealDatabase
) : Database {

    private fun checkAccess(): Boolean {
        println("Access 확인중")
        return true
    }

    override fun query(sql: String): String {
        if (checkAccess()) {
            return realService.query(sql)
        }
        throw SecurityException("엑세스 거부")
    }
}

// Client
class Client(private val db: Database) {
    fun doWork() {
        val result = db.query("SELECT * FROM  Kotlin")
        print(result)
    }
}

fun main() {
    val real = RealDatabase()
    val proxy = DatabaseProxy(real)
    val client = Client(proxy)
    client.doWork()
}