package com.apboutos.moneytrack.utilities.converter

import org.junit.Assert.*
import org.junit.Test

class CurrencyConverterTest{

    @Test
    fun toPresentableAmountTest(){

        assertEquals("31.00 $",CurrencyConverter.toPresentableAmount(31.0,"$"))
        assertEquals("233,123,331.10 $",CurrencyConverter.toPresentableAmount(233123331.1,"$"))
        assertEquals("1,233,123,331.11 $",CurrencyConverter.toPresentableAmount(1233123331.11,"$"))
        assertEquals("0.00 $",CurrencyConverter.toPresentableAmount(0.0,"$"))
    }

    @Test
    fun substringAfter(){
        var amount = 31.22
        assertEquals("22",amount.toString().substringAfter(".", ""))
        amount = 31.2
        assertEquals("2",amount.toString().substringAfter(".", ""))
        amount.toInt()
        assertEquals("31.2",amount.toString())
        assertEquals("31.22",(31.22).toString())
        assertEquals("31.2",(31.2).toString())

    }

    @Test
    fun normalizeAmount() {
        assertEquals(32.11,CurrencyConverter.normalizeAmount(32.11,"Income"),0.001)
        assertEquals(32.11,CurrencyConverter.normalizeAmount(-32.11,"Income"),0.001)
        assertEquals(-32.11,CurrencyConverter.normalizeAmount(32.11,"Expense"),0.001)
        assertEquals(-32.11,CurrencyConverter.normalizeAmount(-32.11,"Expense"),0.001)
        assertEquals(32.11,CurrencyConverter.normalizeAmount(32.1111,"Income"),0.001)

    }
}