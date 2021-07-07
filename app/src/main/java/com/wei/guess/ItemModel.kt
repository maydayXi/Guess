package com.wei.guess

// <summary> 資料類別：每一個互動果 </summary>
// <param name="count"> 使用者猜次數 </param>
// <param name="guess"> 使用者輸入數字 </param>
// <param name="result"> 互動結果 </param>
data class ItemModel(var count: String, var guess: String, var result: String)