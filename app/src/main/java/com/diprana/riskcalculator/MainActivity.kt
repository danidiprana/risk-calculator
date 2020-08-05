package com.diprana.riskcalculator

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.diprana.riskcalculator.R.color
import kotlinx.android.synthetic.main.activity_main.container
import kotlinx.android.synthetic.main.activity_main.entry_point
import kotlinx.android.synthetic.main.activity_main.loss_percentage
import kotlinx.android.synthetic.main.activity_main.porto_recycler
import kotlinx.android.synthetic.main.activity_main.risk_ratio
import kotlinx.android.synthetic.main.activity_main.risk_reward
import kotlinx.android.synthetic.main.activity_main.stop_loss
import kotlinx.android.synthetic.main.activity_main.take_profit

class MainActivity : AppCompatActivity() {

  private val adapter = PortoAdapter()
  private val riskList = mutableListOf<RiskPorto>()
  private val riskToPortoList = mutableListOf<RiskPorto>()


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    porto_recycler.layoutManager = GridLayoutManager(this, 4)
    porto_recycler.adapter = adapter

    generateDefaultData()

    handleLossPercentage()

    handleTakeProfit()

    container.setOnClickListener {
      val inputMethodManager = this.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? InputMethodManager
      inputMethodManager?.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
    }
  }

  private fun generateDefaultData() {
    for (x in 10..100 step 5) {
      val porto = RiskPorto(x, 0.0)
      riskList.add(porto)
    }
  }

  private fun handleTakeProfit() {
    take_profit.addTextChangedListener {
      if (it.toString().isNotEmpty()) {

        calculateRiskReward(it.toString().toDouble())
      }
    }
  }

  private fun calculateRiskReward(takeProfit: Double) {

    if (entry_point.text.toString().isNotEmpty() && stop_loss.text.toString().isNotEmpty() && take_profit.text.toString().isNotEmpty()) {
      val entryPoint = entry_point.text.toString()
          .toDouble()
      val stopLoss = stop_loss.text.toString()
          .toDouble()

      val risk = entryPoint - stopLoss
      val reward = takeProfit - entryPoint

      val potentialRisk = risk / risk
      val potentialReward = reward / risk

      risk_reward.text = "$potentialRisk : $potentialReward"

      if (potentialReward >= 2) {
        risk_ratio.text = "GOOD RATIO"
        risk_ratio.setTextColor(ContextCompat.getColor(this, color.goodRatio))
      } else {
        risk_ratio.text = "BAD RATIO"
        risk_ratio.setTextColor(ContextCompat.getColor(this, color.badRatio))
      }
    }
  }

  private fun handleLossPercentage() {
    stop_loss.addTextChangedListener {

      if (take_profit.text.toString().isNotEmpty()) {
        calculateRiskReward(take_profit.text.toString().toDouble())
      }

      if (it.toString().isNotEmpty() && entry_point.text.toString().isNotEmpty()) {

        val entryPoint = entry_point.text.toString().toDouble()
        val stopLoss = it.toString().toDouble()
        val percentage = ((entryPoint - stopLoss) / entryPoint) * 100
        val value = String.format("%.2f", percentage)
        loss_percentage.text = "$value%"

        riskList.map {
          val percentagePorto = it.percentage
          val riskToPorto = (percentage / 100) * percentagePorto

          if (riskToPorto <= 2.0) {
            val newRiskPorto = it.copy(risk = riskToPorto)
            riskToPortoList.add(newRiskPorto)
          }
        }
        val newList = riskToPortoList.toList().sortedByDescending { it.percentage }

        adapter.submitList(newList)
        riskToPortoList.clear()
      }
    }
  }
}