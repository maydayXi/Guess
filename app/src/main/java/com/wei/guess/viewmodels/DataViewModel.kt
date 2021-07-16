package com.wei.guess.viewmodels

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

object DataViewModel {
    // <summary> 資料類別：每一筆猜數字結果 </summary>
    // <param name="count"> 使用者猜次數 </param>
    // <param name="guess"> 使用者輸入數字 </param>
    // <param name="result"> 判斷結果 </param>
    @Parcelize
    data class ResultViewModel(
        var count: String,
        var guess: String,
        var result: String) : Parcelable

    // <summary> 資料類別：玩家紀錄 </summary>
    // <param name="rank"> 使用排名 </param>
    // <param name="count"> 使用者猜次數 </param>
    // <param name="datetime"> 遊戲日期時間 </param>
    data class RecordViewModel(
        var rank: String,
        var count: String,
        var datetime: String)

    // <summary> initial ResultViewModel Data </summary>
    // <param name="count"> User's guess Times </param>
    // <param name="guess"> User Input </param>
    // <param name="result"> Application Output </summary>
    fun initialResultViewModel(count: String, guess: String, result: String)
        = ResultViewModel(count, guess, result)

    // <summary> initial RecordViewModel Data </summary>
    // <param name="rank"> User's rank </param>
    // <param name="count"> User guess times </param>
    // <param name="result"> What time user play </summary>
    fun initialRecordViewModel(rank: String, count: String, datetime: String)
        = RecordViewModel(rank, count, datetime)
}
