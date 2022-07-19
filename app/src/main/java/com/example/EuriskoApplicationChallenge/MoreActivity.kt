package com.example.EuriskoApplicationChallenge

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.GenericTransitionOptions.with
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.with
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.with
import com.bumptech.glide.module.AppGlideModule
import com.example.EuriskoApplicationChallenge.more.AboutusActivity
import com.example.EuriskoApplicationChallenge.more.PasswordActivity
import com.example.EuriskoApplicationChallenge.more.ProfileActivity
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_home.btn_logout
import kotlinx.android.synthetic.main.activity_more.*
import java.io.File
import java.io.InputStream


class MoreActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)



        btn_profile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        btn_password.setOnClickListener {
            startActivity(Intent(this, PasswordActivity::class.java))
        }

        btn_aboutus.setOnClickListener {
            startActivity(Intent(this, AboutusActivity::class.java))
        }

        btn_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }



        val filename = FirebaseAuth.getInstance().uid.toString()
        val imageref = FirebaseStorage.getInstance().getReference("/images/$filename.jpg")
        val imageView = findViewById<ShapeableImageView>(R.id.profileIV)
        GlideApp.with(this).load(imageref).into(imageView)


    }
}
@GlideModule
class MyAppGlideModule: AppGlideModule(){
    override fun registerComponents(
        context: android.content.Context,
        glide: Glide,
        registry: Registry
    ) {
        super.registerComponents(context, glide, registry)
        registry.append(
            StorageReference::class.java, InputStream::class.java,
            FirebaseImageLoader.Factory()
        )
    }
}

