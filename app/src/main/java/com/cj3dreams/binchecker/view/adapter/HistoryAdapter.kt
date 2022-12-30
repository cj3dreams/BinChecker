package com.cj3dreams.binchecker.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.cj3dreams.binchecker.R
import com.cj3dreams.binchecker.model.entity.BinHistoryEntity
import com.cj3dreams.binchecker.utils.AppConstants.u

class HistoryAdapter(private val list: List<BinHistoryEntity>,
                     private val onClickListener: View.OnClickListener)
    : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val itemBinHistoryCardView = view.findViewById(R.id.itemBinHistoryCardView) as CardView
        val itemBinHistoryCardNumberTx = view.findViewById(R.id.itemBinHistoryCardNumberTx) as TextView
        val itemBinHistoryCountryTx = view.findViewById(R.id.itemBinHistoryCountryTx) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bin_history, parent, false)

        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val itemData = list[position]
        val cardNumb = itemData.cardNumb.toString()
        with(holder){
            for (i in cardNumb.indices)
                itemBinHistoryCardNumberTx.append(if(i%4 == 0) " ${cardNumb[i]}" else "${cardNumb[i]}")
            itemBinHistoryCountryTx.text =
                if (itemData.countryName != null) itemData.emoji + " " + itemData.countryName else u
            itemBinHistoryCardView.setOnClickListener(onClickListener)
            itemBinHistoryCardView.tag = itemData
        }
    }


    override fun getItemCount() = list.size

}