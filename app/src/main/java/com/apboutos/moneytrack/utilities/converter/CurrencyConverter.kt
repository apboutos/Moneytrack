package com.apboutos.moneytrack.utilities.converter

import java.text.DecimalFormat


object CurrencyConverter {

    const val tag = "CurrencyConverter"

    /**
     * Returns a String in the containing the specified amount formatted
     * in the standard US currency format and applies the specified currency symbol
     * at the end.
     */
    fun toPresentableAmount(amount: Double, currency: String) : String{
        if(amount == 0.0) return "0.00 $"
        val formatter = DecimalFormat("##,###.00")

        return "${formatter.format(amount)} $currency"
    }

    /**
     * Returns a double containing a normalized value of the specified amount.
     * All the decimal points over two decimal digits are dropped and a sign
     * is applied depending on the specified type.
     */
    fun normalizeAmount(amount : Double , type : String) : Double{
        val tmp = if(amount < 0 && type == "Income") amount*(-1)
        else if(amount >= 0 && type == "Income") amount
        else if(amount <  0 && type == "Expense") amount
        else amount*(-1)
        //Drop all decimal digits after the twi first ones
        return (tmp*100).toInt()/100.0
    }
}