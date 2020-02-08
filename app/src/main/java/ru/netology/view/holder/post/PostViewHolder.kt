package ru.netology.view.holder.post

import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.netology.activity.R
import ru.netology.view.adapter.PostAdapter
import ru.netology.model.Post
import java.time.format.DateTimeFormatter

open class PostViewHolder(postAdapter: PostAdapter, view: View) :
    ABaseViewHolder(postAdapter, view) {
    private val favoriteIcon: ImageButton = itemView.findViewById(R.id.favoriteIcon)
    private val favoriteCount: TextView = itemView.findViewById(R.id.favoriteCount)
    private val commentIcon: ImageButton = itemView.findViewById(R.id.commentIcon)
    private val commentCount: TextView = itemView.findViewById(R.id.commentCount)
    private val shareIcon: ImageButton = itemView.findViewById(R.id.shareIcon)
    private val shareCount: TextView = itemView.findViewById(R.id.shareCount)

    init {
        with(itemView) {
            this.setOnLongClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    adapter.posts.removeAt(adapterPosition)
                    adapter.notifyItemRemoved(adapterPosition)
                }

                true
            }

            favoriteIcon.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    var post = adapter.posts[adapterPosition]

                    post = post.copy(
                        favorite = if (post.favoriteCurrentUser) post.favorite - 1 else post.favorite + 1,
                        favoriteCurrentUser = !post.favoriteCurrentUser
                    )
                    adapter.posts[adapterPosition] = post
                    adapter.notifyItemChanged(adapterPosition)
                }
            }

            commentIcon.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    var post = adapter.posts[adapterPosition]

                    post = post.copy(
                        comment = if (post.commentCurrentUser) post.comment - 1 else post.comment + 1,
                        commentCurrentUser = !post.commentCurrentUser
                    )
                    adapter.posts[adapterPosition] = post
                    adapter.notifyItemChanged(adapterPosition)
                }
            }

            shareIcon.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    var post = adapter.posts[adapterPosition]

                    if (!post.shareCurrentUser) {
                        post = post.copy(
                            share = post.share + 1,
                            shareCurrentUser = true
                        )

                        context.startActivity(
                            Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "${post.createdUser} (${post.createTime.format(
                                        DateTimeFormatter.ofPattern("dd MMMM uuuu")
                                    )})\n" + post.content
                                )
                                type = "text/plain"
                            }
                        )

                        adapter.posts[adapterPosition] = post
                        adapter.notifyItemChanged(adapterPosition)
                    }
                }
            }
        }
    }

    override fun bind(post: Post) {
        super.bind(post)
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
    }
}