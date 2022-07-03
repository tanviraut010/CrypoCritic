package com.example.crypocritic.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.crypocritic.R
import com.example.crypocritic.data.CryptoResponseItem
import com.example.crypocritic.ui.interfaces.ItemClickInterface

class MainAdapter(
    private val cryptoResponseItem: List<CryptoResponseItem>,
    private val itemClickInterface: ItemClickInterface
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return ViewHolder(inflater.inflate(R.layout.row_main, viewGroup, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvSymbol?.text = cryptoResponseItem[position].symbol
        holder.tvBaseAsset?.text = "/" + cryptoResponseItem[position].baseAsset
        holder.tvBidPrice?.text = cryptoResponseItem[position].bidPrice

        holder.cslMainCell?.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("symbol", cryptoResponseItem[position].symbol)
            itemClickInterface.onSymbolClick(bundle)
        }
    }

    override fun getItemCount(): Int {
        return cryptoResponseItem.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvSymbol: TextView? = null
        var tvBaseAsset: TextView? = null
        var tvBidPrice: TextView? = null
        var cslMainCell: ConstraintLayout? = null

        init {
            tvSymbol = view.findViewById(R.id.tvSymbol)
            tvBaseAsset = view.findViewById(R.id.tvBaseAsset)
            tvBidPrice = view.findViewById(R.id.tvBidPrice)
            cslMainCell = view.findViewById(R.id.cslMainCell)
        }
    }
}