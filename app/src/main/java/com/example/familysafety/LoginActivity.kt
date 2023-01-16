package com.example.familysafety

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.Identity.getSignInClient
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
   private val RC_SIGN_IN = 89
   private lateinit var googleSignInClient : GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this,gso)


    }
   fun SignIn(view: android.view.View) {
       val signinIntent = googleSignInClient.signInIntent
       startActivityForResult(signinIntent ,RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.w("requestcode","request code:${requestCode}")
        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account =task.getResult(ApiException::class.java)
            Log.w("signed up","welcome")
                firebaseAuthWithGoogle(account.idToken!!)
            }
            catch(e: ApiException){
                Log.w("Error message","Google sign in failed",e)
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken:String){

        val auth = FirebaseAuth.getInstance()
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                   Sharedpref.init(this)
                    Log.d("success","Sign in with credential :successful")

                    Sharedpref.putBoolean(PrefContants.IS_USER_LOGGED_IN,true)
                    val user = auth.currentUser
                    Log.d("fire88","firebaseAuthWithGoogle: ${user?.displayName}")
                   // startActivity(Intent(this,MainActivity::class.java))

                }
                else{
                    Log.w("failure","sign in with credential : failure", task.exception)

                }

            }
    }
}
