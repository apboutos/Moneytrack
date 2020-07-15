package com.apboutos.moneytrack.utilities.converter

import java.text.DecimalFormat


object CurrencyConverter {

    const val tag = "CurrencyConverter"

    fun toPresentableAmount(amount: Double, currency: String) : String{
        if(amount == 0.0) return "0.00 $"
        val formatter = DecimalFormat("#,###.00");

        return "${formatter.format(amount)} $currency"
    }
}