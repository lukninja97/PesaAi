package com.example.pesaai.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pesaai.databinding.RowFazendaBinding
import com.example.pesaai.service.model.Fazenda

class FazendaAdapter(fazendas: List<Fazenda>?, private val clickListener: (Fazenda) -> Unit) :
    RecyclerView.Adapter<FazendaAdapter.FazendaViewHolder>() {

    private var mFazendaList: List<Fazenda> = arrayListOf()

    class FazendaViewHolder(private val binding: RowFazendaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fazenda: Fazenda, clickListener: (Fazenda) -> Unit) {
            binding.apply {
                textFazenda.text = fazenda.nome
                textDono.text = fazenda.dono
                textLocal.text = fazenda.local

                root.setOnClickListener { clickListener(fazenda) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FazendaViewHolder {
        val rowFazendaBinding =
            RowFazendaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FazendaViewHolder(rowFazendaBinding)
    }

    override fun onBindViewHolder(holder: FazendaViewHolder, position: Int) {
        holder.bind(mFazendaList[position], clickListener)
    }

    override fun getItemCount(): Int = mFazendaList.count()

    fun updateFazendas(list: List<Fazenda>){
        mFazendaList = list
        notifyDataSetChanged()
    }
}