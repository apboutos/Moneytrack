package com.apboutos.moneytrack.view

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import com.apboutos.moneytrack.R
import com.apboutos.moneytrack.model.database.converter.Date
import com.apboutos.moneytrack.model.database.entity.Summary
import com.apboutos.moneytrack.utilities.converter.DateFormatConverter

class SearchDialog(private val parentActivity: LedgerActivity) : Dialog(parentActivity){

    private val tag = "SearchDialog"
    private val typeSpinner by lazy { findViewById<Spinner>(R.id.activity_ledger_search_dialog_typeSpinner) }
    private val categorySpinner by lazy { findViewById<Spinner>(R.id.activity_ledger_search_dialog_categorySpinner) }
    private val fromDaySpinner by lazy { findViewById<Spinner>(R.id.activity_ledger_search_dialog_fromDaySpinner) }
    private val fromMonthSpinner by lazy { findViewById<Spinner>(R.id.activity_ledger_search_dialog_fromMonthSpinner) }
    private val fromYearSpinner by lazy { findViewById<Spinner>(R.id.activity_ledger_search_dialog_fromYearSpinner) }
    private val untilDaySpinner by lazy { findViewById<Spinner>(R.id.activity_ledger_search_dialog_untilDaySpinner) }
    private val untilMonthSpinner by lazy { findViewById<Spinner>(R.id.activity_ledger_search_dialog_untilMonthSpinner) }
    private val untilYearSpinner by lazy { findViewById<Spinner>(R.id.activity_ledger_search_dialog_untilYearSpinner) }
    private val descriptionBox by lazy { findViewById<EditText>(R.id.activity_ledger_search_dialog_descriptionBox) }
    private val searchButton by lazy { findViewById<Button>(R.id.activity_ledger_search_dialog_searchButton) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_ledger_search_dialog)

        setUpTypeSpinner()
        setUpCategorySpinner()
        setUpFromYearSpinner()
        setUpUntilYearSpinner()
        setUpFromMonthSpinner()
        setUpUntilMonthSpinner()
        setUpFromDaySpinner()
        setUpUntilDaySpinner()

        fromYearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                setUpFromDaySpinner()
            }

        }

        untilYearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                setUpUntilDaySpinner()
            }

        }

        fromMonthSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                setUpFromDaySpinner()
            }

        }

        untilMonthSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                setUpUntilDaySpinner()
            }

        }

        searchButton.setOnClickListener {
            val fromDate = fromYearSpinner.selectedItem.toString() +
                           "-" +
                           DateFormatConverter.getMonthNumber(fromMonthSpinner.selectedItem.toString(),parentActivity) +
                           "-" +
                           DateFormatConverter.normalizeDay(fromDaySpinner.selectedItem.toString())
            val untilDate = untilYearSpinner.selectedItem.toString() +
                            "-" +
                            DateFormatConverter.getMonthNumber(untilMonthSpinner.selectedItem.toString(),parentActivity) +
                            "-" +
                            DateFormatConverter.normalizeDay(untilDaySpinner.selectedItem.toString())
            val searchSummary = Summary(parentActivity.viewModel.currentUser,categorySpinner.selectedItem.toString(),typeSpinner.selectedItem.toString(),descriptionBox.text.toString(),Date(fromDate),Date(untilDate))
            Log.d(tag,"fromDate $fromDate")
            Log.d(tag, "untilDate $untilDate")
            parentActivity.loadSearchResults(searchSummary)
            dismiss()
        }
    }


    private fun setUpTypeSpinner(){
        val typeAdapter = ArrayAdapter(context, R.layout.activity_ledger_report_spinner, arrayOf("Any","Income","Expense"))
        typeSpinner.adapter = typeAdapter
        typeSpinner.setSelection(0)

    }

    private fun setUpCategorySpinner(){
        val typeAdapter = ArrayAdapter(context,R.layout.activity_ledger_report_spinner, parentActivity.viewModel.getCategories())
        categorySpinner.adapter = typeAdapter
        categorySpinner.setSelection(0)
    }

    private fun setUpFromDaySpinner(){
        val month = DateFormatConverter.getMonthNumber(fromMonthSpinner.selectedItem.toString(),context)
        val year = fromYearSpinner.selectedItem.toString()
        val typeAdapter = ArrayAdapter(context,R.layout.activity_ledger_report_spinner, DateFormatConverter.getDaysOfMonth(month,year))
        fromDaySpinner.adapter = typeAdapter
        fromDaySpinner.setSelection(0)
    }

    private fun setUpUntilDaySpinner(){
        val month = DateFormatConverter.getMonthNumber(untilMonthSpinner.selectedItem.toString(),context)
        val year = untilYearSpinner.selectedItem.toString()
        val typeAdapter = ArrayAdapter(context,R.layout.activity_ledger_report_spinner, DateFormatConverter.getDaysOfMonth(month,year))
        untilDaySpinner.adapter = typeAdapter
        untilDaySpinner.setSelection(0)
    }

    private fun setUpFromMonthSpinner(){
        val typeAdapter = ArrayAdapter(context,R.layout.activity_ledger_report_spinner, DateFormatConverter.getListOfMonths(context))
        fromMonthSpinner.adapter = typeAdapter
        fromMonthSpinner.setSelection(0)
    }

    private fun setUpUntilMonthSpinner(){
        val typeAdapter = ArrayAdapter(context,R.layout.activity_ledger_report_spinner, DateFormatConverter.getListOfMonths(context))
        untilMonthSpinner.adapter = typeAdapter
        untilMonthSpinner.setSelection(0)
    }

    private fun setUpFromYearSpinner(){
        val typeAdapter = ArrayAdapter(context,R.layout.activity_ledger_report_spinner, getYearsThatContainEntries())
        fromYearSpinner.adapter = typeAdapter
        fromYearSpinner.setSelection(0)
    }

    private fun setUpUntilYearSpinner(){
        val typeAdapter = ArrayAdapter(context,R.layout.activity_ledger_report_spinner, getYearsThatContainEntries())
        untilYearSpinner.adapter = typeAdapter
        untilYearSpinner.setSelection(0)
    }

    private fun getYearsThatContainEntries() : Array<String>{
        return parentActivity.viewModel.getYearsThatContainEntries()
    }
}