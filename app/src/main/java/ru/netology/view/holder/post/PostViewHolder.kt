package ru.netology.view.holder.post

import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.netology.activity.R
import ru.netology.model.Post
import ru.netology.view.adapter.PostAdapter
import java.time.format.DateTimeFormatter

open class PostViewHolder(postAdapter: PostAdapter, view: View) :
    ABaseViewHolder(postAdapter, view) {
    private val viewsCount: TextView = itemView.findViewById(R.id.viewsCount)
    private val favoriteIcon: ImageButton = itemView.findViewById(R.id.favoriteIcon)
    private val favoriteCount: TextView = itemView.findViewById(R.id.favoriteCount)
    private val repostIcon: ImageButton = itemView.findViewById(R.id.repostIcon)
    private val repostCount: TextView = itemView.findViewById(R.id.repostCount)
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
                        favorite = if (post.favoriteByMe) post.favorite - 1 else post.favorite + 1,
                        favoriteByMe = !post.favoriteByMe
                    )
                    adapter.posts[adapterPosition] = post
                    adapter.notifyItemChanged(adapterPosition)
                }
            }

            repostIcon.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    var post = adapter.posts[adapterPosition]

                    // TODO: repost API
                    adapter.posts[adapterPosition] = post
                    adapter.notifyItemChanged(adapterPosition)
                }
            }

            shareIcon.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    var post = adapter.posts[adapterPosition]

                    if (!post.shareByMe) {
                        post = post.copy(
                            share = post.share + 1,
                            shareByMe = true
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
        bindCountView(viewsCount, post.views)

        bindCountView(favoriteCount, post.favorite)
        if (post.favoriteByMe) {
            favoriteIcon.setBackgroundResource(R.drawable.ic_favorite_active)
            favoriteCount.setTextColor(this.itemView.resources.getColor(R.color.activeFavorite))
        } else {
            favoriteIcon.setBackgroundResource(R.drawable.ic_favorite)
            favoriteCount.setTextColor(this.itemView.resources.getColor(R.color.colorSecondaryText))
        }

        bindCountView(repostCount, post.repost)

        bindCountView(shareCount, post.share)
        if (post.shareByMe) {
            shareIcon.setBackgroundResource(R.drawable.ic_share_active)
            shareCount.setTextColor(this.itemView.resources.getColor(R.color.activeShare))
        } else {
            shareIcon.setBackgroundResource(R.drawable.ic_share)
            shareCount.setTextColor(this.itemView.resources.getColor(R.color.colorSecondaryText))
        }
    }
}