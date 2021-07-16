package com.wei.guess

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wei.guess.adapters.RecordAdapter
import com.wei.guess.adapters.ResultAdapter
import com.wei.guess.databinding.ActivityMaterialGameBinding
import com.wei.guess.db.DBRepository
import com.wei.guess.db.RecordDataModel
import com.wei.guess.db.ioThread
import com.wei.guess.viewmodels.DataViewModel as VM
import kotlinx.android.synthetic.main.content_material_main.*
import java.text.SimpleDateFormat
import java.util.*

class MaterialGameActivity : AppCompatActivity() {

    // region class member
    // For log.d Method
    private val TAG = MaterialGameActivity::class.java.simpleName
    private val DATASET_KEY = "DATASET_KEY"
    private val USER_INPUT_KEY = "USER_INPUT_KEY"
    private val ANSWER_KEY = "ANSWER_KEY"

    private lateinit var binding: ActivityMaterialGameBinding
    // SecretNumber Instance
    val secretNumber = SecretNumber()
    // Result View Model Dataset
    private var dataSet = arrayListOf<VM.ResultViewModel>()
    // Database Manipulation Instance
    private lateinit var repository: DBRepository
    // Data Adapter Instance
    private lateinit var resultAdapter: ResultAdapter

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

        binding = ActivityMaterialGameBinding.inflate(layoutInflater)
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

    // <summary> Save Game data </summary>
    // <param name='outState'> Save Game Data Bundle Instance </param>
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(USER_INPUT_KEY, edtInput.text.toString())
        outState.putParcelableArrayList(DATASET_KEY,
            resultAdapter.getDataset())
        outState.putString(ANSWER_KEY, secretNumber.answer)
        Log.d(TAG, "onSaveInstanceState: User Input is ${edtInput.text}" +
                "\n Dataset size is ${resultAdapter.itemCount}" +
                "\n secret number is ${secretNumber.answer}")
        super.onSaveInstanceState(outState)
    }

    // <summary> View Data restore </summary>
    // <param name='saveInstanceState'> For save view data </param>
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // restore user input
        edtInput.setText(
            savedInstanceState.getString(USER_INPUT_KEY, ""))

        // restore guess data
        dataSet = savedInstanceState.getParcelableArrayList(DATASET_KEY)
            ?: arrayListOf(VM.initialResultViewModel(count, guess, result))
        resultAdapter = ResultAdapter(dataSet)
        rycResult.adapter = resultAdapter

        // restore secret number
        secretNumber.answer = savedInstanceState.getString(ANSWER_KEY
            , "")

        Log.d(TAG, "onRestoreInstanceState: Dataset Size is ${dataSet.size}" +
                "\nUser Input is ${edtInput.text}" +
                "\nsecret number is ${secretNumber.answer}")

        super.onRestoreInstanceState(savedInstanceState)
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
                        val bundle = Bundle()
                        bundle.putInt("GuessCount", secretNumber.guess_cnt)
                        bundle.putInt("ActivityEntry", ActivityCode.GAME_ACTIVITY.ordinal)
                        intent.putExtras(bundle)
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
        resultAdapter.add(
            VM.ResultViewModel(
                count = secretNumber.guess_cnt.toString(),
                guess = secretNumber.input,
                result = result
            )
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
            resultAdapter = ResultAdapter(dataSet)
            adapter = resultAdapter
        }
    }

    // <summary> Initial Data </summary>
    private fun initialData() {
        count = getString(R.string.lbl_count)
        guess = getString(R.string.lbl_guess)
        result = getString(R.string.lbl_result)

        // Initial First Row Data
        dataSet.add(
            VM.initialResultViewModel(
                count, guess, result
            )
        )
    }

    // <summary> 重設畫面元件，遊戲資料 </summary>
    private fun resetViews() {
        edtInput.setText("")
        edtInput.isEnabled = true
        resultAdapter.reset(count, guess, result)
        secretNumber.input = ""
        secretNumber.guess_cnt = 0
        secretNumber.generateSecret()
    }
    // endregion
}
