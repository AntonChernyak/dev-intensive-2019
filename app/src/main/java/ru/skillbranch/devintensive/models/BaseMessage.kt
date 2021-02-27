package ru.skillbranch.devintensive.models

import java.util.*

abstract class BaseMessage(
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming: Boolean = false,
    val date: Date = Date()
) {
    abstract fun formatMessage(): String

    companion object AbstractFactory {
        var lastId = -1

        fun makeMessage(
            from: User?,
            chat: Chat,
            date: Date = Date(),
            type: TypeMessage,
            payload: Any?,
            isIncoming: Boolean = false
        ): BaseMessage {
            lastId++
            return when (type) {
                TypeMessage.IMAGE -> ImageMessage(
                    id = "$lastId",
                    from = from,
                    chat = chat,
                    date = date,
                    image = payload as String
                )
                TypeMessage.TEXT -> TextMessage(
                    id = "$lastId",
                    from = from,
                    chat = chat,
                    date = date,
                    text = payload as String
                )
            }
        }
    }
}

enum class TypeMessage {
    IMAGE,
    TEXT
}