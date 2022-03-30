package com.example.pesaai.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pesaai.databinding.RowBoisBinding
import com.example.pesaai.service.model.Boi
import com.example.pesaai.service.model.relations.PesagemWithBois

class BoiAdapter(bois: List<PesagemWithBois>?, private val clickListener: (Boi) -> Unit) :
    RecyclerView.Adapter<BoiAdapter.BoisViewHolder>() {

    private var mBoiList: List<Boi> = arrayListOf()

    class BoisViewHolder(private val binding: RowBoisBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(boi: Boi, clickListener: (Boi) -> Unit) {
            binding.apply {
                textBrinco.text = boi.brinco
                textPeso.text = boi.peso.toString()
                textArroba.text = boi.arroba.toString()

                root.setOnClickListener { clickListener(boi) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoisViewHolder {
        val rowBoiBinding =
            RowBoisBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BoisViewHolder(rowBoiBinding)
    }

    override fun onBindViewHolder(holder: BoisViewHolder, position: Int) {
        holder.bind(mBoiList[position], clickListener)
    }

    override fun getItemCount(): Int = mBoiList.count()

    fun updateBois(list: ArrayList<Boi>){
        mBoiList = list
        notifyDataSetChanged()
    }
}