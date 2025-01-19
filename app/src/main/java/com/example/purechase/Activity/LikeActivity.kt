package com.example.purechase.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purechase.Domain.ItemsDomain
import com.example.purechase.R
import com.google.firebase.database.*

class LikeActivity : AppCompatActivity() {

    private lateinit var likedItemsRecyclerView: RecyclerView
    private lateinit var likedItemsAdapter: LikedItemsAdapter
    private val likedItems = mutableListOf<ItemsDomain>()
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_like)

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, insets ->
            val statusBarHeight = insets.systemWindowInsetTop
            view.setPadding(0, statusBarHeight, 0, 0)
            insets
        }

        database = FirebaseDatabase.getInstance()
        likedItemsRecyclerView = findViewById(R.id.likedItemsRecyclerView)
        likedItemsRecyclerView.layoutManager = LinearLayoutManager(this)
        likedItemsAdapter = LikedItemsAdapter(likedItems)
        likedItemsRecyclerView.adapter = likedItemsAdapter

        fetchLikedItems()
    }

    private fun fetchLikedItems() {
        val likedItemsRef = database.getReference("LikedItems")
        likedItemsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        likedItems.add(issue.getValue(ItemsDomain::class.java)!!)
                    }
                    likedItemsAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
// Handle error if needed
            }
        })
    }
}