package com.eko.sidtestandroid.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class TODO(
    @ColumnInfo(name = "userId") var userId: String?,
    @NotNull  @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "completed") var completed: Boolean
)