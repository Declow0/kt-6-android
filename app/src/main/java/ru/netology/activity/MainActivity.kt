package ru.netology.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import ru.netology.model.Post
import ru.netology.repository.CommercialPostRepository
import ru.netology.repository.PostRepository
import ru.netology.view.adapter.PostAdapter

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(container) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            launch {
                var postList = mutableListOf<ru.netology.model.Post>()
                var commercialList = mutableListOf<ru.netology.model.Post>()

                withContext(Dispatchers.IO) {
                    postList = PostRepository.getList()
                    commercialList = CommercialPostRepository.getList()
                }
                loadContainer.visibility = View.GONE
                adapter = PostAdapter(postList, commercialList)
            }
        }
    }
}
