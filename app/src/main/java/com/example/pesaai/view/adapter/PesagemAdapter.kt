package com.example.pesaai.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pesaai.databinding.RowPesagemBinding
import com.example.pesaai.service.model.Farm
import com.example.pesaai.service.model.Pesagem

class PesagemAdapter(pesagens: List<Pesagem>?, private val clickListener: (Pesagem) -> Unit) :
    RecyclerView.Adapter<PesagemAdapter.PesagemViewHolder>() {

    private var mPesagemList: List<Pesagem> = arrayListOf()
    private var mFarmList: List<Farm> = arrayListOf()

    class PesagemViewHolder(private val binding: RowPesagemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pesagem: Pesagem, farm: Farm, clickListener: (Pesagem) -> Unit) {
            binding.apply {
//                textFazenda.setText(fazenda.nome)
//                textLocal.setText(fazenda.local)
//                textDono.setText(fazenda.dono)
                textDataPesagem.setText(pesagem.data)

                root.setOnClickListener { clickListener(pesagem) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesagemViewHolder {
        val rowPesagensBinding =
            RowPesagemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PesagemViewHolder(rowPesagensBinding)
    }

    override fun onBindViewHolder(holder: PesagemViewHolder, position: Int) {
        holder.bind(mPesagemList[position], mFarmList[position], clickListener)
    }

    override fun getItemCount(): Int = mPesagemList.count()

    fun updatePesagens(list: List<Pesagem>) {
        mPesagemList = list
        notifyDataSetChanged()
    }
}