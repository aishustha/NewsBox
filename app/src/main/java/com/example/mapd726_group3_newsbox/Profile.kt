package com.example.mapd726_group3_newsbox

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.mapd726_group3_newsbox.adapter.RecyclerViewAdapter
import com.example.mapd726_group3_newsbox.databinding.ActivityProfileBinding
import com.example.mapd726_group3_newsbox.adapter.ViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Profile : AppCompatActivity() {


    //database reference for reading from database
    private lateinit var database:  FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth

    //ViewBinding
    private lateinit var binding: ActivityProfileBinding

    //ActionBar and nav
    private  lateinit var actionBar: ActionBar
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController

    //other variables
    private lateinit var articleArrayList: ArrayList<Article>
    private lateinit var article: Article
    // getting the recyclerview by its id
    private lateinit var recyclerview: RecyclerView

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
        database= FirebaseFirestore.getInstance()

        /*********************************    RECYCLERVIEW     *************************/
        // initializig needed variables
        articleArrayList = ArrayList<Article>()
        article = Article(R.drawable.default_article,"","","")
        recyclerview = findViewById<RecyclerView>(R.id.rNewsList)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        getBbcData()

        /*********************************   RECYCLERVIEW_END    *************************/


        /**********************************   BUTTONS    ************************/
//        val viewBtn = findViewById<Button>(R.id.view_btn)
//        viewBtn.setOnClickListener {
//            val intent = Intent(this@Profile, Detail::class.java)
//            startActivity(intent)
//        }

        //handle click, logout
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
        /**********************************   BUTTONS END   ************************/


        /**********************************   TABS AND HEADINGS    ************************/

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager =findViewById<ViewPager2>(R.id.view_pager_2)

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            when(position) {
                0-> {
                    tab.text="CNN  News"
                }
                1-> {
                    tab.text="BBC  News"
                }
                2-> {
                    tab.text="FOX News"
                }
//                3-> {
//                    tab.text="Fourth"
//                }
//                4-> {
//                    tab.text="Fifth"
//                }
            }
        }.attach()

        /**********************************   TABS AND HEADINGS END   ************************/

        //Bottom Navigation
        navController = this.findNavController(R.id.hostFragment)
        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.setupWithNavController(navController)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navBarView: NavigationView = findViewById(R.id.navigation_view)
        //Navigation up button

        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        //Drawer Navigation
       NavigationUI.setupWithNavController(navBarView, navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }



    //gets list from collections where category matches the search string cat


    //gets list of values from BBC sccrapedFeed
    private fun getBbcData()
    {
        database= FirebaseFirestore.getInstance()

        val list = database.collection("ScrapedFeed").document("BBC News - World")
            .get().addOnSuccessListener {
                if (it.exists()) {
                    val data = it.data
//                    binding.data.text = data.toString()

                    // binding.data.text = data.toString()
                    data?.let {
                        for ((key, value) in data) {
                            val v = value as Map<*, *>
                            val title = v["title"]as String
                            //Log.d(TAG, "article t "+article.title)
                            val body = v["summary"]as String
                            //Log.d(TAG, "article b "+article.body)
                            val content = v["content_html"] as String
                            //Log.d(TAG, "article s "+article.source)
                            val datePublished = v["date_published"]
//                            Log.d(TAG, "$key -> $datePublished")
                            val guid = v["guid"]
//                            Log.d(TAG, "$key -> $guid")
                            val url = v["url"] as String
//                            Log.d(TAG, "$key -> $url")

                            val article = Article(R.drawable.default_article,title, body, url)
                            articleArrayList.add(article)
                        }
                        val c = articleArrayList.count()

                        // This will pass the ArrayList to our Adapter
                        val recyclerViewAdapter = RecyclerViewAdapter(articleArrayList)
                        recyclerview.adapter = recyclerViewAdapter
                    }
                }
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
    }

    //gets list of values from CNN sccrapedFeed
    private fun getCnnData()
    {
        database= FirebaseFirestore.getInstance()

        val list = database.collection("ScrapedFeed").document("CNN.com - RSS Channel - App International Edition")
            .get().addOnSuccessListener {
                if (it.exists()) {
                    val data = it.data
//                    binding.data.text = data.toString()

                    // binding.data.text = data.toString()
                    data?.let {
                        for ((key, value) in data) {
                            val v = value as Map<*, *>
                            val title = v["title"] as String
                            Log.d(TAG, "$key -> $title")
                            val summary = v["summary"] as String
                            Log.d(TAG, "$key -> $summary")
                            val contentHtml = v["content_html"] as String
                            Log.d(TAG, "$key -> $contentHtml")
                            val datePublished = v["date_published"] as String
                            Log.d(TAG, "$key -> $datePublished")
                            val guid = v["guid"] as String
                            Log.d(TAG, "$key -> $guid")
                            val url = v["url"] as String
                            Log.d(TAG, "$key -> $url")
                            val article = Article(R.drawable.default_article,title, summary, url)
                            articleArrayList.add(article)
                        }
                        val c = articleArrayList.count()

                        // This will pass the ArrayList to our Adapter
                        val recyclerViewAdapter = RecyclerViewAdapter(articleArrayList)
                        recyclerview.adapter = recyclerViewAdapter
                    }
                }
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
    }


    //gets list of values from CNN sccrapedFeed
    private fun getFoxData()
    {
        database= FirebaseFirestore.getInstance()
        val list = database.collection("ScrapedFeed").document("FOX News")
            .get().addOnSuccessListener {
                if (it.exists()) {
                    val data = it.data
 //                   binding.data.text = data.toString()

                    // binding.data.text = data.toString()
                    data?.let {
                        for ((key, value) in data) {
                            val v = value as Map<*, *>
                            val title = v["title"] as String
                            Log.d(TAG, "$key -> $title")
                            val summary = v["summary"] as String
                            Log.d(TAG, "$key -> $summary")
                            val contentHtml = v["content_html"] as String
                            Log.d(TAG, "$key -> $contentHtml")
                            val datePublished = v["date_published"] as String
                            Log.d(TAG, "$key -> $datePublished")
                            val guid = v["guid"] as String
                            Log.d(TAG, "$key -> $guid")
                            val url = v["url"] as String
                            Log.d(TAG, "$key -> $url")
                            val article = Article(R.drawable.default_article,title, summary, url)
                            articleArrayList.add(article)
                        }
                        val c = articleArrayList.count()

                        // This will pass the ArrayList to our Adapter
                        val recyclerViewAdapter = RecyclerViewAdapter(articleArrayList)
                        recyclerview.adapter = recyclerViewAdapter
                    }
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
//                    binding.data.text = data.toString()
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


    private fun checkUser() {
        //check user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            //user not null, user is logged in, get user info
            val email = firebaseUser.email

            //set to text view
//            binding.emailTv.text = email
        }

        else {
            //user is null, user is not logged in, goto activity
            startActivity(Intent(this, SignIn::class.java))
            finish()
        }
    }
}
