package com.example.mapd726_group3_newsbox

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mapd726_group3_newsbox.databinding.ActivityExploreBinding
import com.example.mapd726_group3_newsbox.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.core.RepoManager.clear
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Comment

class Profile : AppCompatActivity() {
    //database reference for reading from database
    //private lateinit var database: DatabaseReference
    private lateinit var database:  FirebaseFirestore
    private lateinit var db: DatabaseReference

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var articlearrayList: ArrayList<Article>


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

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //connection for reading from database
        db = Firebase.database.reference
        database= FirebaseFirestore.getInstance()
        getData()

        //handle click, logout
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

        userRecyclerView = findViewById(R.id.rNewsList)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        articlearrayList = arrayListOf<Article>()
        getDataList()
    }

    //gets list from collections where category matches the search string cat

    private fun getData()
    {
        database= FirebaseFirestore.getInstance()
        val list = database.collection("ScrapedFeed").document("BBC News - World")
            .get().addOnSuccessListener {
                if (it.exists()) {
                    val data = it.data
                    println(data)
                    //set to text view
                    binding.data.text = data.toString()
//                    data?.let {
//                        for ((key, value) in data) {
//                            val v = value as Map<*, *>
//                            val time = v["time"]
//                            Log.d(TAG, "$key -> $time")
//                        }
//                    }
                }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
    private fun getDataList()
    { database= FirebaseFirestore.getInstance()
        val list = database.collection("ScrapedFeed").document("BBC News - World")
            .get().addOnSuccessListener {
                if (it.exists()) {
                    val data = it.data
                    //println(data)

                    //set to text view
                    binding.data.text = data.toString()
                    data?.let {
                        for ((key, value) in data) {
                            val v = value as Map<*, *>
                            val time = v["time"]
                            Log.d(TAG, "$key -> $time")
                        }
                    }
                }
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
    }


    private fun getDatList()
    {
        val newsArticleList: MutableList<NewsArticle> = mutableListOf()

            val listListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    newsArticleList.clear()
                    dataSnapshot.children.mapNotNullTo(newsArticleList) { it.getValue<NewsArticle>(NewsArticle::class.java) }
                    println(newsArticleList)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadPost:onCancelled ${databaseError.toException()}")
                }
            }
            db.child("ScrapedFeed").child("BBC News - World").addListenerForSingleValueEvent(listListener)

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
