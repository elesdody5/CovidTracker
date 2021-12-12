package com.example.covidtracker


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.covidtracker.domain.CountryModel
import com.example.covidtracker.ui.adapters.CountryAdapter

/**
 * When there is no  property data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("list")
fun bindRecycler(recyclerView: RecyclerView, data: List<CountryModel>?) {
    val adapter = recyclerView.adapter as CountryAdapter
    adapter.submitList(data)
}

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("image")
fun bindimage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Glide.with(imgView.context)
            .load(it)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}

@BindingAdapter("drwable_image")
fun bindDrawable(imgView: ImageView, imgUrl: Int?) {
    imgUrl?.let {
        Glide.with(imgView.context).load(imgView.resources.getDrawable(imgUrl, null)).into(imgView)
    }

}
