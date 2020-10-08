package com.madchan.migratedatademo.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.madchan.migratedatademo.R

abstract class ViewPager2SupportFragment : Fragment() {

    private lateinit var collectionAdapter: FragmentStateAdapter
    private lateinit var viewPager: ViewPager2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        collectionAdapter = createCollectionAdapter(this)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = collectionAdapter

        val tabLayout = view.findViewById(R.id.tab_layout) as TabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            onTabConfigChanged(tab, position)

        }.attach()

    }

    abstract fun onTabConfigChanged(tab : TabLayout.Tab, position: Int)

    abstract fun createCollectionAdapter(fragment: Fragment) : FragmentStateAdapter

}