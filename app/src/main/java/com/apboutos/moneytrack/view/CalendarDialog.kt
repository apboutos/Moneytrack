package com.apboutos.moneytrack.view


import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import com.apboutos.moneytrack.R
import com.apboutos.moneytrack.utilities.converter.DateFormatConverter
import java.util.*

class CalendarDialog(private val parentActivity: LedgerActivity, private val startingCurrentDate: String) : Dialog(parentActivity) {

    private val calendarView: CalendarView by lazy { findViewById<CalendarView>(R.id.calendar_dialog_calendarView) }
    private val pickDayButton: Button by lazy { findViewById<Button>(R.id.calendar_dialog_pickDayButton)}
    private var selectedDate: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.calendar_dialog_layout)
        setInitialDisplayDate()
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth -> //For some reason, months start from 0 instead of 1
            selectedDate = DateFormatConverter.parseToDatabaseDateFromUserDate(year, month + 1, dayOfMonth)
        }

        pickDayButton.setOnClickListener {

            parentActivity.viewModel.currentDate = selectedDate
            parentActivity.viewModel.loadEntries()
            closeDialog()
        }
    }

    private fun setInitialDisplayDate() {
        val date = startingCurrentDate
        val parts = date.split("-".toRegex()).toTypedArray()
        val day = parts[2].toInt()
        var month = parts[1].toInt()
        month--
        val year = parts[0].toInt()
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = day
        val milliTime = calendar.timeInMillis
        calendarView.setDate(milliTime, false, true)
    }

    private fun closeDialog() {
        dismiss()
    }

    init {
        selectedDate = startingCurrentDate
        //TODO set the calendar to start from current selected date. This is important.
    }
}