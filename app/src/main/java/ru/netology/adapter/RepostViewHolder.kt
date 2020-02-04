package ru.netology.adapter

import android.view.View
import ru.netology.model.Post

class RepostViewHolder(postAdapter: PostAdapter, view: View) : PostViewHolder(postAdapter, view) {

    override fun bind(post: Post) {
        super.bind(post)
        bindRepost(post)
    }
}