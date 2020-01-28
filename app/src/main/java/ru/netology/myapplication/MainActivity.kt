package ru.netology.myapplication

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.model.Post
import ru.netology.service.Period
import ru.netology.service.intervalBetweenNowMessage
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    var post = Post(
        "Netology Group Company",
        "В чащах юга жил-был цитрус? Да, но фальшивый экземпляръ!",
        LocalDateTime.now().minus(2L, Period.DAYS.chronoUnit),
        2,
        0,
        10,
        location = 55.7765289 to 37.6749378
//        address = "ул. Нижняя Красносельская"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createTime.text = intervalBetweenNowMessage(post.createTime)
        userName.text = post.createdUser
        postContent.text = post.content


        updateCountView(favoriteCount, post.favorite)
        favoriteIcon.setOnClickListener { view ->
            if (view is ImageButton) {
                if (post.favoriteCurrentUser) {
                    post = post.copy(
                        favorite = post.favorite - 1,
                        favoriteCurrentUser = false
                    )

                    updateCountView(favoriteCount, post.favorite)
                    favoriteIcon.setBackgroundResource(R.drawable.ic_favorite)
                    favoriteCount.setTextColor(Color.parseColor("#999999"))
                } else {
                    post = post.copy(
                        favorite = post.favorite + 1,
                        favoriteCurrentUser = true
                    )

                    updateCountView(favoriteCount, post.favorite)
                    favoriteIcon.setBackgroundResource(R.drawable.ic_favorite_active)
                    favoriteCount.setTextColor(Color.parseColor("#F06292"))
                }
            }
        }

        updateCountView(commentCount, post.comment)
        commentIcon.setOnClickListener { view ->
            if (view is ImageButton) {
                if (post.commentCurrentUser) {
                    post = post.copy(
                        comment = post.comment - 1,
                        commentCurrentUser = false
                    )

                    updateCountView(commentCount, post.comment)
                    commentIcon.setBackgroundResource(R.drawable.ic_chat_bubble)
                    commentCount.setTextColor(Color.parseColor("#999999"))
                } else {
                    post = post.copy(
                        comment = post.comment + 1,
                        commentCurrentUser = true
                    )

                    updateCountView(commentCount, post.comment)
                    commentIcon.setBackgroundResource(R.drawable.ic_chat_bubble_active)
                    commentCount.setTextColor(Color.parseColor("#2196F3"))
                }
            }
        }

        updateCountView(shareCount, post.share)
        shareIcon.setOnClickListener { view ->
            if (view is ImageButton) {
                if (post.shareCurrentUser) {
                    post = post.copy(
                        share = post.share - 1,
                        shareCurrentUser = false
                    )

                    updateCountView(shareCount, post.share)
                    shareIcon.setBackgroundResource(R.drawable.ic_share)
                    shareCount.setTextColor(Color.parseColor("#999999"))
                } else {
                    post = post.copy(
                        share = post.share + 1,
                        shareCurrentUser = true
                    )

                    updateCountView(shareCount, post.share)
                    shareIcon.setBackgroundResource(R.drawable.ic_share_active)
                    shareCount.setTextColor(Color.parseColor("#4CAF50"))
                }
            }
        }

        geolocation.visibility =
            if (post.location != null || post.address.isNotBlank())
                View.VISIBLE
            else
                View.INVISIBLE
        geolocation.setOnClickListener {
            startActivity(
                Intent().apply {
                    action = Intent.ACTION_VIEW
                    if (post.location != null) {
                        data =
                            Uri.parse("geo:${post.location?.first},${post.location?.second}?z=19")
                    } else if (post.address.isNotBlank()) {
                        data = Uri.parse("geo:0,0?q=1600+${post.address}")
                    }
                }
            )
        }
    }

    private fun updateCountView(
        view: View,
        count: Long
    ) {
        if (view is TextView) {
            view.text = count.toString()
            view.visibility = if (count > 0) View.VISIBLE else View.INVISIBLE
        }
    }
}
