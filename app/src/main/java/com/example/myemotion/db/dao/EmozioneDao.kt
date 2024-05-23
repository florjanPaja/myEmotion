package com.example.myemotion.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myemotion.db.entity.Emozione

@Dao
interface EmozioneDao {
    @Insert
    fun inserisciEmozione(emozione: Emozione)

    @Query("SELECT * FROM emozioni")
    fun getEmozioni(): List<Emozione>

    @Query("SELECT nome,descrizione FROM emozioni")
    fun getEmozioniList(): List<Emozione>

}
