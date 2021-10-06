package com.example.hbapplicationgroupa.adapter.exploreHomeAfterSearchAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hbapplicationgroupa.R
import com.example.hbapplicationgroupa.databinding.ExploreHomeAfterSearchRecyclerview1ItemBinding
import com.example.hbapplicationgroupa.models.model.Hotel

class ExploreHomeAfterSearchRecyclerViewAdapter1(
    private var listOfTopHotels : List<Hotel>
): RecyclerView.Adapter<ExploreHomeAfterSearchRecyclerViewAdapter1.MyViewHolder>() {
    val binding: ExploreHomeAfterSearchRecyclerview1ItemBinding? = null

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hotelName1 = binding?.exploreHomeAfterSearchFragmentRecyclerViewTextviewName1
        val hotelPrice1 = binding?.exploreHomeAfterSearchFragmentRecyclerViewTextviewPrice1
        val hotelImage1 = binding?.exploreHomeAfterSearchFragmentRecyclerViewImageview1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.explore_home_after_search_recyclerview1_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.hotelImage1?.setImageResource(listOfTopHotels[position].image)
        holder.hotelName1?.text = listOfTopHotels[position].name
        holder.hotelPrice1?.text = listOfTopHotels[position].price.toString()
    }

    override fun getItemCount(): Int {
        return listOfTopHotels.size
    }


}