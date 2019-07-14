package ru.skillbranch.devintensive.models

import android.util.Log

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    var wrongAnswer:Int = 0
    var allAnswer:Int = 0

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

        if (allAnswer < 5 ) {
            if (question.validate(answer)) {
                return if (question.answers.contains(answer.toLowerCase())) {
                    allAnswer += 1
                    wrongAnswer = 0

                    question = question.nextQuestion()
                    "Отлично - ты справился\n${question.question}" to status.color

                } else {
                    wrongAnswer += 1
                    if (wrongAnswer < 5) {
                        status = status.nextStatus()
                        Log.d("M_Bender", "$wrongAnswer")
                        Log.d("M_Bender", "Это неправильный ответ\n${question.question}")
                        Log.d("M_Bender", "${status.color}")
                        "Это неправильный ответ\n${question.question}" to status.color
                    } else {
                        status = Status.NORMAL
                        question = Question.NAME

                        Log.d("M_Bender", "$wrongAnswer")
                        Log.d("M_Bender", "Это неправильный ответ. Давай все по новой\n${question.question}")
                        Log.d("M_Bender", "${status.color}")

                        "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
                    }

                }
            } else {
                return "${question.errorMessage()}\n${question.question}" to status.color
            }
        } else { return "На этом все, вопросов больше нет" to Triple(255,255,255)}
    }


  ///  Error in module3(ru.skillbranch.devintensive.InstrumentalTest1): androidx.test.espresso.base.DefaultFailureHandler$AssertionFailedWithCauseError:
    // 'with text: is
    // "Это неправильный ответ. Давай все по новой\nКак меня зовут?"'
    // doesn't match the selected view. Expected: with text: is
    // "Это неправильный ответ. Давай все по новой\nКак меня зовут?"
    // Got: "AppCompatTextView{id=2131230913, res-name=tv_text, visibility=VISIBLE, width=320, height=100, has-focus=false,
    // has-focusable=false, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false,
    // is-layout-requested=false, is-selected=false, layout-params=android.widget.LinearLayout$LayoutParams@a47407e, tag=null,
    // root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0, text=
    // Это неправильный ответ\nКак меня зовут?,
    // input-type=0, ime-target=false, has-links=false}"





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