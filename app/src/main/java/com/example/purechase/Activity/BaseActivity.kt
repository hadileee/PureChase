package com.example.purechase.Activity

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.example.purechase.R
import com.google.firebase.database.FirebaseDatabase

open class BaseActivity : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        database = FirebaseDatabase.getInstance()
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, insets ->
            val statusBarHeight = insets.systemWindowInsetTop
            view.setPadding(0, statusBarHeight, 0, 0)
            insets
        }

    }
}