package com.example.EuriskoApplicationChallenge.more

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.example.EuriskoApplicationChallenge.MoreActivity
import com.example.EuriskoApplicationChallenge.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_profile.*
import okio.ByteString.Companion.toByteString
import java.io.ByteArrayOutputStream
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        btn_profile_choose.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)
        }

        btn_profile_save.setOnClickListener {
            uploadImageToFirebaseStorage()
            val x = Intent(this,MoreActivity::class.java)
            startActivity(x)
            finish()
        }

        btn_profile_back.setOnClickListener {
            onBackPressed()
        }

        val userID = FirebaseAuth.getInstance().uid.toString()
        database = FirebaseDatabase.getInstance().getReference("users")
        database.child(userID).get().addOnSuccessListener {
            if (it.exists()){
                val firstname = it.child("firstname").value
                val lastname = it.child("lastname").value
                val email = it.child("email").value

                profile_change_firstname.setText(firstname.toString())
                profile_change_lastname.setText(lastname.toString())
                profile_change_email.text = email.toString()

            }
        }


    }



    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){

            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            val bitmapDrawable = BitmapDrawable(bitmap)
            profile_imageview.setImageDrawable(bitmapDrawable)




        }
    }

private fun uploadImageToFirebaseStorage(){
    if (selectedPhotoUri ==null) return
    val filename = FirebaseAuth.getInstance().uid.toString()
    val imageref = FirebaseStorage.getInstance().getReference("/images/$filename.jpg")
    imageref.putFile(selectedPhotoUri!!).addOnSuccessListener {
            imageref.downloadUrl.addOnSuccessListener {
                saveUserToFirebaseDatabase(it.toString())
            }
        }


    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user = User(profile_change_firstname.text.toString(),profile_change_lastname.text.toString(),profile_change_email.text.toString(),profileImageUrl)
        ref.setValue(user)
    }
}

class User(val firstname: String,val lastname: String,val email:String, val profileImageUrl:String)