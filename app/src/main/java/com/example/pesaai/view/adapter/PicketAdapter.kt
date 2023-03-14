package com.example.pesaai.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pesaai.databinding.RowPicketBinding
import com.example.pesaai.service.model.Farm
import com.example.pesaai.service.model.Weight

class PicketAdapter(pickets: List<Weight>?, private val clickListener: (Weight) -> Unit) :
    RecyclerView.Adapter<PicketAdapter.PicketViewHolder>() {

    private var mPicketList: List<Weight> = arrayListOf()
    private var mFarmList: List<Farm> = arrayListOf()

    class PicketViewHolder(private val binding: RowPicketBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(picket: Weight, farm: Farm, clickListener: (Weight) -> Unit) {
            binding.apply {
                root.setOnClickListener { clickListener(picket) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicketViewHolder {
        val rowPicketBinding =
            RowPicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PicketViewHolder(rowPicketBinding)
    }

    override fun onBindViewHolder(holder: PicketViewHolder, position: Int) {
        holder.bind(mPicketList[position], mFarmList[position], clickListener)
    }

    override fun getItemCount(): Int = mPicketList.count()

    fun updatePesagens(list: List<Weight>) {
        mPicketList = list
        notifyDataSetChanged()
    }
}