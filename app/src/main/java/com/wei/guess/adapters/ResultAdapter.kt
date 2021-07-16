package com.wei.guess.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wei.guess.R
import com.wei.guess.viewmodels.DataViewModel
import kotlinx.android.synthetic.main.row_result_view.view.*

// <summary> Result Data Adapter </summary>
// <param name="dataSet"> 要介接的資料 </summary>
class ResultAdapter(private var dataSet: ArrayList<DataViewModel.ResultViewModel>):
    RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

    // <summary> Initial View Component </summary>
    // <param name="view">  </param>
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val lblCount = view.lblCountRecord!!
        val lblGuess = view.lblGuess!!
        val lblResult = view.lblResult!!
    }

    // To display data on the view
    // <summary> Create new views </summary>
    // <param name="parent"> </param>
    // <param name="viewType"> </param>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_result_view, parent, false)

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
    fun add(result: DataViewModel.ResultViewModel) {
        dataSet.add(result)
        notifyItemInserted(dataSet.size)
    }

    // <summary> 取得資料集 </summary>
    fun getDataset() = dataSet

    // <summary> 重設資料 </summary>
    // <param name="count"> 第一欄名稱 </param>
    // <param name="guess"> 第二欄名稱 </param>
    // <param name="result"> 第三欄名稱 </param>
    fun reset(count: String, guess: String, result: String) {
        dataSet = arrayListOf(
            DataViewModel.initialResultViewModel(
                count = count, guess = guess, result = result
            )
        )
        notifyDataSetChanged()
    }
}
