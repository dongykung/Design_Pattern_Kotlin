package org.example.proxy

interface Document {
    fun read(): String
    fun write(content: String)
}

class RealDocument(
    private var content: String
) : Document {
    override fun read(): String {
        return content
    }

    override fun write(content: String) {
        this.content = content
    }
}

interface AccessPolicy {
    fun canRead(): Boolean
    fun canWrite(): Boolean
}

class AdminPolicy : AccessPolicy {
    override fun canRead() = true
    override fun canWrite(): Boolean = true
}

class ViewerPolicy : AccessPolicy {
    override fun canRead() = true
    override fun canWrite() = false
}

class ProtectedDocument(
    private val real: RealDocument,
    private val policy: AccessPolicy
) : Document {
    override fun read(): String {
        if (!policy.canRead()) throw SecurityException("읽기 권한 없음")
        return real.read()
    }

    override fun write(content: String) {
        if (!policy.canWrite()) throw SecurityException("쓰기 권한 없음")
        real.write(content)
    }
}

fun main() {
    val doc = ProtectedDocument(RealDocument("Content"), ViewerPolicy())
    println(doc.read())
    doc.write("내용 수정")
}