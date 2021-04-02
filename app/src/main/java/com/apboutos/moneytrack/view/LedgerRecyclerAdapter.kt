@file:Suppress("unused")

package com.apboutos.moneytrack.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apboutos.moneytrack.R
import com.apboutos.moneytrack.model.database.entity.Entry
import com.apboutos.moneytrack.utilities.converter.CurrencyConverter


class LedgerRecyclerAdapter(private val dataSet : ArrayList<Entry>, private val parentActivity: LedgerActivity) : RecyclerView.Adapter<LedgerRecyclerAdapter.EntryViewHolder>() {

    var listener : OnItemClickListener? = null
    private val currency = "$"

    /**
     * This is a placeholder class for caching the layout and the touch listener
     * of a recycler view's item.
     */
    inner class EntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val description : TextView = itemView.findViewById(R.id.row_item_descriptionBox)
        val category : TextView = itemView.findViewById(R.id.row_item_categoryBox)
        val amount : TextView = itemView.findViewById(R.id.row_item_amountBox)
        init {
            itemView.setOnClickListener{
                if(listener!=null && adapterPosition != RecyclerView.NO_POSITION)
                listener?.onItemClick(adapterPosition)
            }
        }
    }


    /**
     * Creates a new EntryViewHolder object and initializes it's itemView using the parent activity's
     * LayoutInflater.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType : Int): EntryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_ledger_row_item,parent,false)
        return EntryViewHolder(itemView)
    }

    /**
     * Map the values of the dataSet to the corresponding EntryViewHolder of the recycler view.
     */
    override fun onBindViewHolder(holder : EntryViewHolder, position : Int) {
        val currentItem = dataSet[position]
        holder.category.text = currentItem.category
        holder.description.text = currentItem.description
        holder.amount.text = CurrencyConverter.toPresentableAmount(currentItem.amount,currency)
        if(currentItem.type == "Expense"){
            holder.amount.setTextColor(parentActivity.getColor(R.color.red))
            holder.category.setTextColor(parentActivity.getColor(R.color.red))
            holder.description.setTextColor(parentActivity.getColor(R.color.red))
        }else{
            holder.amount.setTextColor(parentActivity.getColor(R.color.light_oily_green))
            holder.category.setTextColor(parentActivity.getColor(R.color.light_oily_green))
            holder.description.setTextColor(parentActivity.getColor(R.color.light_oily_green))
        }
    }

    /**
     * Returns the size of the current data set.
     */
    override fun getItemCount(): Int = dataSet.size

    /**
     * Custom interface for providing the EntryViewHolder class with a touch listener.
     */
    interface OnItemClickListener{
        fun onItemClick(position : Int)
    }

}