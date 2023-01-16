package com.example.familysafety

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contactModel: Contactmodel)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(contactModelList: List<Contactmodel>)



    @Query("SELECT * FROM contactmodel")
     fun getAllContacts(): LiveData<List<Contactmodel>>
}