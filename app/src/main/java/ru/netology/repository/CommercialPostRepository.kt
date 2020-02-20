package ru.netology.repository

import ru.netology.model.PostType

class CommercialPostRepository :
    APostRepository({ it.type.contains(PostType.COMMERCIAL) })