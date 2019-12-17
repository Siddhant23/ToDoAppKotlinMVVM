package com.eko.sidtestandroid.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [com.eko.sidtestandroid.model.data.TODO::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time. 
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "todoSid.db"

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}