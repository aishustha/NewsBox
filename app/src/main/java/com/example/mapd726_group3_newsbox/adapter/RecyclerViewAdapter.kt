package com.example.mapd726_group3_newsbox.adapter

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mapd726_group3_newsbox.Article
import com.example.mapd726_group3_newsbox.R

class RecyclerViewAdapter (private val mList: List<Article>, var mContext:Context) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        //holder.imageView.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.title
        // sets the text to the textview from our itemHolder class
        holder.textView2.text = ItemsViewModel.body
        holder.textView3.text = ItemsViewModel.source

        // Button to open web view of the selected article
        holder.button_more.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            // Set flag to open new activity 
            openURL.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            openURL.data = Uri.parse(ItemsViewModel.source)
            mContext.startActivity(openURL)
            println(ItemsViewModel.source)

        }


    }




    // return the number of the items in the list
    override fun getItemCount(): Int
    {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
       // val imageView: ImageView = itemView.findViewWithTag(R.drawable.default_article)
        val textView: TextView = itemView.findViewById(R.id.articleTitle)
        val textView2: TextView = itemView.findViewById(R.id.articleBody)
        val textView3: TextView = itemView.findViewById(R.id.articleSource)

        val button_more: Button = itemView.findViewById(R.id.button_more)
        }

}



