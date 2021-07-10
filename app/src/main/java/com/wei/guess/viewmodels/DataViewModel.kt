package com.wei.guess.viewmodels

import java.util.*

// <summary> 資料類別：每一筆猜數字結果 </summary>
// <param name="count"> 使用者猜次數 </param>
// <param name="guess"> 使用者輸入數字 </param>
// <param name="result"> 判斷結果 </param>
data class ResultViewModel(var count: String, var guess: String, var result: String)

// <summary> 資料類別：玩家紀錄 </summary>
// <param name="rank"> 使用排名 </param>
// <param name="count"> 使用者猜次數 </param>
// <param name="datetime"> 遊戲日期時間 </param>
data class RecordViewModel(var rank: String, var count: String, var datetime: String)