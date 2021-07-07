package com.wei.guess

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.wei.guess.databinding.ActivityMaterialMainBinding
import kotlinx.android.synthetic.main.content_material_main.*

class MaterialMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMaterialMainBinding
    private val secretNumber = SecretNumber()
    private var dataSet = arrayListOf<ItemModel>()
    private lateinit var dataAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMaterialMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        // Initial First Row Data
        dataSet.add(ItemModel(count = "Count", guess = "Guess", result = "Result"))
        dataAdapter = ItemAdapter(dataSet)

        rycResult.layoutManager = LinearLayoutManager(this)
        rycResult.setHasFixedSize(false)

        rycResult.adapter = dataAdapter

        // 重設所有畫面元件
        resetViews()

        // 重玩事件
        binding.fab.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.title_replay))
                .setMessage(getString(R.string.msg_replay))
                .setPositiveButton(getString(R.string.dialog_btn_yes)) {
                        _, _ ->
                    // 重設所有資料
                    resetViews()
                    Toast.makeText(this, getString(R.string.toast_msg), Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(getString(R.string.dialog_btn_no), null)
                .show()
        }
    }

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
                result = getString(R.string.msg_excellent) + secretNumber.answer
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
            .show()

        result = if(title.contains(getString(R.string.title_excellent)))
            "4A0B"
        else
            result

        // 視覺元件處理
        // 新增一筆結果
        dataAdapter.add(ItemModel(count = secretNumber.guess_cnt.toString(),
            guess = secretNumber.input, result = result))
        edtInput.setText("")
    }

    // <summary> 重設畫面元件，遊戲資料 </summary>
    private fun resetViews() {
        edtInput.setText("")
        dataAdapter.reset()
        secretNumber.input = ""
        secretNumber.guess_cnt = 0
        secretNumber.generateSecret()
    }
}
