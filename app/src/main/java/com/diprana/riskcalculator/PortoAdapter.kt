package com.diprana.riskcalculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class PortoAdapter : ListAdapter<RiskPorto, PortoViewHolder>(PortoDiffUtil()) {
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): PortoViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_risk_porto, parent, false)

    return PortoViewHolder(view)
  }

  override fun onBindViewHolder(
    holder: PortoViewHolder,
    position: Int
  ) {
    holder.bind(getItem(position))
  }
}
