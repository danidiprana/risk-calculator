package com.diprana.riskcalculator

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.item_risk_porto.view.risk_porto_percentage
import kotlinx.android.synthetic.main.item_risk_porto.view.risk_to_porto

class PortoViewHolder(itemView: View) : ViewHolder(itemView) {
  fun bind(item: RiskPorto) {
    val riskPercentage = String.format("%.2f", item.risk)

    itemView.risk_to_porto.text = "$riskPercentage%"


    itemView.risk_porto_percentage.text = "${item.percentage}% Porto"
  }
}