package ru.skillbranch.devintensive.models

class Bender(
    var status: Status = Status.NORMAL,
    var question: Question = Question.NAME
) {

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        return if (!question.validation(answer)) {
            val validateString: String = when (question) {
                Question.NAME -> "Имя должно начинаться с заглавной буквы\n"
                Question.PROFESSION -> "Профессия должна начинаться со строчной буквы\n"
                Question.MATERIAL -> "Материал не должен содержать цифр\n"
                Question.BDAY -> "Год моего рождения должен содержать только цифры\n"
                Question.SERIAL -> "Серийный номер содержит только цифры, и их 7\n"
                Question.IDLE -> ""
            }
            "$validateString${question.question}" to status.color
        } else if (question == Question.IDLE) return question.question to status.color
        else if (question.answers.contains(answer.toLowerCase())) {
            question = question.nextQuestion()
            "Отлично - ты справился\n${question.question}" to status.color
        } else {
            if (status == Status.CRITICAL) {
                status = Status.NORMAL
                question = Question.NAME
                return "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            }
            status = status.nextStatus()
            "Это неправильный ответ\n${question.question}" to status.color
        }
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("Бендер", "bender")) {
            override fun validation(message: String): Boolean {
                return message.isNotEmpty() && message.first().isUpperCase()
            }

            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun validation(message: String): Boolean {
                return message.isNotEmpty() && message.first().isLowerCase()
            }

            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun validation(message: String): Boolean {
                return !message.contains("[0-9]".toRegex())
            }

            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun validation(message: String): Boolean {
               // if (message.isEmpty()) return false
                for (l in message) {
                    if (!l.isDigit()) return false
                }
                return true
            }

            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun validation(message: String): Boolean {
                if (message.length != 7) return false
                if (message.isEmpty()) return false
                for (l in message) {
                    if (!l.isDigit()) return false
                }
                return true
            }

            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun validation(message: String): Boolean {
                return true
            }

            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question

        abstract fun validation(message: String): Boolean
    }
}