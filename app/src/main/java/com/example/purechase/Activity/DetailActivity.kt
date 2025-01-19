package com.example.purechase.Activity

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purechase.Adapter.SizeAdapter
import com.example.purechase.Adapter.SliderAdapter
import com.example.purechase.Domain.ItemsDomain
import com.example.purechase.Fragment.DescriptionFragment
import com.example.purechase.Fragment.ReviewFragment
import com.example.purechase.Fragment.SoldFragment
import com.example.purechase.Helper.ManagmentCart
import com.example.purechase.databinding.ActivityDetailBinding
import com.example.purechase.domains.SliderItems

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsDomain
    private var numberOrder: Int = 1
    private lateinit var managmentCart: ManagmentCart
    private val slideHandler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        managmentCart = ManagmentCart(context = this)
        getBundles()
        initBanners()
        initSize()
        setupViewPager()
    }

    private fun initSize() {
        val list = ArrayList<String>()
        list.add("S")
        list.add("M")
        list.add("L")
        list.add("XL")
        list.add("XXL")

        binding.recyclerSize.adapter = SizeAdapter(list, this)
        binding.recyclerSize.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initBanners() {
        val sliderItems = arrayListOf<SliderItems>()
        for (i in 0 until item.picUrl.size) {
            sliderItems.add(SliderItems(item.picUrl[i]))
        }
        binding.viewPageSlider.adapter = SliderAdapter(sliderItems, binding.viewPageSlider)
        binding.viewPageSlider.clipToPadding = false
        binding.viewPageSlider.clipChildren = false
        binding.viewPageSlider.offscreenPageLimit = 3
        binding.viewPageSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun getBundles() {
        item = intent.getSerializableExtra("object") as ItemsDomain
        binding.titleTxt.text = item.title
        binding.priceTxt.text = "${item.price}dt"
        binding.ratingBar.rating = item.rating.toFloat()
        binding.ratingTxt.text = "${item.rating} Rating"
        binding.addTocartBtn.setOnClickListener {
            item.numberInCart = numberOrder
            managmentCart.insertFood(item)
        }

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        val tab1 = DescriptionFragment()
        val tab2 = ReviewFragment()
        val tab3 = SoldFragment()
        val bundle1 = Bundle()
        val bundle2 = Bundle()
        val bundle3 = Bundle()

        bundle1.putString("description", item.description)
        tab1.arguments = bundle1
        tab2.arguments = bundle2
        tab3.arguments = bundle3

        adapter.addFrag(tab1, "Descriptions")
        adapter.addFrag(tab2, "Reviews")
        adapter.addFrag(tab3, "Sold")

        binding.viewpager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewpager)
    }


}
private class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun addFrag(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }
}
