package ru.netology.adapter

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.repost_elem.view.*
import ru.netology.model.Post
import ru.netology.model.PostType
import ru.netology.myapplication.R
import ru.netology.repository.repository
import ru.netology.service.intervalBetweenNowMessage
import java.time.format.DateTimeFormatter

class RepostViewHolder(postAdapter: PostAdapter, view: View) : BaseViewHolder(postAdapter, view) {
    init {
        with(itemView) {
            this.setOnLongClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    adapter.postList.removeAt(adapterPosition)
                    adapter.notifyItemRemoved(adapterPosition)
                }

                true
            }

            favoriteIcon.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    var post = adapter.postList[adapterPosition]

                    post = post.copy(
                            favorite = if (post.favoriteCurrentUser) post.favorite - 1 else post.favorite + 1,
                            favoriteCurrentUser = !post.favoriteCurrentUser
                    )
                    adapter.postList[adapterPosition] = post
                    adapter.notifyItemChanged(adapterPosition)
                }
            }

            commentIcon.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    var post = adapter.postList[adapterPosition]

                    post =
                            post.copy(
                                    comment = if (post.commentCurrentUser) post.comment - 1 else post.comment + 1,
                                    commentCurrentUser = !post.commentCurrentUser
                            )
                    adapter.postList[adapterPosition] = post
                    adapter.notifyItemChanged(adapterPosition)
                }
            }

            shareIcon.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    var post = adapter.postList[adapterPosition]

                    if (!post.shareCurrentUser) {
                        post =
                                post.copy(
                                        share = post.share + 1,
                                        shareCurrentUser = true
                                )

                        context.startActivity(
                                Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(
                                            Intent.EXTRA_TEXT,
                                            "${post.createdUser} (${post.createTime.format(DateTimeFormatter.ofPattern("dd MMMM uuuu"))})\n" + post.content
                                    )
                                    type = "text/plain"
                                }
                        )

                        adapter.postList[adapterPosition] = post
                        adapter.notifyItemChanged(adapterPosition)
                    }
                }
            }

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

            bindCountView(favoriteCount, post.favorite)
            if (post.favoriteCurrentUser) {
                favoriteIcon.setBackgroundResource(R.drawable.ic_favorite_active)
                favoriteCount.setTextColor(Color.parseColor("#F06292"))
            } else {
                favoriteIcon.setBackgroundResource(R.drawable.ic_favorite)
                favoriteCount.setTextColor(Color.parseColor("#999999"))
            }

            bindCountView(commentCount, post.comment)
            if (post.commentCurrentUser) {
                commentIcon.setBackgroundResource(R.drawable.ic_chat_bubble_active)
                commentCount.setTextColor(Color.parseColor("#2196F3"))
            } else {
                commentIcon.setBackgroundResource(R.drawable.ic_chat_bubble)
                commentCount.setTextColor(Color.parseColor("#999999"))
            }

            bindCountView(shareCount, post.share)
            if (post.shareCurrentUser) {
                shareIcon.setBackgroundResource(R.drawable.ic_share_active)
                shareCount.setTextColor(Color.parseColor("#4CAF50"))
            } else {
                shareIcon.setBackgroundResource(R.drawable.ic_share)
                shareCount.setTextColor(Color.parseColor("#999999"))
            }

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