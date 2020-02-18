package ru.netology.view.holder.post

import android.view.View
import ru.netology.model.Post
import ru.netology.view.adapter.PostAdapter

class RepostViewHolder(postAdapter: PostAdapter, view: View) : PostViewHolder(postAdapter, view) {

    override fun bind(post: Post) {
        super.bind(post)
        bindRepost(post)
    }
}