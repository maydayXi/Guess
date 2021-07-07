package com.wei.guess

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.wei.guess.databinding.ActivityMaterialMainBinding
import kotlinx.android.synthetic.main.content_material_main.*

class MaterialMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMaterialMainBinding
    private val secretNumber = SecretNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMaterialMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // 重設所有畫面元件
        resetViews()

        // 重玩事件
        binding.fab.setOnClickListener { view ->
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.title_replay))
                .setMessage(getString(R.string.msg_replay))
                .setPositiveButton(getString(R.string.dialog_btn_yes)) {
                    dialog, view ->
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
            title = getString(R.string.title_bingo)
            result += "\n${secretNumber.answer}"
        }
        else
            title = getString(R.string.title_try_again)

        // 顯示結果
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(result)
            .show()

        lblCount.append("\n${secretNumber.guess_cnt}")
        lblGuess.append("\n${secretNumber.input}")
        lblResult.append("\n$result")
    }

    // <summary> 重設畫面元件，遊戲資料 </summary>
    private fun resetViews() {
        edtInput.setText("")
        lblCount.text = getString(R.string.lbl_count)
        lblGuess.text = getString(R.string.lbl_guess)
        lblResult.text = getString(R.string.lbl_result)
        secretNumber.input = ""
        secretNumber.generateSecret()
    }
}
