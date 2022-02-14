package com.example.mapd726_group3_newsbox

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import com.example.mapd726_group3_newsbox.databinding.ActivityExploreBinding
import com.example.mapd726_group3_newsbox.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class Profile : AppCompatActivity() {
    //database reference for reading from database
    //private lateinit var database: DatabaseReference
    private lateinit var database:  FirebaseFirestore


    //ViewBinding
    private lateinit var binding: ActivityProfileBinding

    //ActionBar
    private  lateinit var actionBar: ActionBar

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Explore"

        //init firebasea auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //connection for reading from database
        //database = Firebase.database.reference
        database= FirebaseFirestore.getInstance()

        //handle click, logout
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
        getArticle()
    }

    private fun getArticle()
    {
        database.collection("Articles")
            .whereEqualTo("category", "space")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
//        // Create a reference to the cities collection
//        val spaceCategory = database.collection("Articles")
//        // Create a query against the collection.
//        val query = spaceCategory.whereEqualTo("category", "space").get()
//        println(query)

       // database.child("Articles").child("category").equals("space")

//        database.child("Articles").child("category").get().addOnSuccessListener {
//            Log.i("firebase", "Got value ${it.}")
//        }.addOnFailureListener{
//            Log.e("firebase", "Error getting data", it)
//        }
    }

    private fun checkUser() {
        //check user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            //user not null, user is logged in, get user info
            val email = firebaseUser.email

            //set to text view
            binding.emailTv.text = email
        }

        else {
            //user is null, user is not logged in, goto activity
            startActivity(Intent(this, SignIn::class.java))
            finish()
        }
    }
}