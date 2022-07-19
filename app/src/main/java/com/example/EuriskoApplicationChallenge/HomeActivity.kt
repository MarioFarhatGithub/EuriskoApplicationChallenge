package com.example.EuriskoApplicationChallenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.EuriskoApplicationChallenge.ui.NewsActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btn_news.setOnClickListener {
            val x = Intent(this,NewsActivity::class.java)
            startActivity(x)
        }

        btn_more.setOnClickListener {
            val x = Intent(this,MoreActivity::class.java)
            startActivity(x)
        }

        btn_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}