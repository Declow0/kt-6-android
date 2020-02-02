package ru.netology.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.model.Post
import ru.netology.model.PostType
import ru.netology.myapplication.R

open class PostAdapter(val postList: MutableList<Post>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            when (viewType) {
                2 -> {
                    InnerViewHolder(
                            this,
                            LayoutInflater
                                    .from(parent.context)
                                    .inflate(R.layout.inner_elem, parent, false)
                    )
                }
                1 -> RepostViewHolder(
                        this,
                        LayoutInflater
                                .from(parent.context)
                                .inflate(R.layout.repost_elem, parent, false)
                )
                else -> {
                    PostViewHolder(
                            this,
                            LayoutInflater
                                    .from(parent.context)
                                    .inflate(R.layout.post_elem, parent, false)
                    )
                }
            }

    override fun getItemCount(): Int = postList.size

    override fun getItemViewType(position: Int): Int =
            when {
                postList[position].type.contains(PostType.INNER) -> {
                    2
                }
                postList[position].type.contains(PostType.REPOST) -> {
                    1
                }
                else -> {
                    0
                }
            }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BaseViewHolder) {
            holder.bind(postList[position])
        }
    }
}