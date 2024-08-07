package com.example.myemotion.db.entity

/**
 * Rappresenta un consiglio associato a una specifica emozione.
 *
 * @property id L'ID univoco del consiglio, utilizzato come chiave primaria nel database. Di default è 0 e viene automaticamente gestito dal database.
 * @property consiglio Il testo del consiglio fornito.
 * @property idEmozione L'ID dell'emozione associata a questo consiglio. È una chiave esterna che collega il consiglio all'emozione specifica.
 * @property numDiVolteVisualizzata Il numero di volte che questo consiglio è stato visualizzato. Viene utilizzato per determinare quale consiglio mostrare in base alla frequenza di visualizzazione.
 */
data class Consiglio(
    val id: Int = 0, // ID del consiglio, con valore predefinito 0
    val consiglio: String, // Testo del consiglio
    val idEmozione: Int, // ID dell'emozione associata
    val numDiVolteVisualizzata: Int // Numero di volte che il consiglio è stato visualizzato
)
