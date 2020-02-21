package ru.netology.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import ru.netology.model.Post
import ru.netology.repository.CommercialPostRepository
import ru.netology.repository.PostRepository
import ru.netology.util.unauthorized
import ru.netology.view.adapter.PostAdapter
import java.io.IOException
import java.net.HttpURLConnection

class FeedActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        val postRepository = PostRepository()

        // TODO fix BE for filter on server
        val commercialPostRepository = CommercialPostRepository()

        with(container) {
            layoutManager = LinearLayoutManager(this@FeedActivity)
            launch {
                var postList = mutableListOf<Post>()
                var commercialList = mutableListOf<Post>()

                try {
                    val postListResponse = postRepository.getList()
                    if (postListResponse.isSuccessful) {
                        postList = postListResponse.body()!!.toMutableList()
                    } else if (postListResponse.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        loadContainer.visibility = View.GONE
                        unauthorized()
                        finish()
                    }
                } catch (e: IOException) {
                    toast(R.string.network_error)
                }

                adapter = PostAdapter(postList, commercialList)
                loadContainer.visibility = View.GONE
            }
        }
    }
}
