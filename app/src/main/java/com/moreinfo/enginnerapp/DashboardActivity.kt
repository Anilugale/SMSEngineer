package com.moreinfo.enginnerapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.moreinfo.enginnerapp.cache.DataCache
import com.moreinfo.enginnerapp.cache.UserCache
import com.moreinfo.enginnerapp.fragment.AboutFragment
import com.moreinfo.enginnerapp.fragment.ExistingUserFragment
import com.moreinfo.enginnerapp.fragment.Offer
import com.moreinfo.enginnerapp.fragment.Registration
import com.moreinfo.enginnerapp.util.RequestUtility
import kotlinx.android.synthetic.main.activity_dashbord.*
import kotlinx.android.synthetic.main.header_layout.view.*


class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashbord)
        setSupportActionBar(toolbar)
        toolbar.title = "Hello"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        setupDrawerContent()
        sycData()
        val drawerToggle = setupDrawerToggle()
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerToggle.syncState()
        drawerLayout.addDrawerListener(drawerToggle)
        attachExistingFragment()
    }

    fun attachExistingFragment() {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, ExistingUserFragment(), ExistingUserFragment.TAG)
            .commit()

    }

    private fun setupDrawerToggle(): ActionBarDrawerToggle {
        return ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
    }


    private fun sycData() {
        if (DataCache.cityList.isEmpty()) {
            RequestUtility.getCitys(this)
        }
        if (DataCache.stateList.isEmpty()) {
            RequestUtility.getStateList(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    fun attachRegistrationFragment(number:String = "") {
        val registration = Registration()

        val bundle = Bundle()
        bundle.putString("mobile",number)
        registration.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, registration, Registration.TAG)
            .commit()
    }
    private fun setupDrawerContent() {
        nvView.getHeaderView(0).username.text = "Hi, ${UserCache.user!!.contactNumber}"
        nvView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawers()
            when (it.itemId) {
                R.id.newUser -> {

                    attachRegistrationFragment()
                }
                R.id.offer -> {
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container, Offer(), Offer.TAG)
                            .commit()
                }
                R.id.existingUser -> {
                    attachExistingFragment()
                }

                R.id.about -> {
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container, AboutFragment(), AboutFragment.TAG)
                            .commit()
                }
            }
            true
        }
    }
}
