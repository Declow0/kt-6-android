package ru.netology.myapplication

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.model.Post
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val post = Post("Netology", "First post in our network!")
        post.comment = 10
        post.share = 20
//        post.changeComment()
        post.changeFavorite()
//        post.changeShare()

        timestamp.text = post.createTime.format(DateTimeFormatter.ofPattern("dd MMMM uuuu"))
        postContent.text = post.content

        val isVisible = { text: CharSequence ->
            if (text == "0" || text.isBlank()) {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }
        }

        favoriteCount.text = post.favorite.toString()
        favoriteCount.visibility = isVisible(favoriteCount.text)
        if (post.favoriteByMe) {
            favoriteIcon.setBackgroundResource(R.drawable.ic_favorite_active)
            favoriteCount.setTextColor(Color.parseColor("#F06292"))
        } else {
            favoriteIcon.setBackgroundResource(R.drawable.ic_favorite)
            favoriteCount.setTextColor(-1979711488)
        }

        commentCount.text = post.comment.toString()
        commentCount.visibility = isVisible(commentCount.text)
        if (post.commentByMe) {
            commentIcon.setBackgroundResource(R.drawable.ic_chat_bubble_active)
            commentCount.setTextColor(Color.parseColor("#2196F3"))
        } else {
            commentIcon.setBackgroundResource(R.drawable.ic_chat_bubble)
            commentCount.setTextColor(-1979711488)
        }

        shareCount.text = post.share.toString()
        shareCount.visibility = isVisible(shareCount.text)
        if (post.shareByMe) {
            shareIcon.setBackgroundResource(R.drawable.ic_share_active)
            shareCount.setTextColor(Color.parseColor("#4CAF50"))
        } else {
            shareIcon.setBackgroundResource(R.drawable.ic_share)
            shareCount.setTextColor(-1979711488)
        }
    }
}
