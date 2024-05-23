package com.example.myemotion.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myemotion.db.entity.Consiglio

@Dao
interface ConsiglioDao {
    @Insert
    fun inserisciConsiglio(consiglio: Consiglio)

    @Query("SELECT * FROM consigli WHERE idEmozione = :idEmozione")
    fun getConsigliByEmozione(idEmozione: Int): List<Consiglio>
}
