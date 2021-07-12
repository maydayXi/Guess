package com.wei.guess.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wei.guess.R
import com.wei.guess.viewmodels.RecordViewModel
import kotlinx.android.synthetic.main.row_record_view.view.*

// <summary> Record Data Adapter </summary>
// <param name="dataSet"> 要介接的資料 </param>
class RecordAdapter(private var dataSet: ArrayList<RecordViewModel>)
    : RecyclerView.Adapter<RecordAdapter.ViewHolder>() {

    // <summary> Initial View Component </summary>
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val lblRank = view.lblRankRecord!!
        val lblCount = view.lblCountRecord!!
        val lblDate = view.lblDatetimeRecord!!
    }

    // <summary> Create New View to display data </summary>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_record_view, parent, false)
        return ViewHolder(view)
    }

    // <summary> Replace the content of a view </summary>
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.lblRank.text = dataSet[position].rank
        holder.lblCount.text = dataSet[position].count
        holder.lblDate.text = dataSet[position].datetime
    }

    // <summary> 取得資料筆數 </summary>
    override fun getItemCount() = dataSet.size

    // <summary> 刪除所有資料 </summary>
    fun reset(rank: String, count: String, datetime: String) {

        Log.d(RecordAdapter::class.java.simpleName, "reset: ")
        dataSet = arrayListOf(
            RecordViewModel(
                rank = rank,
                count = count,
                datetime = datetime
            )
        )
        notifyDataSetChanged()
    }
}