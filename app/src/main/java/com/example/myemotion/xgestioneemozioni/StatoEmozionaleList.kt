package com.example.myemotion.xgestioneemozioni

class StatoEmozionaleList(data: List<EmozioneStatistica>) {

    private var data: List<EmozioneStatistica> = data
    private var keys: List<String> = data.map { it.nome } // Lista delle chiavi estratte dalla lista dei dati
    private var values: List<Double> = data.map { it.mediaIntensita * it.conteggio } // Lista dei valori pesati

    // Restituisce il numero di elementi nella lista dei dati
    fun getCount(): Int {
        return data.size
    }

    // Restituisce l'elemento alla posizione specificata
    fun getItem(position: Int): EmozioneStatistica {
        return data[position]
    }

    // Restituisce l'ID dell'elemento alla posizione specificata (usando la posizione come ID)
    fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // Restituisce la chiave alla posizione specificata
    fun getItemKey(position: Int): String {
        return keys[position]
    }

    // Restituisce il valore pesato alla posizione specificata
    fun getItemValue(position: Int): Double {
        return values[position]
    }

    // Restituisce la chiave alla posizione specificata (duplicato di getItemKey)
    fun getKey(position: Int): String {
        return keys[position]
    }

    // Restituisce il valore pesato alla posizione specificata (duplicato di getItemValue)
    fun getValue(position: Int): Double {
        return values[position]
    }

    // Restituisce la somma di tutti i valori pesati nella lista dei dati
    fun getSum(): Double {
        return values.sum()
    }

    // Calcola e restituisce la percentuale del valore pesato alla posizione specificata rispetto alla somma totale
    fun getPercentage(position: Int): Double {
        val sum = getSum()
        val value = values[position]
        return (value / sum) * 100
    }

    // Converte la lista di input in una mappa di chiavi e percentuali formattate come stringhe
    fun convertListToPercentageMap(): Map<String, String> {
        val totalSum = getSum() // Calcola la somma totale dei valori pesati
        return keys.zip(values).associate { (key, value) ->
            val percentage = (value / totalSum) * 100
            key to "%.2f%%".format(percentage) // Associa la chiave alla percentuale formattata
        }
    }


}

