package com.example.myemotion.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myemotion.db.entity.StatoEmozionale

@Dao
interface StatoEmozionaleDao {
    @Insert
    fun inserisciStatoEmozionale(statoEmozionale: StatoEmozionale)

    @Query("SELECT * FROM stato_emozionale")
    fun getStatiEmozionali(): List<StatoEmozionale>
}
