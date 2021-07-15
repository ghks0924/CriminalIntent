package com.example.criminalintent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.criminalintent.Crime
import java.util.*

@Dao
interface CrimeDao {

    @Query("SELECT * FROM crime")
//    fun getCrimes(): List<Crime> 라이브데이터를 사용해서 주석처리
    fun getCrimes(): LiveData<List<Crime>>

    @Query("SELECT * FROM crime where id =(:id)")
//    fun getCrime(id:UUID): Crime? 라이브데이터를 사용해서 주석처리
    fun getCrime(id:UUID): LiveData<Crime?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(crime :Crime)



}