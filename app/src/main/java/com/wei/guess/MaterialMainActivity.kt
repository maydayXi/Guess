package com.wei.guess

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wei.guess.adapters.ResultAdapter
import com.wei.guess.databinding.ActivityMaterialMainBinding
import com.wei.guess.db.DBRepository
import com.wei.guess.db.RecordDataModel
import com.wei.guess.db.ioThread
import com.wei.guess.viewmodels.ResultViewModel
import kotlinx.android.synthetic.main.content_material_main.*
import java.text.SimpleDateFormat
import java.util.*

class MaterialMainActivity : AppCompatActivity() {

    // region class member
    // For log.d Method
    private val TAG = MaterialMainActivity::class.java.simpleName

    private lateinit var binding: ActivityMaterialMainBinding
    // SecretNumber Instance
    val secretNumber = SecretNumber()
    // Result View Model Dataset
    private var dataSet = arrayListOf<ResultViewModel>()
    // Database Manipulation Instance
    private lateinit var repository: DBRepository
    // Data Adapter Instance
    private lateinit var dataAdapter: ResultAdapter

    // Local Variable
    private val dateFormat =    // Date Format Instance
        SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    private var count = ""      // User Guess Count
    private var guess = ""      // User Input
    private var result = ""     // SecretNumber Output
    private var now = ""        // Now string
    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMaterialMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        repository = DBRepository(this)

        // 初始使用資料
        initialData()
        // 初始視覺元件
        precessView()
        // 重設所有畫面元件
        resetViews()

        // 重玩事件
        binding.fabReplay.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.title_replay))
                .setMessage(getString(R.string.msg_replay))
                .setPositiveButton(getString(R.string.dialog_btn_yes)) {
                        _, _ ->
                    // 重設所有資料
                    resetViews()
                    Toast.makeText(this, getString(R.string.toast_msg_replay), Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(getString(R.string.dialog_btn_no), null)
                .show()
        }

        Log.d(TAG, "onCreate: ")
    }

    // region lifecycle
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
    // endregion

    // region button click event
    // <summary> 驗證按鈕事件 </summary>
    fun validateInput(view: View) {
        var result = ""
        var title = ""

        // 取得使用者輸入
        secretNumber.input = edtInput.text.toString()
        // 驗證使用者輸入
        result = secretNumber.validate()

        // 判斷結果
        if(result.contains("4A")) {
            if(secretNumber.guess_cnt < 3) {
                title = getString(R.string.title_excellent)
                result = "${getString(R.string.msg_excellent)} ${secretNumber.answer}"
            }
            else {
                title = getString(R.string.title_bingo)
                result += "\n${secretNumber.answer}"
            }
        }
        else
            title = getString(R.string.title_try_again)

        // Show Result
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(result)
            .let {
                if(title.contains(getString(R.string.title_bingo)) ||
                    title.contains(getString(R.string.title_excellent))) {
                    it.setPositiveButton(getString(R.string.btn_ok)) {
                        _, _ ->
                        edtInput.isEnabled = false
                        // 產生時間字串
                        now = dateFormat.format(Calendar.getInstance().time)
                        // 新增一筆遊戲紀錄
                        val record = RecordDataModel(secretNumber.guess_cnt, now)
                        ioThread { repository.insertRecord(record) }
                        // 導向紀錄畫面
                        val intent = Intent(this, RecordActivity::class.java)
                        intent.putExtra("GuessCount", secretNumber.guess_cnt)
                        startActivity(intent)
                    }.show()
                }
                else
                    it.show()
            }

        result = if(title.contains(getString(R.string.title_excellent)))
            "4A0B"
        else
            result

        // 視覺元件處理
        // 新增一筆結果
        dataAdapter.add(
            ResultViewModel(count = secretNumber.guess_cnt.toString(),
            guess = secretNumber.input, result = result)
        )
        edtInput.setText("")
    }
    // endregion

    // region initial
    // <summary> Initial View Components </summary>
    private fun precessView() {
        rycResult.apply {
            layoutManager = LinearLayoutManager(this.context)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this.context,
                DividerItemDecoration.VERTICAL))
            dataAdapter = ResultAdapter(dataSet)
            adapter = dataAdapter
        }
    }

    // <summary> Initial Data </summary>
    private fun initialData() {
        count = getString(R.string.lbl_count)
        guess = getString(R.string.lbl_guess)
        result = getString(R.string.lbl_result)

        // Initial First Row Data
        dataSet.add(
            ResultViewModel(
                count = count,
                guess = guess,
                result = result
            )
        )
    }

    // <summary> 重設畫面元件，遊戲資料 </summary>
    private fun resetViews() {
        edtInput.setText("")
        edtInput.isEnabled = true
        dataAdapter.reset(count, guess, result)
        secretNumber.input = ""
        secretNumber.guess_cnt = 0
        secretNumber.generateSecret()
    }
    // endregion
}
