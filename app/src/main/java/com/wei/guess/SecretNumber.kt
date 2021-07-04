package com.wei.guess

import android.util.Log
import java.util.*

class SecretNumber {
    // <field> 答案 </field>
    private var secret = ""
    var input = ""

    // <field> 使用者猜的次數 </field>
    // var guess_cnt = 0
    var a = 0
    var b = 0

    // <summary> 產生答案 </summary>
    fun generateSecret() {
        val numbers = (0..9).toMutableList()

        (0..3).forEach { _ ->
            val number = Random().nextInt(numbers.size)
            secret += numbers.removeAt(number).toString()
        }

        Log.d("SecretNumber", "generateSecret: $secret")
    }

    // <summary> 驗證使用者輸入 </summary>
    // <return> XAYB </return>
    fun validate(): String {
        val match = secret.filter { it in input }
        a = 0
        b = 0

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
            if(secret.indexOf(it) == input.indexOf(it))
                a++
            else
                b++
        }
    }

    // <summary> 顯示答案 </summary>
    fun printSecret() = println(secret)
}

//fun main() {
//    // 建立 SecretNumber 物件
//    println("Game Start\n Please input your Number：")
//    val secretNumber = SecretNumber()
//
//    secretNumber.input = readLine().toString()
//    secretNumber.generateSecret()
//    secretNumber.printSecret()
//
//    do {
//        val output = secretNumber.validate()
//        println("$output\nPlease guess again：")
//        secretNumber.input = readLine().toString()
//        if(secretNumber.input == "exit")
//            break
//    } while (output != "4A0B")
//}
