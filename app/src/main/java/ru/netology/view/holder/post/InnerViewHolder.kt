package ru.netology.view.holder.post

import android.view.View
import ru.netology.view.adapter.PostAdapter
import ru.netology.model.Post

class InnerViewHolder(postAdapter: PostAdapter, view: View) : ABaseViewHolder(postAdapter, view) {

    override fun bind(post: Post) {
        super.bind(post)
        bindRepost(post)
    }
}