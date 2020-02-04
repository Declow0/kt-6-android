package ru.netology.adapter

import android.view.View
import ru.netology.model.Post

class InnerViewHolder(postAdapter: PostAdapter, view: View) : BaseViewHolder(postAdapter, view) {

    override fun bind(post: Post) {
        super.bind(post)
        bindRepost(post)
    }
}