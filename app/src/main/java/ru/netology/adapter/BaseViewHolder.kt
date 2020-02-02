package ru.netology.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.netology.model.Post

abstract class BaseViewHolder(val adapter: PostAdapter, view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(post: Post)

    protected fun bindCountView(
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