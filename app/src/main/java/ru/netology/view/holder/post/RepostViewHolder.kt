package ru.netology.view.holder.post

import android.view.View
import ru.netology.view.adapter.PostAdapter
import ru.netology.model.Post

class RepostViewHolder(postAdapter: PostAdapter, view: View) : PostViewHolder(postAdapter, view) {

    override fun bind(post: ru.netology.model.Post) {
        super.bind(post)
        bindRepost(post)
    }
}