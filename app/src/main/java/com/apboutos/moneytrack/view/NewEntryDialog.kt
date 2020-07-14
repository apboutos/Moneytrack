@file:Suppress("unused")

package com.apboutos.moneytrack.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.apboutos.moneytrack.R
import com.apboutos.moneytrack.model.database.entity.Entry
import com.apboutos.moneytrack.utilities.Time

class NewEntryDialog(private val parentActivity : LedgerActivity) : Dialog(parentActivity) {

    private val saveButton by lazy { findViewById<Button>(R.id.activity_ledger_new_entry_dialog_saveButton) }
    private val cancelButton by lazy { findViewById<Button>(R.id.activity_ledger_new_entry_dialog_cancelButton) }
    private val typeSpinner by lazy { findViewById<Spinner>(R.id.activity_ledger_new_entry_dialog_typeSpinner) }
    private val descriptionBox by lazy { findViewById<EditText>(R.id.activity_ledger_new_entry_dialog_descriptionBox) }
    private val categorySpinner by lazy { findViewById<Spinner>(R.id.activity_ledger_new_entry_dialog_categorySpinner) }
    private val amountBox by lazy { findViewById<EditText>(R.id.activity_ledger_new_entry_dialog_amountBox) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_ledger_new_entry_dialog)

        setUpBoxes()

        saveButton.setOnClickListener{
            if (validateUserInputFormat()) {
                val entry = Entry.createEmptyEntry()
                entry.id = Entry.createId(parentActivity.viewModel.currentUser)
                entry.username = parentActivity.viewModel.currentUser
                entry.type = typeSpinner.selectedItem.toString()
                entry.category = categorySpinner.selectedItem.toString()
                entry.description = descriptionBox.text.toString()
                entry.amount = amountBox.text.toString().toDouble()
                entry.lastUpdate = Time.getTimestamp()
                entry.date = Time.getDate()
                entry.isDeleted = false
                parentActivity.viewModel.createEntry(entry)
                parentActivity.adapter.notifyItemInserted(parentActivity.adapter.itemCount + 1)
                dismiss()
            }
        }

        cancelButton.setOnClickListener{
            dismiss()
        }
    }

    private fun setUpBoxes(){
        val typeAdapter = ArrayAdapter(context, R.layout.activity_ledger_dialog_spinner, arrayOf("Income", "Expense"))
        typeSpinner.adapter = typeAdapter
        typeSpinner.setSelection(0)
        val categoryAdapter = ArrayAdapter(context, R.layout.activity_ledger_dialog_spinner, getCategoriesList())
        categorySpinner.adapter = categoryAdapter
        categorySpinner.setSelection(1)
    }

    private fun getCategoriesList() : ArrayList<String>{
        //TODO this is a mock for testing. This function must be implemented to draw categories from the database
        return arrayListOf("bill","consumable","electronic","entertainment","food","gift","house item","junk food","loan"
            ,"medical","miscellaneous","paycheck","transportation")
    }

    private fun validateUserInputFormat(): Boolean {
        var flag = true
        if (descriptionBox.text.toString() == "") {
            descriptionBox.error = "Enter description"
            flag = false
        }
        if (amountBox.text.toString() == "") {
            amountBox.error = "Enter amount"
            flag = false
        }
        return flag
    }
}