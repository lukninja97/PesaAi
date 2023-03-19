package com.example.pesaai.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pesaai.databinding.RowPicketBinding
import com.example.pesaai.service.model.Farm
import com.example.pesaai.service.model.Picket

class PicketAdapter(pickets: List<Picket>?, private val clickListener: (Picket) -> Unit) :
    RecyclerView.Adapter<PicketAdapter.PicketViewHolder>() {

    private var mPicketList: List<Picket> = arrayListOf()
    private var mFarmList: List<Farm> = arrayListOf()

    class PicketViewHolder(private val binding: RowPicketBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(picket: Picket, farm: Farm, clickListener: (Picket) -> Unit) {
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

    fun updatePickets(list: List<Picket>) {
        mPicketList = list
        notifyDataSetChanged()
    }
}