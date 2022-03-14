package com.example.mapd726_group3_newsbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mapd726_group3_newsbox.adapter.RecyclerViewAdapter

class Detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail2)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class NewsArticle
        val data = ArrayList<Article>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            //data.add(Article("Item " + i,"Item 2","Item 3"))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = RecyclerViewAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_detail)
//    }
}