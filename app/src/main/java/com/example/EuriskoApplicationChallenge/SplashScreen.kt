package com.example.EuriskoApplicationChallenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.NonNull
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth? = null
    var mAuthListener: FirebaseAuth.AuthStateListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        iv_ss.alpha = 0f
        tv_em.alpha= 0f
        tv_em.animate().setDuration(3000).alpha(1f)
        iv_ss.animate().setDuration(3000).alpha(1f).withEndAction {
            val x = Intent(this, MainActivity::class.java)
            startActivity(x)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }

    }
}