package com.example.mymusic.search.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

// 搜索结果页面adapter
class TabViewPagerAdapter(fm: FragmentManager, val item: List<Fragment>, val category: List<String>): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return item[position]
    }

    override fun getCount(): Int {
        return item.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return category[position]
    }
}