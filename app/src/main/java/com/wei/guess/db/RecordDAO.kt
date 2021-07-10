package com.wei.guess.db

import androidx.room.*

// <summary>
//  Record Model DAOs Interface
//  Interact with Database
// </summary>
@Dao
interface RecordDAO {
    // <summary> Get All Records </summary>
    // <return> All Game Record: ArrayList<RecordDataModel> </return>
    @Query("SELECT * FROM Guess_Record")
    fun getAll(): List<RecordDataModel>

    // <summary> Query function </summary>
    // <return> All Record Order by Count </return>
    @Query("SELECT * FROM Guess_Record ORDER BY count")
    fun getAllOrderByRank(): List<RecordDataModel>

    // <summary> Insert function </summary>
    // <param name="record"> Record Data Model Instance what you want to insert </param>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg record: RecordDataModel)

    // <summary> Delete All Data </summary>
    // <param name="records"> Record Data Model List what you want to delete </param>
    @Delete
    fun deleteAll(records: ArrayList<RecordDataModel>)
}