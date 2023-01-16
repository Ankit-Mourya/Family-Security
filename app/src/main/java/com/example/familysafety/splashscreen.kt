package com.example.familysafety

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Sharedpref.init(this)

        val isUserLoggedIn = Sharedpref.getBoolean(PrefContants.IS_USER_LOGGED_IN)
        if (isUserLoggedIn){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        else{
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

    }
}