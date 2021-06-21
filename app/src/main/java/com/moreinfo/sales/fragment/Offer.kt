package com.moreinfo.sales.fragment

import com.moreinfo.sales.R

class Offer : BaseFragment() {

    companion object{
        const val TAG = "Offer"
    }

    override fun getFragmentTitle(): String {
        return "Offer"
    }

    override fun getLayoutId(): Int {
        return R.layout.offer_layout
    }

    override fun initUI() {

    }
}