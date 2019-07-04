package ru.skillbranch.devintensive.extensions

import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60* SECOND
const val HOUR = 60* MINUTE
const val DAY  = 24 * HOUR
fun Date.format (pattern:String="HH:mm:ss dd.MM.yy"):String {
    val dateFormat = java.text.SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)

}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY

    }
    this.time = time
    return this
}



enum class TimeUnits  {
    SECOND {
        override fun plural(value:Int) : String {
            val result = num2str(value,SECOND )
            return result;        }

    },
    MINUTE {
        override fun plural(value:Int) : String {
            val result = num2str(value,MINUTE )
            return result;        }

    },
    HOUR {
        override fun plural(value:Int) : String {
            val result = num2str(value,HOUR )
            return result;        }

    },
    DAY{
        override fun plural(value:Int) : String {
            val result = num2str(value,DAY )
            return result;        }

    };

    abstract fun plural(value:Int) : String
}


//**plural
//Необходимо реализовать метод plural для enum TimeUnits
//+2
//Реализуй метод plural для всех перечислений TimeUnits следующего вида TimeUnits.SECOND.plural(value:Int) возвращающую значение
// с праильно склоненной единицой измерения
//Пример:
//TimeUnits.SECOND.plural(1) //1 секунду
//TimeUnits.MINUTE.plural(4) //4 минуты
//TimeUnits.HOUR.plural(19) //19 часов
//TimeUnits.DAY.plural(222) //222 дня


//0с - 1с "только что"
//1с - 45с "несколько секунд назад"
//45с - 75с "минуту назад"
//75с - 45мин "N минут назад"
//45мин - 75мин "час назад"
//75мин 22ч "N часов назад"
//22ч - 26ч "день назад"
//26ч - 360д "N дней назад"
//>360д "более года назад"


fun num2str(n: Int , units: TimeUnits = TimeUnits.SECOND): String {
    val text_forms: Array<String>
    when (units) {
        TimeUnits.SECOND -> text_forms = arrayOf("секунду" , "секунды" , "секунд")
        TimeUnits.MINUTE -> text_forms = arrayOf("минуту" , "минуты" , "минут")
        TimeUnits.HOUR -> text_forms = arrayOf("час" , "часа" , "часов")
        TimeUnits.DAY -> text_forms = arrayOf("день" , "дня" , "дней")

    }

    val nresult = abs(n)
    val nn = (abs(n) % 100)

    var n1 = nn % 10;

    if (nn > 10 && nn < 20) {
        return "$nresult ${text_forms[2]}" }
    if (n1 > 1 && n1 < 5) {
        return "$nresult ${text_forms[1]}" }
    if (n1 == 1) {
        return "$nresult ${text_forms[0]}" }
    return "$nresult ${text_forms[2]}"
}

fun Date.humanizeDiff(date: Date= Date()): String {
    println(this.time)
    println(date.time)

    var time = ((this.time - date.time)/1000).toLong()

    println(time/24/60/60)

    return when (time) {
        //0с - 1с "только что"
        in 0..1 -> "только что"
        in -1..0 -> "только что"
        //1с - 45с "несколько секунд назад"
        in 1..45 -> "через несколько секунд"
        in -45..-1 -> "несколько секунд назад"
        //45с - 75с "минуту назад"
        in 45..75 -> "через минуту"
        in -75..-45 -> "минуту назад"
        //75с - 45мин "N минут назад"
        in 75..45*60*1 -> "через ${ num2str((time / 60).toInt(), TimeUnits.MINUTE)}"
        in -45*60..-75 -> "${num2str((time / 60).toInt(), TimeUnits.MINUTE)} назад"
        //45мин - 75мин "час назад"
        in -75*60..-45*60 -> "час назад"
        in 45*60..75*60-> "через час"
        //75мин 22ч "N часов назад"
        in -22*60*60+1..-75*60-> "${num2str((time/60/60).toInt(), TimeUnits.HOUR)} назад"
        in 75*60..22*60*60 -> "через ${num2str((time/60/60).toInt(), TimeUnits.HOUR)}"
        //22ч - 26ч "день назад"
        in -26*60*60..-22*60*60-> "день назад"
        in 22*60*60..26*60*60 -> "через день"
        //26ч - 360д "N дней назад"
        in -360*24*60*60..-26*60*60-> "${num2str((time/60/60/24).toInt(), TimeUnits.DAY)} назад"
        in 26*60*60..360*24*60*60 -> "через ${num2str((time/60/60/24).toInt(), TimeUnits.DAY)}"
        //>360д "более года назад"
        else -> {if (time>0) {"более чем через год"} else {"более года назад"}}

    }




}

fun Date.humanizeDiff_new(date: Date = Date()): String {
    val diffInMillies = ((this.time - date.time+6) / SECOND).toInt()

    val minute = 60
    val hour = 60 * 60
    val day = 24 * hour

    return when (diffInMillies) {
        in -45..-1 -> "несколько секунд назад"
        in -75..-45 -> "минуту назад"
        in -45 * minute..-75 -> "${minutesAsPlular(abs(diffInMillies / 60))} назад"
        in -75 * minute..-45 * minute -> "час назад"
        in -22 * hour..-75 * minute -> "${hoursAsPlular(abs(diffInMillies / 60 / 60))} назад"
        in -26 * hour..-22 * hour -> "день назад"
        in -360 * day..-26 * hour -> "${daysAsPlular(abs(diffInMillies / 60 / 60 / 24))} назад"
        in -Int.MAX_VALUE..-360 * day -> "более года назад"

        in -1..1 -> "только что"

        in 1..45 -> "через несколько секунд"
        in 45..75 -> "через минуту"
        in 75..45 * minute -> "через ${minutesAsPlular(diffInMillies / 60)}"
        in 45 * minute..75 * minute -> "через час"
        in 75 * minute..22 * hour -> "через ${hoursAsPlular(diffInMillies / 60 / 60)}"
        in 22 * hour..26 * hour -> "через день"
        in 26 * hour..360 * day -> "через ${daysAsPlular(diffInMillies / 60 / 60 / 24)}"
        in 360 * day..Int.MAX_VALUE -> "более чем через год"

        else -> "более года назад"
    }
}


enum class Plular {
    ONE,
    FEW,
    MANY
}

private fun minutesAsPlular(value: Int) = when (value.asPlulars()) {
    Plular.ONE -> "$value минуту"
    Plular.FEW -> "$value минуты"
    Plular.MANY -> "$value минут"
}

private fun hoursAsPlular(value: Int) = when (value.asPlulars()) {
    Plular.ONE -> "$value час"
    Plular.FEW -> "$value часа"
    Plular.MANY -> "$value часов"
}

private fun daysAsPlular(value: Int) = when (value.asPlulars()) {
    Plular.ONE -> "$value день"
    Plular.FEW -> "$value дня"
    Plular.MANY -> "$value дней"
}

fun Int.asPlulars() : Plular {
    if (this > 10 && this % 100 / 10 == 1) return Plular.MANY
    if (this == 0 || this == 1) return Plular.ONE

    return when (this % 10) {
        1 -> Plular.ONE
        2, 3, 4 -> Plular.FEW
        else -> Plular.MANY
    }
}

