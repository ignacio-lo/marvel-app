package com.example.marvelapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 123
    }

    private lateinit var authUser: FirebaseAuth
    private lateinit var listener: FirebaseAuth.AuthStateListener
    private lateinit var providers: ArrayList<AuthUI.IdpConfig>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
    }

    override fun onStart() {
        super.onStart()
        authUser.addAuthStateListener(listener)
    }

    override fun onStop() {
        if (listener != null) authUser.removeAuthStateListener(listener)

        super.onStop()
    }

    private fun init() {
        providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build()
        )

        authUser = FirebaseAuth.getInstance()

        listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser != null) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers)
                        .build(),
                    RC_SIGN_IN
                )
            }
        }
    }
}