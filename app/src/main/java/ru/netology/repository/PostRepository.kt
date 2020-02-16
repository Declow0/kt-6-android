package ru.netology.repository

import ru.netology.model.PostType

object PostRepository :
    APostRepository({ !it.type.contains(PostType.COMMERCIAL) })