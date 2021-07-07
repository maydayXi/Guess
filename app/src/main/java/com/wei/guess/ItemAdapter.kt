package com.wei.guess

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_item_model.view.*

// <summary> Data Adapter </summary>
class ItemAdapter(private var dataSet: ArrayList<ItemModel>):
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    // <summary> Initial View Component </summary>
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val lblCount = view.lblCount
        val lblGuess = view.lblGuess
        val lblResult = view.lblResult
    }

    // To display data on the view
    // <summary> Create new views </summary>
    // <param name=""> </param>
    // <param name=""> </param>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item_model, parent, false)

        return ViewHolder(view)
    }

    // <summary> Replace the content of a view </summary>
    // <param name="holder"> </param>
    // <param name="position"> Data Index </param>
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.lblCount.text = dataSet[position].count
        holder.lblGuess.text = dataSet[position].guess
        holder.lblResult.text = dataSet[position].result
    }

    // <summary> Get Data Count </summary>
    // <return> Data Size </return>
    override fun getItemCount() = dataSet.size

    // <summary> 新增資料 </summary>
    // <param name="item"> [count, guess, result] </param>
    fun add(item: ItemModel) {
        dataSet.add(item)
        notifyItemInserted(dataSet.size)
    }

    // <summary> 重設資料 </summary>
    fun reset() {
        dataSet = arrayListOf(
            ItemModel(
                count = "Count",
                guess = "Guess",
                result = "Result"
        ))
        notifyDataSetChanged()
    }
}