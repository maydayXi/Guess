package com.wei.guess

import android.util.Log
import java.util.*

class SecretNumber {
    private val TAG = SecretNumber::class.java.simpleName

    // <field> 答案 </field>
    var answer = ""
    // <field> 使用者輸入 </field>
    var input = ""

    // <field> 使用者猜的次數 </field>
    var guess_cnt = 0
    // <field> 變數 A </field>
    private var a = 0
    // <field> 變數 B </field>
    private var b = 0

    // <summary> 產生答案 </summary>
    fun generateSecret() {
        answer = (0..9)
            .toMutableList()
            .shuffled()
            .take(4)
            .joinToString("")

         Log.d(TAG, "generateSecret: $answer")
    }

    // <summary> 驗證使用者輸入 </summary>
    // <return> XAYB </return>
    fun validate(): String {
        val match = answer.filter { it in input }
        a = 0
        b = 0
        guess_cnt ++

        when(match.length) {
            4 -> calc(match)
            3 -> calc(match)
            2 -> calc(match)
            1 -> calc(match)
            else -> {
                a = 0
                b = 0
            }
        }

        return "${a}A${b}B"
    }

    // <summary> 計算驗證結果 </summary>
    private fun calc(match: String) {
        match.forEach {
            if(answer.indexOf(it) == input.indexOf(it))
                a++
            else
                b++
        }
    }
}