package ru.netology.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.coroutines.*
import ru.netology.model.Post
import ru.netology.repository.CommercialPostRepository
import ru.netology.repository.PostRepository
import ru.netology.util.getAuthToken
import ru.netology.view.adapter.PostAdapter

class FeedActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        with(container) {
            layoutManager = LinearLayoutManager(this@FeedActivity)
            launch {
                var postList = mutableListOf<Post>()
                var commercialList = mutableListOf<Post>()

                withContext(Dispatchers.IO) {
                    postList = PostRepository.getList(getAuthToken())
                    commercialList = CommercialPostRepository.getList(getAuthToken())
                }
                loadContainer.visibility = View.GONE
                adapter = PostAdapter(postList, commercialList)
            }
        }
    }
}
