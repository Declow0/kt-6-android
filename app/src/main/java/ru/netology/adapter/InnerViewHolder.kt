package ru.netology.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.inner_elem.view.*
import ru.netology.model.Post
import ru.netology.model.PostType
import ru.netology.repository.repository
import ru.netology.service.intervalBetweenNowMessage

class InnerViewHolder(postAdapter: PostAdapter, view: View) : BaseViewHolder(postAdapter, view) {
    init {
        with(itemView) {
            geolocation.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val post = adapter.postList[adapterPosition]

                    context.startActivity(
                            Intent().apply {
                                action = Intent.ACTION_VIEW
                                data = Uri.parse(
                                        "geo:" +
                                                if (post.location != null) "${post.location.latitude},${post.location.longitude}" else "0,0" +
                                                        if (post.address.isNotBlank()) "?q=${Uri.encode(post.address)}" else ""
                                )
                            }
                    )
                }
            }

            youtube.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val post = adapter.postList[adapterPosition]
                    context.startActivity(
                            Intent().apply {
                                action = Intent.ACTION_VIEW
                                data = Uri.parse("https://www.youtube.com/watch?v=${post.youtubeId}")
                            }
                    )
                }
            }

            commercial.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val post = adapter.postList[adapterPosition]
                    context.startActivity(
                            Intent().apply {
                                action = Intent.ACTION_VIEW
                                data = post.commercialContent
                            }
                    )
                }
            }
        }
    }

    override fun bind(post: Post) {
        with(itemView) {
            if (post.type.contains(PostType.COMMERCIAL)) {
                createTime.text = "Рекламная запись"
            } else {
                createTime.text = intervalBetweenNowMessage(post.createTime)
            }
            userName.text = post.createdUser
            postContent.text = post.content

            if (post.type.contains(PostType.GEO_EVENT)) {
                geolocation.visibility = View.VISIBLE
            } else {
                geolocation.visibility = View.INVISIBLE
            }

            if (post.type.contains(PostType.YOUTUBE)) {
                youtube.visibility = View.VISIBLE
//                var url = URL("http://img.youtube.com/vi/${post.youtubeId}/maxresdefault.jpg")
//                interactive.background = Drawable.createFromStream()
            } else {
                youtube.visibility = View.GONE
            }

            if (post.type.contains(PostType.COMMERCIAL)) {
                commercial.visibility = View.VISIBLE
            } else {
                commercial.visibility = View.GONE
            }

            if (post.type.contains(PostType.REPOST)) {
                repost.visibility = View.VISIBLE

                val reposted = repository.get(post.original)
                if (reposted != null) {
                    with(repost) {
                        layoutManager = LinearLayoutManager(repost.context)
                        adapter = PostAdapter(mutableListOf(reposted.copy(inner = true)))
                    }
                }
            } else {
                repost.visibility = View.GONE
            }
        }
    }
}