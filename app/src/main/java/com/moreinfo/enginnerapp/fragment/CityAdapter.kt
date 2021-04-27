package com.moreinfo.enginnerapp.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moreinfo.enginnerapp.R
import com.moreinfo.enginnerapp.cache.DataCache
import com.moreinfo.enginnerapp.model.City
import com.moreinfo.enginnerapp.model.StateModel
import kotlinx.android.synthetic.main.state_item.view.*

class CityAdapter(val context: Context, val function: (it:City) -> Unit) : RecyclerView.Adapter<CityAdapter.StateHolder>() {

    inner class StateHolder(it: View):RecyclerView.ViewHolder(it)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateHolder {
        return StateHolder(LayoutInflater.from(context).inflate(R.layout.state_item,parent,false))
    }

    override fun onBindViewHolder(holder: StateHolder, position: Int) {
        holder.itemView.name.text = DataCache.cityList[position].name
        holder.itemView.setOnClickListener {
            function(DataCache.cityList[position])
        }
    }


    override fun getItemCount(): Int {
       return DataCache.cityList.size
    }
}
