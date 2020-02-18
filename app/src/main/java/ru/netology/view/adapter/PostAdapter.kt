package ru.netology.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.activity.R
import ru.netology.model.Post
import ru.netology.model.PostType
import ru.netology.view.holder.post.ABaseViewHolder
import ru.netology.view.holder.post.InnerViewHolder
import ru.netology.view.holder.post.PostViewHolder
import ru.netology.view.holder.post.RepostViewHolder

const val REGULAR_POST = 0
const val RE_POST = 1
const val INNER_POST = 2

class PostAdapter(
    postList: MutableList<Post>,
    commercialList: MutableList<Post> = mutableListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val posts: MutableList<Post> = addCommercialToPostList(postList, commercialList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            INNER_POST -> InnerViewHolder(
                this,
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.inner_elem, parent, false)
            )
            RE_POST -> RepostViewHolder(
                this,
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.repost_elem, parent, false)
            )
            else -> PostViewHolder(
                this,
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.post_elem, parent, false)
            )
        }

    override fun getItemCount(): Int = posts.size

    override fun getItemViewType(position: Int): Int =
        when {
            posts[position].type.contains(PostType.INNER) -> INNER_POST
            posts[position].type.contains(PostType.REPOST) -> RE_POST
            else -> REGULAR_POST
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ABaseViewHolder) holder.bind(posts[position])
    }

    private fun addCommercialToPostList(
        postList: MutableList<Post>,
        commercialList: MutableList<Post>
    ): MutableList<Post> {
        if (commercialList.size == 0) {
            return postList
        }

        val shufflePost = ArrayList<Post>(postList.size + postList.size / 3)
        loop@ for (i in 0..(postList.size / 3)) {
            for (j in 0..2) {
                val index = i * 3 + j
                if (index >= postList.size) {
                    break@loop
                }
                shufflePost.add(postList[index])
            }
            shufflePost.add(commercialList[i % commercialList.size])
        }
        return shufflePost
    }
}