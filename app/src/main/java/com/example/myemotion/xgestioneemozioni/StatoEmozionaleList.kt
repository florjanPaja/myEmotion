package com.example.myemotion.xgestioneemozioni

/**
 * La classe StatoEmozionaleList gestisce una lista di oggetti EmozioneStatistica e fornisce
 * metodi per accedere ai dati, calcolare percentuali e convertire i dati in formati specifici.
 *
 * @property data La lista di oggetti EmozioneStatistica che contiene le statistiche delle emozioni.
 * @property keys La lista delle chiavi (nomi delle emozioni) estratte dalla lista dei dati.
 * @property values La lista dei valori pesati, calcolati come media intensità moltiplicata per il conteggio.
 */
class StatoEmozionaleList(data: List<EmozioneStatistica>) {

    private var data: List<EmozioneStatistica> = data
    private var keys: List<String> = data.map { it.nome } // Lista delle chiavi estratte dalla lista dei dati
    private var values: List<Double> = data.map { it.mediaIntensita * it.conteggio } // Lista dei valori pesati

    /**
     * Restituisce il numero di elementi nella lista dei dati.
     *
     * @return Il numero di elementi nella lista.
     */
    fun getCount(): Int {
        return data.size
    }

    /**
     * Restituisce l'elemento alla posizione specificata.
     *
     * @param position La posizione dell'elemento da restituire.
     * @return L'oggetto EmozioneStatistica alla posizione specificata.
     */
    fun getItem(position: Int): EmozioneStatistica {
        return data[position]
    }

    /**
     * Restituisce l'ID dell'elemento alla posizione specificata.
     * Usa la posizione come ID.
     *
     * @param position La posizione dell'elemento.
     * @return L'ID dell'elemento come Long.
     */
    fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * Restituisce la chiave (nome dell'emozione) alla posizione specificata.
     *
     * @param position La posizione della chiave da restituire.
     * @return La chiave alla posizione specificata.
     */
    fun getItemKey(position: Int): String {
        return keys[position]
    }

    /**
     * Restituisce il valore pesato alla posizione specificata.
     *
     * @param position La posizione del valore pesato da restituire.
     * @return Il valore pesato alla posizione specificata.
     */
    fun getItemValue(position: Int): Double {
        return values[position]
    }

    /**
     * Restituisce la chiave (nome dell'emozione) alla posizione specificata.
     * Questo metodo è duplicato di getItemKey().
     *
     * @param position La posizione della chiave da restituire.
     * @return La chiave alla posizione specificata.
     */
    fun getKey(position: Int): String {
        return keys[position]
    }

    /**
     * Restituisce il valore pesato alla posizione specificata.
     * Questo metodo è duplicato di getItemValue().
     *
     * @param position La posizione del valore pesato da restituire.
     * @return Il valore pesato alla posizione specificata.
     */
    fun getValue(position: Int): Double {
        return values[position]
    }

    /**
     * Restituisce la somma di tutti i valori pesati nella lista dei dati.
     *
     * @return La somma di tutti i valori pesati.
     */
    fun getSum(): Double {
        return values.sum()
    }

    /**
     * Calcola e restituisce la percentuale del valore pesato alla posizione specificata rispetto alla somma totale.
     *
     * @param position La posizione del valore pesato per il quale calcolare la percentuale.
     * @return La percentuale del valore pesato rispetto alla somma totale.
     */
    fun getPercentage(position: Int): Double {
        val sum = getSum()
        val value = values[position]
        return (value / sum) * 100
    }

    /**
     * Converte la lista di input in una mappa di chiavi e percentuali formattate come stringhe.
     *
     * @return Una mappa che associa ciascuna chiave alla sua percentuale formattata.
     */
    fun convertListToPercentageMap(): Map<String, String> {
        val totalSum = getSum() // Calcola la somma totale dei valori pesati
        return keys.zip(values).associate { (key, value) ->
            val percentage = (value / totalSum) * 100
            key to "%.2f%%".format(percentage) // Associa la chiave alla percentuale formattata
        }
    }
}
