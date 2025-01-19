package com.example.purechase.Activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.purechase.Adapter.CartAdapter
import com.example.purechase.Helper.ManagmentCart
import com.example.purechase.Helper.ChangeNumberItemsListener
import com.example.purechase.R
import com.example.purechase.databinding.ActivityCartBinding

class CartActivity : BaseActivity() {
    private lateinit var binding: ActivityCartBinding
    private var tax: Double = 0.0
    private lateinit var managmentCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        managmentCart = ManagmentCart(this)
        calculatorCart()
        setVariable()
        initCartList()

        val checkoutButton: Button = findViewById(R.id.checkOutBtn)
        checkoutButton.setOnClickListener {
            checkout()
        }

        val applyButton: Button = findViewById(R.id.applybutton)
        applyButton.setOnClickListener {
            applyDiscount()
        }
    }

    private fun initCartList() {
        val cartItems = managmentCart.getListCart()
        if (cartItems.isEmpty()) {
            binding.emptyTxt.visibility = View.VISIBLE
            binding.scrollViewCart.visibility = View.GONE
        } else {
            binding.emptyTxt.visibility = View.GONE
            binding.scrollViewCart.visibility = View.VISIBLE
        }

        binding.cartView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.cartView.adapter = CartAdapter(cartItems, this, object : ChangeNumberItemsListener {
            override fun changed() {
                calculatorCart()
            }
        })
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener { finish() }
    }

    private fun calculatorCart(discount: Double = 0.0) {
        val percentTax = 0.02
        val delivery = 10.0
        val totalFee = managmentCart.getTotalFee()
        val discountedFee = totalFee * (1 - discount)
        tax = Math.round(discountedFee * percentTax * 100.0) / 100.0
        val total = Math.round((discountedFee + tax + delivery) * 100) / 100.0
        val itemTotal = Math.round(discountedFee * 100) / 100.0

        binding.totalFeeTxt.text = "$itemTotal dt"
        binding.taxTxt.text = "$tax dt"
        binding.deliveryTxt.text = "$delivery dt"
        binding.totalTxt.text = "$total dt"
    }

    private fun checkout() {
        managmentCart.clearCart()


        initCartList()
        calculatorCart()
    }

    private fun applyDiscount() {
        val discount = 0.10 // 10% discount
        calculatorCart(discount)
    }
}
