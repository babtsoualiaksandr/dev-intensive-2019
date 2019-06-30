package ru.skillbranch.devintensive.extensions

import java.util.*
import kotlin.math.absoluteValue

const val SECOND = 1000L
const val MINUTE = 60* SECOND
const val HOUR = 60* MINUTE
const val DAY  = 24 * HOUR
fun Date.format (pattern:String="HH:mm:ss dd.MM.yy"):String {
    val dateFormat = java.text.SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)

}

fun Date.add(value: Int, units: TimeUnit = TimeUnit.SECOND): Date {
    var time = this.time

    time += when (units){
        TimeUnit.SECOND -> value * SECOND
        TimeUnit.MINUTE -> value * MINUTE
        TimeUnit.HOUR -> value * HOUR
        TimeUnit.DAY -> value * DAY

    }
    this.time = time
    return this
}

enum class TimeUnit {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}


//0с - 1с "только что"
//1с - 45с "несколько секунд назад"
//45с - 75с "минуту назад"
//75с - 45мин "N минут назад"
//45мин - 75мин "час назад"
//75мин 22ч "N часов назад"
//22ч - 26ч "день назад"
//26ч - 360д "N дней назад"
//>360д "более года назад"


fun num2str(n: Int , units: TimeUnit = TimeUnit.SECOND): String {
    val text_forms: Array<String>
    when (units) {
        TimeUnit.SECOND -> text_forms = arrayOf("секунда" , "секунд" , "секунд")
        TimeUnit.MINUTE -> text_forms = arrayOf("минута" , "минуты" , "минут")
        TimeUnit.HOUR -> text_forms = arrayOf("часа" , "часа" , "часов")
        TimeUnit.DAY -> text_forms = arrayOf("день" , "дня" , "дней")

    }
    val nn = (n.absoluteValue % 100)
    var n1 = nn % 10;
    if (nn > 10 && nn < 20) {
        return "$nn ${text_forms[2]}" }
    if (n1 > 1 && n1 < 5) {
        return "$nn ${text_forms[1]}" }
    if (n1 == 1) {
        return "$nn ${text_forms[0]}" }
    return "$nn ${text_forms[2]}"
}

fun Date.humanizeDiff(date: Date= Date()): String {
    println(this.time)
    println(date.time)

    var time = this.time - date.time
    println(time)

    return when (time) {
        //0с - 1с "только что"
        in 1..1000 -> "совсем скоро"
        in -1000..0 -> "только что"
        //1с - 45с "несколько секунд назад"
        in 1001..45000 -> "через несколько секунд"
        in -45000..-1000 -> "несколько секунд назад"
        //45с - 75с "минуту назад"
        in 45001..75000 -> "через минуту"
        in -75000..-45000 -> "минуту назад"
        //75с - 45мин "N минут назад"
        in 75000..45*60*1000 -> "через ${num2str((time/1000 / 60).toInt(), TimeUnit.MINUTE)}"
        in -45*60*1000..-75000 -> "${num2str((time / 1000 / 60).toInt(), TimeUnit.MINUTE)}  назад"
        //45мин - 75мин "час назад"
        in -75*60*1000+1..-45*60*1000 -> "час назад"
        in 45*60*1000..75*60*1000 -> "через час"
        //75мин 22ч "N часов назад"
        in -22*60*60*1000+1..-75*60*1000 -> "${num2str((time/1000/60/60).toInt(), TimeUnit.HOUR)} назад"
        in 75*60*1000..22*60*60*1000 -> "через ${num2str((time/1000/60/60).toInt(), TimeUnit.HOUR)}"
        //22ч - 26ч "день назад"
        in -26*60*60*1000+1..22*60*60*1000 -> "день назад"
        in 22*60*60*1000..26*60*60*1000 -> "через день"
        //26ч - 360д "N дней назад"
        in -360*24*60*60*1000+1..26*60*60*1000 -> "${num2str((time/1000/60/60/24).toInt(), TimeUnit.DAY)} назад"
        in 26*60*60*100..360*24*60*60*1000 -> "через ${num2str((time/1000/60/60/24).toInt(), TimeUnit.DAY)}"
        //>360д "более года назад"
        else -> {if (time>0) {"более чем через год"} else {"более года назад"}}

    }


}

