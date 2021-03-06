package com.example.covidtracker.ui.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter
import com.example.covidtracker.R
import com.example.covidtracker.database.CountryEntitySubscribed

class SubscripedRecycleViewAdapter(dataSet: MutableList<CountryEntitySubscribed>)
    : DragDropSwipeAdapter<CountryEntitySubscribed, SubscripedRecycleViewAdapter.ViewHolder>((dataSet)) {

    class ViewHolder(itemView: View) : DragDropSwipeAdapter.ViewHolder(itemView),View.OnClickListener {
        val countryText: TextView = itemView.findViewById(R.id.countryNameTextView)
        val totalCasesText: TextView = itemView.findViewById(R.id.countryTotalCasesTextView)
        val countryImage: ImageView = itemView.findViewById(R.id.countryImageView)
 //       val onEntitySelected: OnItemSelected
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }

    override fun getViewHolder(itemLayout: View) = SubscripedRecycleViewAdapter.ViewHolder(itemLayout)

    override fun onBindViewHolder(item: CountryEntitySubscribed, viewHolder: SubscripedRecycleViewAdapter.ViewHolder, position: Int) {
        // Here we update the contents of the view holder's views to reflect the item's data
        viewHolder.countryText.text = item.country
        viewHolder.totalCasesText.text = "Total Cases: ${item.totalCases}"
        Glide.with(viewHolder.itemView)
            .load(item.countryThumb)
            .placeholder(R.drawable.sick_person)
            .fitCenter()
            .into(viewHolder.countryImage)
    }

    override fun getViewToTouchToStartDraggingItem(item: CountryEntitySubscribed, viewHolder: SubscripedRecycleViewAdapter.ViewHolder, position: Int): View? {
        // We return the view holder's view on which the user has to touch to drag the item
        return viewHolder.itemView
    }

}