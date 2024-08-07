import kotlin.random.Random

enum class Dice(private val values: List<Int>) {
    TWO(listOf(1,2)),
    FOUR(listOf(1,2,3,4)),
    SIX((1..6).toList()),
    TEN((1..10).toList()),
    TWELVE((1..12).toList()),
    TWENTY((1..20).toList());

    private fun randomValue(): Int = values[Random.nextInt(values.size)]
    fun roll( rolls:Int=1 ):List<Int> = (0 until  rolls).map { randomValue() }
}

