package com.diprana.riskcalculator

import androidx.recyclerview.widget.DiffUtil.ItemCallback

class PortoDiffUtil : ItemCallback<RiskPorto>() {

  override fun areItemsTheSame(
    oldItem: RiskPorto,
    newItem: RiskPorto
  ): Boolean {
    return oldItem.hashCode() == newItem.hashCode()
  }

  override fun areContentsTheSame(
    oldItem: RiskPorto,
    newItem: RiskPorto
  ): Boolean {
    return oldItem.percentage == newItem.percentage
  }

}