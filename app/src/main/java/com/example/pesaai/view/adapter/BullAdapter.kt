package com.example.pesaai.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pesaai.databinding.RowBullsBinding
import com.example.pesaai.service.model.Bull
import com.example.pesaai.service.model.relations.WeightWithBulls

class BullAdapter(bulls: List<WeightWithBulls>?, private val clickListener: (Bull) -> Unit) :
    RecyclerView.Adapter<BullAdapter.BullsViewHolder>() {

    private var mBullList: List<Bull> = arrayListOf()

    class BullsViewHolder(private val binding: RowBullsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bull: Bull, clickListener: (Bull) -> Unit) = with(binding) {
            tvTag.text = bull.brinco
            tvWeight.text = bull.weight.toString()
            tvArroba.text = bull.arroba.toString()

            root.setOnClickListener { clickListener(bull) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BullsViewHolder {
        val rowBoiBinding =
            RowBullsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BullsViewHolder(rowBoiBinding)
    }

    override fun onBindViewHolder(holder: BullsViewHolder, position: Int) {
        holder.bind(mBullList[position], clickListener)
    }

    override fun getItemCount(): Int = mBullList.count()

    fun updateBulls(list: ArrayList<Bull>) {
        mBullList = list
        notifyDataSetChanged()
    }
}