package com.moreinfo.enginnerapp.fragment

import com.moreinfo.enginnerapp.R

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