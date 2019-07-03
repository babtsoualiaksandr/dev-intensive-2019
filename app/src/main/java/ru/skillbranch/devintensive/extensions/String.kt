package ru.skillbranch.devintensive.extensions

//*String.truncate
//Необходимо реализовать метод truncate усекающий исходную строку до указанного числа символов и добавляющий заполнитель "..." в конец строки
//Реализуй extension усекающий исходную строку до указанного числа символов (по умолчанию 16) и возвращающий усеченную строку с заполнителем "..."
// если последний символ усеченной строки является пробелом - удалить его и добавить заполнитель
//Пример:
//"Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate() //Bender Bending Ro...
//"Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate(14) //Bender Bending..

fun String.truncate(value :Int = 16): String {

    var result = StringBuilder()
    println(this.length)
    for (i in 0..this.length-1) {
        if (i < value + 1) result.append(this[i])

    }

    println(result[result.lastIndex])
    if (result[result.lastIndex] == ' ') result.deleteCharAt(result.lastIndex)
    result.append("...")


return result.toString()
}


// последовательностей ("& < > ' "")
fun String.stripHtml(): String {


    var result = StringBuilder()
    var isTag = false

    for (i in 0..this.length-1) {

        if ((this[i]== '<')||((this[i]== '&')) && isTag == false) {

            isTag = true
        }
        if ((this[i]== '>')||(this[i]==';') && isTag == true) {

            isTag = false
        }

        if (this[i]!='<' && this[i]!='>'&& isTag ==false) {
            if (this[i]==' '&& this[i-1]==' ') {

            } else  result.append(this[i])
        }
    }
    return result.toString()
    }