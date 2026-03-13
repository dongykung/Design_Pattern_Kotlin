package org.example.builder

// Product
data class Computer(
    val cpu: String,
    val ram: Int,
    val storage: Int,
    val gpu: String?,
    val bluetooth: Boolean,
    val wifi: Boolean,
)

// Builder interface
interface ComputerBuilder {
    fun reset()
    fun setCpu(cpu: String): ComputerBuilder
    fun setRam(ram: Int): ComputerBuilder
    fun setStorage(storage: Int): ComputerBuilder
    fun setGpu(gpu: String): ComputerBuilder
    fun setBluetooth(enabled: Boolean): ComputerBuilder
    fun setWifi(enabled: Boolean): ComputerBuilder
    fun getResult(): Computer
}

class GamingComputerBuilder : ComputerBuilder {
    private var cpu = ""
    private var ram = 0
    private var storage = 0
    private var gpu: String? = null
    private var bluetooth = false
    private var wifi = false

    override fun reset() {
        cpu = ""
        ram = 0
        storage = 0
        gpu = null
        bluetooth = false
        wifi = false
    }

    override fun setCpu(cpu: String) = apply { this.cpu = cpu }
    override fun setRam(ram: Int) = apply { this.ram = ram }
    override fun setStorage(storage: Int) = apply { this.storage = storage }
    override fun setGpu(gpu: String) = apply { this.gpu = gpu }
    override fun setBluetooth(enabled: Boolean) = apply { this.bluetooth = enabled }
    override fun setWifi(enabled: Boolean) = apply { this.wifi = enabled }

    override fun getResult() = Computer(cpu, ram, storage, gpu, bluetooth, wifi)
}

// ConcreteBuilder2: 사무용 PC
class OfficeComputerBuilder : ComputerBuilder {
    private var cpu = ""
    private var ram = 0
    private var storage = 0
    private var gpu: String? = null
    private var bluetooth = false
    private var wifi = false

    override fun reset() {
        cpu = ""
        ram = 0
        storage = 0
        gpu = null
        bluetooth = false
        wifi = false
    }

    override fun setCpu(cpu: String) = apply { this.cpu = cpu }
    override fun setRam(ram: Int) = apply { this.ram = ram }
    override fun setStorage(storage: Int) = apply { this.storage = storage }
    override fun setGpu(gpu: String) = apply { this.gpu = gpu }
    override fun setBluetooth(enabled: Boolean) = apply { this.bluetooth = enabled }
    override fun setWifi(enabled: Boolean) = apply { this.wifi = enabled }

    override fun getResult() = Computer(cpu, ram, storage, gpu, bluetooth, wifi)
}

// Director: 조립 순서를 알고 있는 디렉터
class ComputerDirector(private var builder: ComputerBuilder) {

    fun changeBuilder(builder: ComputerBuilder) {
        this.builder = builder
    }

    fun make(type: String) {
        builder.reset()
        when (type) {
            "gaming" -> {
                builder.setCpu("i9-14900K")
                    .setRam(64)
                    .setStorage(2000)
                    .setGpu("RTX 4090")
                    .setBluetooth(true)
                    .setWifi(true)
            }
            "office" -> {
                builder.setCpu("i5-14400")
                    .setRam(16)
                    .setStorage(512)
                    .setBluetooth(true)
                    .setWifi(true)
            }
        }
    }
}

// Client
fun main() {
    // 다이어그램의 Client 코드 그대로
    val builder = GamingComputerBuilder()
    val director = ComputerDirector(builder)
    director.make("gaming")
    val gamingPC = builder.getResult()
    println(gamingPC.toString()) // 게이밍 PC에 대한 정보가 출력됩니다

    // Builder 교체
    val officeBuilder = OfficeComputerBuilder()
    director.changeBuilder(officeBuilder)
    director.make("office")
    val officePC = officeBuilder.getResult()
    println(officePC.toString()) // 사무용 PC에 대한 정보가 출력됩니다
}