package com.example.pesaai.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pesaai.databinding.RowFarmBinding
import com.example.pesaai.service.model.Farm

class FarmAdapter(farms: List<Farm>?, private val clickListener: (Farm) -> Unit) :
    RecyclerView.Adapter<FarmAdapter.FazendaViewHolder>() {

    private var farmList: List<Farm> = arrayListOf()

    class FazendaViewHolder(private val binding: RowFarmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(farm: Farm, clickListener: (Farm) -> Unit) {
            binding.apply {
                textFazenda.text = farm.name
                textDono.text = farm.proprietor
                textLocal.text = farm.local

                root.setOnClickListener { clickListener(farm) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FazendaViewHolder {
        val rowFarmBinding =
            RowFarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FazendaViewHolder(rowFarmBinding)
    }

    override fun onBindViewHolder(holder: FazendaViewHolder, position: Int) {
        holder.bind(farmList[position], clickListener)
    }

    override fun getItemCount(): Int = farmList.count()

    fun updateFarms(list: List<Farm>){
        farmList = list
        notifyDataSetChanged()
    }
}