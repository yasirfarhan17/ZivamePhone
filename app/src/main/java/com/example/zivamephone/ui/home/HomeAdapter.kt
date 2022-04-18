package com.example.zivamephone.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.networkmodule.model.ProductsItem
import com.example.zivamephone.R
import com.example.zivamephone.databinding.IndiItemViewBinding
import java.util.*
import kotlin.collections.ArrayList

class HomeAdapter (
    private val callBack: PhoneAdapterCallBack
        ) :RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(),Filterable{
    private val item=ArrayList<ProductsItem>()
    private val itemFilter=ArrayList<ProductsItem>()



    @SuppressLint("NotifyDataSetChange")
    fun submitList(list :ArrayList<ProductsItem>){
        item.clear()
        itemFilter.clear()
        itemFilter.addAll(list)
        item.addAll(list)
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun restoreAllList() {
        itemFilter.clear()
        itemFilter.addAll(item)
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(private val binding: IndiItemViewBinding):
    RecyclerView.ViewHolder(binding.root){
        fun bind(item:ProductsItem){
            with(binding){
                imgPhone.load(item.image_url?: R.drawable.ic_baseline_image_24){
                    crossfade(true)
                    placeholder(R.drawable.ic_baseline_image_24)
                }
                tvProduct.text=item.name
                tvPrice.text="Price:-₹ ${item.price}"
                ratingBar.rating= item.rating?.toFloat()!!

                btAdd.setOnClickListener {
                    callBack.onItemClick(item)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.HomeViewHolder {
        val binding=IndiItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.HomeViewHolder, position: Int) {
        holder.bind(itemFilter[position])
    }

    override fun getItemCount(): Int =itemFilter.size

    val filterName = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val localList = ArrayList<ProductsItem>()
            itemFilter.clear()
            itemFilter.addAll(item)

            if (p0 == null || p0.isEmpty()) {
                localList.addAll(itemFilter)
            } else {
                for (item in itemFilter) {
                    if (item.name != null && item.name?.lowercase(Locale.ENGLISH) != null ) {
                        if (item.name!!.lowercase(Locale.ENGLISH)
                                .contains(p0.toString().lowercase(Locale.ENGLISH))
                        ) {
                            localList.add(item)
                        }
                    }
                }
            }

            val filterResults = FilterResults()
            filterResults.values = localList
            return filterResults
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            itemFilter.clear()
            if (p1 != null) {
                itemFilter.addAll(p1.values as Collection<ProductsItem>)
            }
            notifyDataSetChanged()
        }

    }

    override fun getFilter() = filterName

}

interface PhoneAdapterCallBack{
    fun onItemClick(item: ProductsItem)
}