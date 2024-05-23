package com.example.myemotion.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface JoinDao {


    // Esempio di query con join già definiti
    @Query("""
        SELECT e.nome AS emozioneNome, e.descrizione AS emozioneDescrizione, c.consiglio AS consiglio
        FROM emozioni e
        INNER JOIN consigli c ON e.id = c.idEmozione
    """)
    fun getEmozioniConsigli():List<Map<String,Any>>

    // Esempio di query con join già definiti
    @Query("""
        SELECT e.nome AS emozioneNome, e.descrizione AS emozioneDescrizione, s.intensita AS intensita, s.nota AS nota
        FROM emozioni e
        INNER JOIN stato_emozionale s ON e.id = s.idEmozione
    """)
    fun getEmozioniStatiEmozionali(): List<Map<String,Any>>


    // Metodo generico per eseguire una query personalizzata
    @RawQuery
    fun executeCustomQuery(query: SupportSQLiteQuery): List<Map<String, Any>>

    // Metodo per convertire i risultati in una lista di HashMap
    fun getQueryResultAsHashMapList(queryString: String): List<HashMap<String, Any>> {
        val query = SimpleSQLiteQuery(queryString)
        val result = executeCustomQuery(query)
        val listHashMap = mutableListOf<HashMap<String, Any>>()
        for (row in result) {
            val hashMap = HashMap<String, Any>()
            for ((key, value) in row) {
                hashMap[key] = value
            }
            listHashMap.add(hashMap)
        }
        return listHashMap
    }
}


