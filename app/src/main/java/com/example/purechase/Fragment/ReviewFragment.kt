package com.example.purechase.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purechase.Adapter.ReviewAdapter
import com.example.purechase.Domain.ReviewDomain
import com.example.purechase.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.Query

class ReviewFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList(view)
    }

    private fun initList(view: View) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Review")
        val list = ArrayList<ReviewDomain>()
        val query = myRef.orderByChild("ItemId").equalTo(4.toDouble()) // Convert Int to Double

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        list.add(issue.getValue(ReviewDomain::class.java)!!)
                    }
                    val descTxt = view.findViewById<RecyclerView>(R.id.reviewView)
                    if (list.isNotEmpty()) {
                        descTxt.adapter = ReviewAdapter(list)
                        descTxt.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
// Handle error
            }
        })
    }

}