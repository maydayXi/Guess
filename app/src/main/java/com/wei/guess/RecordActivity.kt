package com.wei.guess

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wei.guess.adapters.RecordAdapter
import com.wei.guess.db.DBRepository
import com.wei.guess.db.ioThread
import com.wei.guess.viewmodels.RecordViewModel
import kotlinx.android.synthetic.main.activity_record.*

class RecordActivity : AppCompatActivity() {

    // Database Manipulate instance
    private var repository = DBRepository(this)
    // Data adapter instance
    private lateinit var adapter: RecordAdapter
    // Data container
    private var recordSet = ArrayList<RecordViewModel>()

    // Variable
    private var guess_cnt = 0       // User Guess Times
    private var rank = ""           // User Rank
    private var count = ""          // User Record Times
    private var datetime = ""       // User play Datetime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        // 變數初始化
        initialData()
        // 視覺元件初始化
        processViews()

        // 刪除鈕事件
        fabDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_title_delete))
                .setMessage(getString(R.string.dialog_msg_delete))
                .setPositiveButton(getString(R.string.dialog_btn_yes)) {
                        _, _ ->
                    // 刪除所有紀錄
                    ioThread { repository.deleteAllRecord() }
//                    reset()
//                    Toast.makeText(this,
//                        "Record delete finished",
//                        Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(getString(R.string.dialog_btn_no), null)
                .show()
        }
    }

    // <summary> Initial Variable </summary>
    private fun initialData() {
        rank = getString(R.string.lbl_rank)
        count = getString(R.string.lbl_count)
        datetime = getString(R.string.lbl_datetime)
        // Add Record Column name
        recordSet.add(RecordViewModel(
            rank = rank,
            count = count,
            datetime = datetime)
        )

        // Add Record Data
        ioThread {
            val record = repository.fetRankRecord()
            record.forEachIndexed {
                    index, item ->
                    recordSet.add(RecordViewModel("${index + 1}",
                        item.count.toString(),
                        item.datetime))
            }
        }
    }

    // <summary> Initial View Components </summary>
    private fun processViews() {
        // 設定 Toolbar 物件
        setSupportActionBar(RecordToolbar)

        // 上一個畫面傳來的資料：使用者猜次數
        guess_cnt = intent.getIntExtra("GuessCount", 0)

        // 顯示本次猜的次數
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.record_dialog_title))
            .setMessage("$guess_cnt ${getString(R.string.record_dialog_times)}")
            .show()

        // Initial Recycler View
        rycRecord.apply {
            // Set Recycler View Layout
            layoutManager = LinearLayoutManager(this.context)
            setHasFixedSize(true)
            // Set Recycler View Divider
            addItemDecoration(DividerItemDecoration(this.context,
                DividerItemDecoration.VERTICAL))
            // Set Recycler View Adapter
            adapter = RecordAdapter(recordSet)
            adapter = adapter
        }
    }

    // <summary> 重設排名資料 </summary>
    private fun reset() {
        adapter.reset(rank = rank, count = count, datetime = datetime)
    }

    // <summary> 返回按鈕事件 </summary>
    // <param name='item'> Toolbar 按鈕項目 </param>
    // <return> true / false </return>
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}