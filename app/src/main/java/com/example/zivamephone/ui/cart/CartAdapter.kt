package com.example.zivamephone.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.networkmodule.database.CartTable
import com.example.zivamephone.R
import com.example.zivamephone.databinding.CartViewBinding
import com.example.zivamephone.ui.home.PhoneAdapterCallBack

class CartAdapter(
    private val callBack: CartActivity

) :RecyclerView.Adapter<CartAdapter.CartViewHolder>(){


    private val item=ArrayList<CartTable>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list:ArrayList<CartTable>){
        item.clear()
        item.addAll(list)
        notifyDataSetChanged()
    }

    inner class CartViewHolder(private val binding :CartViewBinding):
    RecyclerView.ViewHolder(binding.root){
        fun bind(item:CartTable){
            with(binding){
                imgPhone.load(item.image_url?: R.drawable.ic_baseline_image_24){
                    crossfade(true)
                    placeholder(R.drawable.ic_baseline_image_24)
                }
                tvProduct.text=item.name
                tvPrice.text="Price:-₹ ${item.price}"
                tvQuant.text= item.quantity.toString()



                var quant: Int? = item.quantity?.toInt()
                var total :Int = (item.price?.toInt()?.let { quant?.times(it) } ?: item.price?.toInt()) as Int
                tvTotal.text="Total:-₹ $total"

                    btAdd.setOnClickListener {
                    quant= quant?.plus(1)
                    callBack.onItemClick(quant.toString(),item.name)
                    tvQuant.text= quant.toString()
                        var total :Int = (item.price?.toInt()?.let { quant?.times(it) } ?: item.price?.toInt()) as Int
                        tvTotal.text="Total:-₹ $total"
                }
                btRemove.setOnClickListener {

                    if(quant!! >1) {
                        quant = quant?.minus(1)
                        callBack.onItemClick(quant.toString(), item.name)
                        tvQuant.text = quant.toString()
                        var total: Int = (item.price?.toInt()?.let { quant?.times(it) }
                            ?: item.price?.toInt()) as Int
                        tvTotal.text = "Total:-₹ $total"
                    }
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.CartViewHolder {
        val binding=CartViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.CartViewHolder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount(): Int =item.size

}
interface CartAdapterCallBack{
    fun onItemClick(quant:String,id:String)
}