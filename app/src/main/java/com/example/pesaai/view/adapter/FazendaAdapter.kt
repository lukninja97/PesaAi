package com.example.pesaai.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pesaai.databinding.RowFazendaBinding
import com.example.pesaai.service.model.Farm

class FazendaAdapter(farms: List<Farm>?, private val clickListener: (Farm) -> Unit) :
    RecyclerView.Adapter<FazendaAdapter.FazendaViewHolder>() {

    private var mFarmList: List<Farm> = arrayListOf()

    class FazendaViewHolder(private val binding: RowFazendaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(farm: Farm, clickListener: (Farm) -> Unit) {
            binding.apply {
                textFazenda.text = farm.nome
                textDono.text = farm.dono
                textLocal.text = farm.local

                root.setOnClickListener { clickListener(farm) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FazendaViewHolder {
        val rowFazendaBinding =
            RowFazendaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FazendaViewHolder(rowFazendaBinding)
    }

    override fun onBindViewHolder(holder: FazendaViewHolder, position: Int) {
        holder.bind(mFarmList[position], clickListener)
    }

    override fun getItemCount(): Int = mFarmList.count()

    fun updateFazendas(list: List<Farm>){
        mFarmList = list
        notifyDataSetChanged()
    }
}