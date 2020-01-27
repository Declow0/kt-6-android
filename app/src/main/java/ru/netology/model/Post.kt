package ru.netology.model

import java.time.LocalDateTime
import java.util.*

class Post(
    val usedName: String,
    var content: String
) {
    val id: UUID = UUID.randomUUID()
    val createTime: LocalDateTime = LocalDateTime.now()
    private var views: Long = 0L

    var favorite: Long = 0L
    var favoriteByMe: Boolean = false
        private set
    var share: Long = 0L
    var shareByMe: Boolean = false
        private set
    var comment: Long = 0L
    var commentByMe: Boolean = false
        private set

    fun incViews() = views++

    fun changeFavorite() {
        favoriteByMe = if (!favoriteByMe) {
            favorite++
            true
        } else {
            favorite--
            false
        }
    }

    fun changeShare() {
        shareByMe = if (!shareByMe) {
            share++
            true
        } else {
            share--
            false
        }
    }

    fun changeComment() {
        commentByMe = if (!commentByMe) {
            comment++
            true
        } else {
            comment--
            false
        }
    }
}