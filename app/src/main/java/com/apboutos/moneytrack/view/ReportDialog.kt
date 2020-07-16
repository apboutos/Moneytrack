@file:Suppress("unused")

package com.apboutos.moneytrack.view

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.apboutos.moneytrack.R
import com.apboutos.moneytrack.model.database.converter.Date
import com.apboutos.moneytrack.utilities.converter.CurrencyConverter
import com.apboutos.moneytrack.utilities.converter.DateFormatConverter

class ReportDialog(private val parentActivity: LedgerActivity) : Dialog(parentActivity) {

    private val tag = "ReportDialog"

    private val daySpinner   by lazy { findViewById<Spinner>(R.id.activity_ledger_report_dialog_todayLabel)  }
    private val monthSpinner by lazy { findViewById<Spinner>(R.id.activity_ledger_report_dialog_monthLabel)  }
    private val yearSpinner  by lazy { findViewById<Spinner>(R.id.activity_ledger_report_dialog_yearLabel)   }
    private val todaySum     by lazy { findViewById<TextView>(R.id.activity_ledger_report_dialog_todaySum)   }
    private val monthSum     by lazy { findViewById<TextView>(R.id.activity_ledger_report_dialog_monthSum)   }
    private val yearSum      by lazy { findViewById<TextView>(R.id.activity_ledger_report_dialog_yearSum)    }
    private val lifetimeSum  by lazy { findViewById<TextView>(R.id.activity_ledger_report_dialog_lifeTimeSum)}

    private var currentDay = "01"
    private var currentMonth = "01"
    private var currentYear = "2020"
    private var currency = "$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_ledger_report_dialog)

        setUpStartingDate()
        setUpDaySpinner()
        setUpMonthSpinner()
        setUpYearSpinner()
        getLifetimeSum()
        calculateYearlySum()
        calculateMonthlySum()
        calculateDailySum()

        daySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                calculateDailySum()
            }

        }

        monthSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                calculateMonthlySum()
                calculateDailySum()
            }

        }

        yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                calculateYearlySum()
                calculateMonthlySum()
                calculateDailySum()
            }
        }
    }

    private fun calculateDailySum(){
        currentDay = DateFormatConverter.normalizeDay(daySpinner.selectedItem.toString())
        val sum = parentActivity.viewModel.getSumOfDateRange(Date("$currentYear-$currentMonth-$currentDay"),Date("$currentYear-$currentMonth-$currentDay"))
        todaySum.text = CurrencyConverter.toPresentableAmount(sum,currency)
        if(sum < 0){
            todaySum.setTextColor(parentActivity.resources.getColor(R.color.red))
        }else{
            todaySum.setTextColor(parentActivity.resources.getColor(R.color.light_oily_green))
        }
        Log.d(tag,"TodaySum= $sum")
    }

    private fun calculateMonthlySum(){
        currentMonth = DateFormatConverter.getMonthNumber(monthSpinner.selectedItem.toString(),parentActivity)
        val sum = parentActivity.viewModel.getSumOfDateRange(Date("$currentYear-$currentMonth-01"),Date("$currentYear-$currentMonth-${DateFormatConverter.getLastDayOfMonth(currentMonth)}"))
        monthSum.text = CurrencyConverter.toPresentableAmount(sum,currency)
        if(sum < 0){
            monthSum.setTextColor(parentActivity.resources.getColor(R.color.red))
        }else{
            monthSum.setTextColor(parentActivity.resources.getColor(R.color.light_oily_green))
        }
        Log.d(tag,"MonthlySum= $sum")
    }

    private fun calculateYearlySum(){
        currentYear = yearSpinner.selectedItem.toString()
        val sum = parentActivity.viewModel.getSumOfDateRange(Date("$currentYear-01-01"),Date("$currentYear-12-31"))
        yearSum.text = CurrencyConverter.toPresentableAmount(sum,currency)
        if(sum < 0){
            yearSum.setTextColor(parentActivity.resources.getColor(R.color.red))
        }else{
            yearSum.setTextColor(parentActivity.resources.getColor(R.color.light_oily_green))
        }
        Log.d(tag,"YearlySum= $sum")
    }

    private fun getLifetimeSum(){
        val sum = parentActivity.viewModel.getSumOfLifetime()
        lifetimeSum.text = CurrencyConverter.toPresentableAmount(sum,currency)
        if(sum < 0){
            lifetimeSum.setTextColor(parentActivity.resources.getColor(R.color.red))
        }else{
            lifetimeSum.setTextColor(parentActivity.resources.getColor(R.color.light_oily_green))
        }
        Log.d(tag,"LifetimeSum= $sum")
    }

    private fun setUpStartingDate(){
        val parts = parentActivity.viewModel.currentDate.split("-".toRegex()).toTypedArray()
        currentYear = parts[0]
        currentMonth = parts[1]
        currentDay = parts[2]
    }

    private fun setUpDaySpinner(){
        val typeAdapter = ArrayAdapter(context, R.layout.activity_ledger_report_spinner, DateFormatConverter.getDaysOfMonth(currentMonth))
        daySpinner.adapter = typeAdapter
        daySpinner.setSelection(typeAdapter.getPosition(DateFormatConverter.cropStartingZeroFrom(currentDay)))

    }

    private fun setUpMonthSpinner(){
        val typeAdapter = ArrayAdapter(context,R.layout.activity_ledger_report_spinner, DateFormatConverter.getListOfMonths(parentActivity))
        monthSpinner.adapter = typeAdapter
        monthSpinner.setSelection(typeAdapter.getPosition(DateFormatConverter.getMonthName(currentMonth,parentActivity)))
    }

    private fun setUpYearSpinner(){
        val typeAdapter = ArrayAdapter(context,R.layout.activity_ledger_report_spinner, getYearsThatContainEntries())
        yearSpinner.adapter = typeAdapter
        yearSpinner.setSelection(typeAdapter.getPosition(currentYear))
    }



    private fun getYearsThatContainEntries() : Array<String>{

        return arrayOf("2020","2021")
    }





}