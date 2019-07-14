package ru.skillbranch.devintensive.models

import android.util.Log

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    var wrongAnswer:Int = 0

    fun askQuestion(): String = when (question) {

        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
    Log.d("M_Bender","answer $answer")
        Log.d("M_Bender","valid ${question.validate(answer)}")
    if (question.validate(answer)) {
        return if (question.answers.contains(answer.toLowerCase())) {
            question = question.nextQuestion()

            "Отлично - ты справился\n${question.question}" to status.color
        } else {
            wrongAnswer += 1
            if (wrongAnswer < 4) {
                status = status.nextStatus()
                Log.d("M_Bender", "$wrongAnswer")
                Log.d("M_Bender", "Это неправильный ответ\n${question.question}")
                Log.d("M_Bender", "${status.color}")
                "Это неправильный ответ\n${question.question}" to status.color
            } else {
                status = status.nextStatus()
                question = Question.NAME
                wrongAnswer = 0
                Log.d("M_Bender", "$wrongAnswer")
                Log.d("M_Bender", "Это неправильный ответ. Давай все по новой\n${question.question}")
                Log.d("M_Bender", "${status.color}")

                "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            }

        }
    } else {
        return "${question.errorMessage()}\n${question.question}" to status.color
    }
    }


    enum class Status (val color : Triple<Int, Int, Int>) {
        NORMAL (Triple(255,255,255)),
        WARNING(Triple(255,120,0)),
        DANGER(Triple(255,60,60)),
        CRITICAL(Triple(255,0,0));


        fun nextStatus(): Status {
            return  if (this.ordinal < values().lastIndex) {
                values()[this.ordinal+1]

            } else {
                values()[0]
            }
        }
    }





    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
            //Question.NAME -> "Имя должно начинаться с заглавной буквы"
            override fun validate(answer: String): Boolean = answer.trim().firstOrNull()?.isUpperCase() ?: false
            override fun errorMessage(): String = "Имя должно начинаться с заглавной буквы"
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
            //Question.PROFESSION -> "Профессия должна начинаться со строчной буквы"
            override fun validate(answer: String): Boolean = answer.trim().firstOrNull()?.isLowerCase() ?: false
            override fun errorMessage(): String = "Профессия должна начинаться со строчной буквы"
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево","metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
            //Question.MATERIAL -> "Материал не должен содержать цифр"
            override fun validate(answer: String): Boolean = answer.trim().contains(Regex("\\d")).not()
            override fun errorMessage(): String = "Материал не должен содержать цифр"
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
            //Question.BDAY -> "Год моего рождения должен содержать только цифры"
            override fun validate(answer: String): Boolean = answer.trim().contains(Regex("^[0-9]*$"))
            override fun errorMessage(): String = "Год моего рождения должен содержать только цифры"
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
            //Question.SERIAL -> "Серийный номер содержит только цифры, и их 7"
            override fun validate(answer: String): Boolean = answer.trim().contains(Regex("^[0-9]{7}$"))
            override fun errorMessage(): String = "Серийный номер содержит только цифры, и их 7"
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
            override fun validate(answer: String): Boolean = true
            override fun errorMessage(): String = ""

        };


        abstract fun  nextQuestion(): Question

        abstract fun validate(answer: String):Boolean

        abstract  fun  errorMessage(): String
    }
}