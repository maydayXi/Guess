package com.wei.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val secretNumber = SecretNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 產生答案
        secretNumber.generateSecret()
    }

    // <summary> 按鈕事件 </summary>
    fun goValidate(view: View) {
        val user_input = edtUserInput.text.toString()
        // 設定使用者輸入
        secretNumber.input = user_input
        // 驗證使用者輸入
        val result = secretNumber.validate()
        val title = when(result) {
            "4A0B" -> getString(R.string.title_bingo)
            else -> getString(R.string.title_try_again)
        }

//        Toast.makeText(this, result, Toast.LENGTH_LONG)
//            .show()

        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(result)
            .show()
    }
}