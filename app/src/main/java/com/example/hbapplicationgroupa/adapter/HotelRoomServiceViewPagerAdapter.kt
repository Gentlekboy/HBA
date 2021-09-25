package com.example.hbapplicationgroupa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.hbapplicationgroupa.R
import com.example.hbapplicationgroupa.model.HotelRoomServiceModel

/**
 * This is the ViewPager Adapter for the id:hotel_desc_services_viewPager
 */
class HotelRoomServiceViewPagerAdapter(private val roomDataList: ArrayList<HotelRoomServiceModel>) : RecyclerView.Adapter<HotelRoomServiceViewPagerAdapter.HotelViewHolder>() {
    class HotelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val roomImage: ImageView = view.findViewById(R.id.hotel_desc_room_iv)
        private val roomTitle: TextView = view.findViewById(R.id.hotel_desc_roomTitle_tv)

        //This method binds HotelViewHolder properties with HotelRoomServiceModel data
        fun bindHotelRoomData(roomData: HotelRoomServiceModel){
            roomImage.setImageResource(roomData.roomImage)
            roomTitle.text = roomData.roomTitle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.hotel_services_viewpager_layout, parent, false)
        return HotelViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        holder.bindHotelRoomData(roomDataList[position])
    }

    override fun getItemCount(): Int {
        return roomDataList.size
    }

}