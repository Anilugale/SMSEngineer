package com.moreinfo.sales.fragment

import com.moreinfo.sales.R

class AboutFragment : BaseFragment() {

    companion object{
        const val TAG = "AboutFragment"
    }

    override fun getFragmentTitle(): String {
        return "About"
    }

    override fun getLayoutId(): Int {
        return R.layout.about_layout
    }

    override fun initUI() {

    }
}