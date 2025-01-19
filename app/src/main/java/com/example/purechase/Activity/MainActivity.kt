package com.example.purechase.Activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.purechase.Adapter.CategoryAdapter
import com.example.purechase.Adapter.PopularAdapter
import com.example.purechase.Adapter.SliderAdapter
import com.example.purechase.Domain.CategoryDomain
import com.example.purechase.Domain.ItemsDomain
import com.example.purechase.databinding.ActivityMainBinding
import com.example.purechase.domains.SliderItems
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let {
                it.hide(WindowInsets.Type.systemBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    )
        }


        database = FirebaseDatabase.getInstance()

        initBanner()
        initCategory()
        initPopular()
        bottomNavigationCart()
        bottomNavigationUser()
        navigateToLikedItems()
    }

    private fun bottomNavigationCart() {
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }
    }

    private fun bottomNavigationUser() {
        binding.userBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, IntroActivity::class.java))
        }
    }

    private fun navigateToLikedItems() {
        binding.likepageid.setOnClickListener {
            startActivity(Intent(this@MainActivity, LikeActivity::class.java))
        }
    }

    private fun initPopular() {
        val myRef = database.getReference("Items")
        binding.progressBarPopular.visibility = View.VISIBLE
        val items = ArrayList<ItemsDomain>()
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        items.add(issue.getValue(ItemsDomain::class.java)!!)
                    }
                    if (items.isNotEmpty()) {
                        binding.recyclerViewPopular.layoutManager = GridLayoutManager(this@MainActivity, 2)
                        binding.recyclerViewPopular.adapter = PopularAdapter(items)
                    }
                    binding.progressBarPopular.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
// Handle error if needed
            }
        })
    }

    private fun initBanner() {
        val myRef = database.getReference("Banner")
        binding.progressBarBanner.visibility = View.VISIBLE
        val items = ArrayList<SliderItems>()
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        items.add(issue.getValue(SliderItems::class.java)!!)
                    }
                    banners(items)
                    binding.progressBarBanner.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
// Handle error if needed
            }
        })
    }

    private fun initCategory() {
        val myRef = database.getReference("Category")
        binding.progressBarCtegories.visibility = View.VISIBLE
        val items = ArrayList<CategoryDomain>()
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        items.add(issue.getValue(CategoryDomain::class.java)!!)
                    }
                    if (items.isNotEmpty()) {
                        binding.recyclerViewCategories.layoutManager = LinearLayoutManager(
                            this@MainActivity,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        binding.recyclerViewCategories.adapter = CategoryAdapter(items)
                    }
                    binding.progressBarCtegories.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
// Handle error if needed
            }
        })
    }

    private fun banners(items: ArrayList<SliderItems>) {
        binding.viewpagerSlider.adapter = SliderAdapter(items, binding.viewpagerSlider)
        binding.viewpagerSlider.clipToPadding = false
        binding.viewpagerSlider.clipChildren = false
        binding.viewpagerSlider.offscreenPageLimit = 3
        binding.viewpagerSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(48))
        }
        binding.viewpagerSlider.setPageTransformer(compositePageTransformer)
    }
}
