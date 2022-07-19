package com.example.EuriskoApplicationChallenge.more

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.EuriskoApplicationChallenge.MainActivity
import com.example.EuriskoApplicationChallenge.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_password.*

class PasswordActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        auth = FirebaseAuth.getInstance()

        btn_change_password.setOnClickListener {
            changePassword()
        }
    }

    private fun changePassword(){

        if (current_password.text.isNotEmpty() &&
                new_password.text.isNotEmpty() &&
                confim_password.text.isNotEmpty()){
            if(new_password.text.toString().equals(confim_password.text.toString())){

                val user = auth.currentUser
                if(user !=null && user.email != null){
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!,current_password.text.toString())

                    user?.reauthenticate(credential)?.addOnCompleteListener{
                        if(it.isSuccessful){
                            Toast.makeText(this,"Re-Authentication success",Toast.LENGTH_SHORT).show()

                            user?.updatePassword(new_password.text.toString())?.addOnCompleteListener { task ->
                                if (task.isSuccessful){
                                    Toast.makeText(this,"Password Changed Successfully",Toast.LENGTH_SHORT).show()
                                auth.signOut()
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                }
                            }




                        } else {
                            Toast.makeText(this,"Re-Authentication failed",Toast.LENGTH_SHORT).show()
                        }

                    }


                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                }

            } else {
                Toast.makeText(this,"Passwords mismatching.", Toast.LENGTH_SHORT).show()

            }
        } else {
            Toast.makeText(this,"Please Enter all fields.", Toast.LENGTH_SHORT).show()
        }
    }
}