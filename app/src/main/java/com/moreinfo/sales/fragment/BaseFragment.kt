package com.moreinfo.sales.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.moreinfo.sales.DashboardActivity

abstract class BaseFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(getLayoutId(),container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as DashboardActivity).supportActionBar?.title = getFragmentTitle()
        initUI()
    }

    abstract fun getFragmentTitle(): String
    abstract fun getLayoutId(): Int
    abstract fun initUI()
}