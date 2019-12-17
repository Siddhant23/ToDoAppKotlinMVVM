package com.eko.sidtestandroid.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.eko.sidtestandroid.model.data.TODO

@Dao
interface TodoDAO {
    @Query("SELECT * FROM todo")
    fun getAll(): List<TODO>

    @Insert
    fun insertAll(users: List<TODO>)
}