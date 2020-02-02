package ru.netology.adapter

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.post_elem.view.*
import ru.netology.model.Post
import ru.netology.myapplication.R
import ru.netology.service.intervalBetweenNowMessage
import java.time.format.DateTimeFormatter

class PostAdapter(val postList: MutableList<Post>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostViewHolder(
                this,
                LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.post_elem, parent, false)
        )
    }

    override fun getItemCount(): Int = postList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder as PostViewHolder) {
            bind(postList[position])
        }
    }
}

class PostViewHolder(private val adapter: PostAdapter, itemView: View) : RecyclerView.ViewHolder(itemView) {
    init {
        with(itemView) {
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

                        itemView.context.startActivity(
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

                    itemView.context.startActivity(
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

            interactive.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val post = adapter.postList[adapterPosition]
                    itemView.context.startActivity(
                            Intent().apply {
                                action = Intent.ACTION_VIEW
                                data = post.interactiveContent
                            }
                    )
                }
            }
        }
    }

    fun bind(post: Post) {
        with(itemView) {
            createTime.text = intervalBetweenNowMessage(post.createTime)
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

            geolocation.visibility =
                    if (post.location != null || post.address.isNotBlank())
                        View.VISIBLE
                    else
                        View.INVISIBLE

            interactive.visibility =
                    if (post.interactiveContent != null) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
        }
    }

    private fun bindCountView(
            view: View,
            count: Long
    ) {
        if (view is TextView) {
            view.text = count.toString()
            view.visibility =
                    if (count > 0)
                        View.VISIBLE
                    else
                        View.INVISIBLE
        }
    }
}