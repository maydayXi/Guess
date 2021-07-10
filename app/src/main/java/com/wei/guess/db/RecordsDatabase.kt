package com.wei.guess.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// <summary>
//  App's Database
//  Main access point to the data
// </summary>
@Database(entities = [RecordDataModel::class], version = 1)
abstract class RecordsDatabase: RoomDatabase() {
    // <summary> Get RecordDAO Instance </summary>
    abstract fun recordDAO(): RecordDAO

    companion object {
        private var db: RecordsDatabase? = null
        private const val DB_NAME = "guess.db"

        // <summary> Get Database instance </summary>
        // <return> RecordsDatabase instance </return>
        fun getDatabase(context: Context): RecordsDatabase =
            db ?: synchronized(this) {
                db ?: buildDatabase(context).also { db = it}
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, RecordsDatabase::class.java, DB_NAME)
                .build()
    }
}