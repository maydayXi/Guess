package com.wei.guess.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// <summary>
//  資料類別：存入資料庫的紀錄
//  @Entity：宣告為 Room Entity 物件，tableName 屬性：指定資料表的名稱
//  </summary>
// <param name="id"> Data Identification </param>
// <param name="count"> Guess Count use for rank </summary>
// <param name="datetime"> Play Datetime </summary>
@Entity(tableName = "Guess_Record")
data class RecordDataModel (
    // @ColumnInfo，name 屬性，定義資料欄位名稱
    @ColumnInfo(name = "count") var count: Int,
    @ColumnInfo(name = "datetime") var datetime: String
) {
    // @PrimaryKey：定義 PK，自動產生 Id，遞增
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}