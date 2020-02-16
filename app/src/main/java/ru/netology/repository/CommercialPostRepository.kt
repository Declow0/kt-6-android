package ru.netology.repository

import ru.netology.model.PostType

object CommercialPostRepository :
    APostRepository({ it.type.contains(PostType.COMMERCIAL)})