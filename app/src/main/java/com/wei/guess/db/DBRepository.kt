package com.wei.guess.db

import android.content.Context

// <summary> Data Manipulation Class </summary>
// <param name="context"> 埸景物件 </param>
class DBRepository constructor(context: Context) {

    // 資料庫物件
    private val db = RecordsDatabase.getDatabase(context)
    // 資料庫操作物件
    private val recordDAO = db.recordDAO()

    // <summary> 取得所有資料 </summary>
    // private fun getAllRecords() = recordDAO.getAll()

    // <summary> 取得排名資料 </summary>
    fun fetRankRecord() = recordDAO.getAllOrderByRank()

    // <summary> 新增遊戲紀錄 </summary>
    fun insertRecord(record: RecordDataModel) = recordDAO.insert(record)

    // <summary> 刪除所有紀錄 </summary>
    // fun deleteAllRecord() = recordDAO.deleteAll(getAllRecords())


}