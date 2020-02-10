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

class PostAdapter(
    postList: MutableList<ru.netology.model.Post>,
    commercialList: MutableList<ru.netology.model.Post> = mutableListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val posts: MutableList<ru.netology.model.Post> = shufflePosts(postList, commercialList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            2 -> InnerViewHolder(
                this,
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.inner_elem, parent, false)
            )
            1 -> RepostViewHolder(
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
            posts[position].type.contains(ru.netology.model.PostType.INNER) -> 2
            posts[position].type.contains(ru.netology.model.PostType.REPOST) -> 1
            else -> 0
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ABaseViewHolder) holder.bind(posts[position])
    }

    private fun shufflePosts(
        postList: MutableList<ru.netology.model.Post>,
        commercialList: MutableList<ru.netology.model.Post>
    ): MutableList<ru.netology.model.Post> {
        val shufflePost = ArrayList<ru.netology.model.Post>(postList.size + postList.size / 3)
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