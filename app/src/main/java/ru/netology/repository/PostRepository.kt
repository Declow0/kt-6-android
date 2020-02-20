package ru.netology.repository

import ru.netology.model.PostType

class PostRepository :
    APostRepository({ !it.type.contains(PostType.COMMERCIAL) })