package ru.netology.view.holder.post

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.netology.activity.R
import ru.netology.api.retrofit.RetrofitClient
import ru.netology.model.Period
import ru.netology.model.Post
import ru.netology.model.PostType
import ru.netology.repository.CommercialPostRepository
import ru.netology.repository.PostRepository
import ru.netology.service.LocalDateTimeService
import ru.netology.view.adapter.PostAdapter
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneOffset

abstract class ABaseViewHolder(val adapter: PostAdapter, view: View) :
    RecyclerView.ViewHolder(view), CoroutineScope by MainScope() {
    private val userName: TextView = itemView.findViewById(R.id.userName)
    private val createTime: TextView = itemView.findViewById(R.id.createTime)
    private val geolocation: ImageButton = itemView.findViewById(R.id.geolocation)
    private val postContent: TextView = itemView.findViewById(R.id.postContent)
    private val youtube: ImageButton = itemView.findViewById(R.id.youtube)
    private val commercial: ImageButton = itemView.findViewById(R.id.commercial)

    private val repost: RecyclerView? = itemView.findViewById(R.id.repost)

    private val postRepository = PostRepository()
    private val commercialPostRepository = CommercialPostRepository()

    init {
        with(itemView) {
            geolocation.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val post = adapter.posts[adapterPosition]

                    context.startActivity(
                        Intent().apply {
                            action = Intent.ACTION_VIEW
                            data = Uri.parse(
                                "geo:" +
                                        if (post.location != null) "${post.location!!.latitude},${post.location!!.longitude}" else "0,0" +
                                                if (post.address.isNotBlank()) "?q=${Uri.encode(post.address)}" else ""
                            )
                        }
                    )
                }
            }

            youtube.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val post = adapter.posts[adapterPosition]
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
                    val post = adapter.posts[adapterPosition]
                    context.startActivity(
                        Intent().apply {
                            action = Intent.ACTION_VIEW
                            data = Uri.parse(post.commercialContent!!.toURI().toString())
                        }
                    )
                }
            }
        }
    }

    open fun bind(post: Post) {
        createTime.text =
            if (post.type.contains(PostType.COMMERCIAL)) this.itemView.resources.getString(R.string.commercial_record)
            else {
                val (period, quantity) = LocalDateTimeService.betweenInterval(
                    post.createTime,
                    LocalDateTime.now(ZoneOffset.UTC)
                )
                when (period) {
                    Period.SECONDS -> this.itemView.resources.getQuantityString(
                        R.plurals.seconds_ago,
                        quantity.toInt()
                    )
                    Period.MINUTES -> this.itemView.resources.getQuantityString(
                        R.plurals.minutes_ago,
                        quantity.toInt(),
                        quantity.toInt()
                    )
                    Period.HOURS -> this.itemView.resources.getQuantityString(
                        R.plurals.hours_ago,
                        quantity.toInt(),
                        quantity.toInt()
                    )
                    Period.DAYS -> this.itemView.resources.getQuantityString(
                        R.plurals.days_ago,
                        quantity.toInt(),
                        quantity.toInt()
                    )
                    Period.WEEKS -> this.itemView.resources.getQuantityString(
                        R.plurals.weeks_ago,
                        quantity.toInt(),
                        quantity.toInt()
                    )
                    Period.MONTHS -> this.itemView.resources.getQuantityString(
                        R.plurals.months_ago,
                        quantity.toInt(),
                        quantity.toInt()
                    )
                    Period.YEARS -> this.itemView.resources.getQuantityString(
                        R.plurals.years_ago,
                        quantity.toInt(),
                        quantity.toInt()
                    )
                }
            }
        userName.text = post.createdUser
        postContent.text = post.content

        geolocation.visibility =
            if (post.type.contains(PostType.GEO_EVENT)) View.VISIBLE else View.INVISIBLE
//      TODO:
//          var url = URL("http://img.youtube.com/vi/${post.youtubeId}/maxresdefault.jpg")
//          interactive.background = Drawable.createFromStream()
        youtube.visibility =
            if (post.type.contains(PostType.YOUTUBE)) View.VISIBLE else View.GONE
        commercial.visibility =
            if (post.type.contains(PostType.COMMERCIAL)) View.VISIBLE else View.GONE
    }

    fun bindRepost(post: Post) {
        if (post.type.contains(PostType.REPOST)) {
            // TODO add exception check
            runBlocking {
                var reposted: Post? = null
                try {
                    val postResponse = postRepository.get(post.original)
                    if (postResponse.isSuccessful) {
                        reposted = postResponse.body()!!
                    } else {
                        Log.v(
                            this::class.java.simpleName,
                            "Can't get repost ${RetrofitClient.parseError(postResponse).error}"
                        )
                        repost!!.visibility = View.GONE
                    }
                } catch (e: IOException) {
                    Log.v(this::class.java.simpleName, "Can't get repost", e)
                    repost!!.visibility = View.GONE
                } finally {
                    if (reposted != null) {
                        with(repost!!) {
                            layoutManager = LinearLayoutManager(repost.context)
                            adapter = PostAdapter(
                                mutableListOf(reposted.copy(inner = true))
                            )
                        }
                    }
                }
            }
        }
    }

    protected fun bindCountView(
        view: View,
        count: Long
    ) {
        if (view is TextView) {
            view.text = count.toString()
            view.visibility = if (count > 0) View.VISIBLE else View.INVISIBLE
        }
    }
}