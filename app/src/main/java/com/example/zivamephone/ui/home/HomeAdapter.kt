package com.example.zivamephone.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.networkmodule.model.ProductsItem
import com.example.zivamephone.R
import com.example.zivamephone.databinding.IndiItemViewBinding

class HomeAdapter (
    private val callBack: PhoneAdapterCallBack
        ) :RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(){
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

    inner class HomeViewHolder(private val binding: IndiItemViewBinding):
    RecyclerView.ViewHolder(binding.root){
        fun bind(item:ProductsItem){
            with(binding){
                imgPhone.load(item.image_url?: R.drawable.ic_launcher_background){
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_background)
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
        holder.bind(item[position])
    }

    override fun getItemCount(): Int =item.size

}

interface PhoneAdapterCallBack{
    fun onItemClick(item: ProductsItem)
}