package com.example.EuriskoApplicationChallenge

import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.EuriskoApplicationChallenge.more.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_password.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        link_already.setOnClickListener {
            onBackPressed()
        }

        btn_signup.setOnClickListener {
            when {

                TextUtils.isEmpty(et_su_fn.text.toString().trim{ it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please Enter First Name.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(et_su_ln.text.toString().trim{ it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please Enter Last Name.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(et_su_email.text.toString().trim{ it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please Enter E-mail.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(et_su_password.text.toString().trim{ it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please Enter Password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(et_su_conf_password.text.toString().trim{ it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please Confirm Password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {

                    if(et_su_password.text.toString().equals(et_su_conf_password.text.toString())) {


                        val firstname: String = et_su_fn.text.toString().trim { it <= ' ' }
                        val lastname: String = et_su_ln.text.toString().trim { it <= ' ' }
                        val email: String = et_su_email.text.toString().trim { it <= ' ' }
                        val password: String = et_su_password.text.toString().trim { it <= ' ' }

                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(
                                        this,
                                        "You have signed up successfully.",
                                        Toast.LENGTH_SHORT
                                    ).show()


                                    val uid = FirebaseAuth.getInstance().uid
                                    val ref =
                                        FirebaseDatabase.getInstance().getReference("/users/$uid")
                                    val user = Usersignup(firstname, lastname, email)
                                    ref.setValue(user)

                                    val intent = Intent(this, HomeActivity::class.java)
                                    startActivity(intent)
                                    finish()

                                } else {
                                    Toast.makeText(
                                        this,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(
                            this,
                            "Make Sure to Match Your Passwords.",
                            Toast.LENGTH_SHORT
                        ).show()

                    }


                }



            }
        }
    }
}

class Usersignup(val firstname: String,val lastname: String,val email: String)