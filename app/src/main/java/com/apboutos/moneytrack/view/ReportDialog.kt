package com.apboutos.moneytrack.view

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.apboutos.moneytrack.R

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
    private var currentYear = "1324"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_ledger_report_dialog)

        setUpStartingDate()
        setUpDaySpinner()
        setUpMonthSpinner()
        setUpYearSpinner()
    }

    private fun setUpStartingDate(){
        val parts = parentActivity.viewModel.currentDate.split("-".toRegex()).toTypedArray()
        currentYear = parts[0]
        currentMonth = parts[1]
        currentDay = parts[2]
    }

    private fun setUpDaySpinner(){
        val typeAdapter = ArrayAdapter(context, R.layout.activity_ledger_report_spinner, getDaysOfMonth(currentMonth))
        daySpinner.adapter = typeAdapter
        daySpinner.setSelection(typeAdapter.getPosition(cropStartingZeroFrom(currentDay)))
        Log.d(tag,"Current day: $currentDay getDay: ${getMonth(currentMonth)}")
    }

    private fun setUpMonthSpinner(){
        val typeAdapter = ArrayAdapter(context,R.layout.activity_ledger_report_spinner, getListOfMonths())
        monthSpinner.adapter = typeAdapter
        monthSpinner.setSelection(typeAdapter.getPosition(getMonth(currentMonth)))
        Log.d(tag,"Current Month: $currentMonth getMonth: ${getMonth(currentMonth)}")
    }

    private fun setUpYearSpinner(){
        val typeAdapter = ArrayAdapter(context,R.layout.activity_ledger_report_spinner, getYearsThatContainEntries())
        yearSpinner.adapter = typeAdapter
        yearSpinner.setSelection(typeAdapter.getPosition(currentYear))
    }

    private fun cropStartingZeroFrom(day : String) : String{
        if(day.startsWith("0")) return day.drop(1)
        return day
    }

    private fun getYearsThatContainEntries() : Array<String>{
        //TODO Data are mocked must be returned by database query.
        return arrayOf("2020","2021")
    }

    private fun getDaysOfMonth(month : String) : Array<String>{
        return when(month){
            "1","3","4","5","6","8","10","12" -> arrayOf("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31")
            "2"                               -> arrayOf("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28")
            else                              -> arrayOf("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30")
        }
    }

    private fun getMonth(month : String) : String{
        return when (month){
            "01" -> parentActivity.resources.getString(R.string.month1)
            "02" -> parentActivity.resources.getString(R.string.month2)
            "03" -> parentActivity.resources.getString(R.string.month3)
            "04" -> parentActivity.resources.getString(R.string.month4)
            "05" -> parentActivity.resources.getString(R.string.month5)
            "06" -> parentActivity.resources.getString(R.string.month6)
            "07" -> parentActivity.resources.getString(R.string.month7)
            "08" -> parentActivity.resources.getString(R.string.month8)
            "09" -> parentActivity.resources.getString(R.string.month9)
            "10" -> parentActivity.resources.getString(R.string.month10)
            "11" -> parentActivity.resources.getString(R.string.month11)
            else -> parentActivity.resources.getString(R.string.month12)
        }
    }

    private fun getListOfMonths() : Array<String>{
        return arrayOf(parentActivity.resources.getString(R.string.month1),
            parentActivity.resources.getString(R.string.month2),
            parentActivity.resources.getString(R.string.month3),
            parentActivity.resources.getString(R.string.month4),
            parentActivity.resources.getString(R.string.month5),
            parentActivity.resources.getString(R.string.month6),
            parentActivity.resources.getString(R.string.month7),
            parentActivity.resources.getString(R.string.month8),
            parentActivity.resources.getString(R.string.month9),
            parentActivity.resources.getString(R.string.month10),
            parentActivity.resources.getString(R.string.month11),
            parentActivity.resources.getString(R.string.month12))
    }
}