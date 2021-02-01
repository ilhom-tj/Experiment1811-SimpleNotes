package com.example.example1811.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.example1811.R
import com.example.example1811.Repository.Repository
import com.example.example1811.Repository.UserRepo
import com.example.example1811.databinding.ActivityLoginBinding
import com.example.example1811.db.NoteDataBase
import com.example.example1811.db.User.User
import com.example.example1811.db.User.UserDataBase
import com.example.example1811.model.NoteViewModel
import com.example.example1811.model.NoteViewModelFactory
import com.example.example1811.model.User.UserViewFactory
import com.example.example1811.model.User.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInApi
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var mAuth : FirebaseAuth
    private lateinit var gso : GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient


    private lateinit var noteViewModel: NoteViewModel


    companion object{
        private const val AuthRes = 777
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val dao = NoteDataBase.getInstance(this).noteDao
        val repository = Repository(dao)
        val factory = NoteViewModelFactory(repository)


        noteViewModel = ViewModelProvider(this,factory).get(NoteViewModel::class.java)

        mAuth = FirebaseAuth.getInstance()
        Log.e("user",mAuth.currentUser?.uid.toString())

        if (mAuth.currentUser?.uid.toString().equals("null")){

        }else{
            val intent : Intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        setContentView(R.layout.activity_login)


        mAuth = FirebaseAuth.getInstance()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)

        binding.signInButton.setOnClickListener{
            signInWithGoogle()
        }

        binding.signInNoneButton.setOnClickListener{
            signInWithNoAuth()
        }
    }

    fun signInWithGoogle(){
        val intent = googleSignInClient.signInIntent

        startActivityForResult(intent, AuthRes)
    }
    fun signInWithNoAuth(){
        val user : User = User(0,false)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AuthRes){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("Succes", "firebaseAuthWithGoogle:" + account.id)


                firebaseAuthWithGoogle(account.idToken!!)


            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("Error", "Google sign in failed", e)
                // [START_EXCLUDE]

                // [END_EXCLUDE]
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent : Intent = Intent(this,MainActivity::class.java)
                    //noteViewModel.getAllNotesFromFire()
                    startActivity(intent)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    // [START_EXCLUDE]
                    val view = binding.root
                    // [END_EXCLUDE]
                    Snackbar.make(view, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()

                }
            }
    }


}