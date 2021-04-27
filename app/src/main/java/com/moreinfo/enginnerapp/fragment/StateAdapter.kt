package com.moreinfo.enginnerapp.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moreinfo.enginnerapp.R
import com.moreinfo.enginnerapp.cache.DataCache
import com.moreinfo.enginnerapp.model.StateModel
import kotlinx.android.synthetic.main.state_item.view.*

class StateAdapter(val context: Context, val function: (it:StateModel) -> Unit) : RecyclerView.Adapter<StateAdapter.StateHolder>() {

    inner class StateHolder(it: View):RecyclerView.ViewHolder(it)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateHolder {
        return StateHolder(LayoutInflater.from(context).inflate(R.layout.state_item,parent,false))
    }

    override fun onBindViewHolder(holder: StateHolder, position: Int) {
        holder.itemView.name.text = DataCache.stateList[position].name
        holder.itemView.setOnClickListener {
            function(DataCache.stateList[position])
        }
    }


    override fun getItemCount(): Int {
       return DataCache.stateList.size
    }
}
