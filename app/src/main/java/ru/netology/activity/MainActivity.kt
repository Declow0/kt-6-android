package ru.netology.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import ru.netology.view.adapter.PostAdapter
import ru.netology.model.Post
import ru.netology.repository.repository

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(container) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            launch {
                var list = mutableListOf<Post>()
                withContext(Dispatchers.IO) {
                    list = repository.getList()
                }
                adapter = PostAdapter(list)
            }
        }
    }
}
