package com.madchan.migratedatademo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.madchan.migratedatademo.R

class MainFragment : ViewPager2SupportFragment() {

    companion object{
        private const val TAB_MESSAGE = 0
        private const val TAB_CONTACT = 1
        private const val TAB_DISCOVER = 2
        private const val TAB_MINE = 3
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onTabConfigChanged(tab: TabLayout.Tab, position: Int) {
        tab.text = when (position) {
            TAB_MESSAGE -> "消息"
            TAB_CONTACT -> "联系人"
            TAB_DISCOVER -> "发现"
            else -> "我的"
        }
    }


    override fun createCollectionAdapter(fragment: Fragment) = object : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                TAB_MESSAGE -> MessageFragment()
                TAB_CONTACT -> ContactFragment()
                TAB_DISCOVER -> DiscoverFragment()
                else -> MineFragment()
            }
        }

    }
}