package com.example.simpletodo

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface TaskDao {
    @Query("SELECT * from task")
    fun getAll(): LiveData<List<Task>>

    @Query("SELECT * from task WHERE text LIKE :word")
    fun getSome(word: String): LiveData<List<Task>>

    @Insert(onConflict = REPLACE)
    fun insert(task: Task)

    @Insert(onConflict = REPLACE)
    fun insertAll(task: List<Task>)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)

    @Delete
    fun deleteAll(task: List<Task>)

    @Query("DELETE FROM task")
    fun deleteAll()

    @Query("DELETE FROM task WHERE done == 1")
    fun deleteDone()

    @Query("UPDATE task SET done = :checked WHERE id == :task")
    fun setDone(task: Long, checked: Boolean)
}