package ru.netology.view.holder.post

import android.view.View
import ru.netology.model.Post
import ru.netology.view.adapter.PostAdapter

class InnerViewHolder(postAdapter: PostAdapter, view: View) : ABaseViewHolder(postAdapter, view) {

    override fun bind(post: Post) {
        super.bind(post)
        bindRepost(post)
    }
}