package com.example.gold

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gold.databinding.RecyclerMainItemBinding
import com.example.gold.remote.model.gold.ContentModel
import java.text.DecimalFormat

class RecyclerMainAdapter(
    private val allData: ArrayList<ContentModel>
) : RecyclerView.Adapter<RecyclerMainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MainViewHolder(
            RecyclerMainItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = allData.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.setData(allData[position])
    }


    inner class MainViewHolder(
        private val view: RecyclerMainItemBinding
    ) : RecyclerView.ViewHolder(view.root) {

        fun setData(data: ContentModel) {

            val decimal = DecimalFormat("0,000")

            val name = when(data.name){
                "bitcoin" -> data.label = "تتر"
                "tether" -> data.label = "بیتکوین"
                else -> {}
            }

            val displayName = when(data.name){
                "bitcoin" -> "تتر"
                "tether" -> "بیتکوین"
                else -> data.label
            }
            view.txtTitleRecycler.text = displayName
            view.txtPriceRecycler.text = decimal.format((data.price / 10)).toString()

            val images = when(data.name){
                "gold_24" -> R.drawable.ic_24
                "gold_18" -> R.drawable.ic_18
                "coin", "half_coin", "quarter_coin" -> R.drawable.ic_coin
                "dollar" -> R.drawable.ic_dolar
                "euro" -> R.drawable.ic_uro
                "derham" -> R.drawable.ic_derham
                "pound" -> R.drawable.ic_pond
                "bitcoin" -> R.drawable.ic_tether
                "tether" -> R.drawable.ic_bitcoin
                else -> R.drawable.ic_coin
            }
            view.imgRecyclerTitle.setImageResource(images)
        }
    }
}