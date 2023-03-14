package com.example.pesaai.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pesaai.databinding.RowWeightBinding
import com.example.pesaai.service.model.Farm
import com.example.pesaai.service.model.Weight

class WeighingAdapter(weights: List<Weight>?, private val clickListener: (Weight) -> Unit) :
    RecyclerView.Adapter<WeighingAdapter.WeightViewHolder>() {

    private var mWeightList: List<Weight> = arrayListOf()
    private var mFarmList: List<Farm> = arrayListOf()

    class WeightViewHolder(private val binding: RowWeightBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weight: Weight, farm: Farm, clickListener: (Weight) -> Unit) = with(binding) {
//            .text = farm.name
//            textLocal.text = farm.local
//            textDono.text = farm.proprietor
            textDataPesagem.text = weight.date

            root.setOnClickListener { clickListener(weight) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeightViewHolder {
        val rowWeightsBinding =
            RowWeightBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return WeightViewHolder(rowWeightsBinding)
    }

    override fun onBindViewHolder(holder: WeightViewHolder, position: Int) {
        holder.bind(mWeightList[position], mFarmList[position], clickListener)
    }

    override fun getItemCount(): Int = mWeightList.count()

    fun updateWeights(list: List<Weight>) {
        mWeightList = list
        notifyDataSetChanged()
    }
}