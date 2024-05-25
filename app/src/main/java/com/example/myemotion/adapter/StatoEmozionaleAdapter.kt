package com.example.myemotion.adapter
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView

class StatoEmozionaleAdapter(private val context: Context, private var data: Map<String, Int>) : BaseAdapter() {

    private val keys: List<String> = data.keys.toList()
    private val values: List<Int> = data.values.toList()

    fun setData(data: Map<String, Int>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[keys[position]]!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LinearLayout(context)

        val key = keys[position]
        val value = values[position]

        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.HORIZONTAL
        layout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val params = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        )

        val nomeEmozioneTextView = TextView(context)
        nomeEmozioneTextView.text = key
        nomeEmozioneTextView.layoutParams = params

        val countTextView = TextView(context)
        countTextView.text = value.toString()
        countTextView.layoutParams = params

        layout.addView(nomeEmozioneTextView)
        layout.addView(countTextView)

        return layout
    }

    fun getItemKey(position: Int): String {
        return keys[position]
    }

    fun getItemValue(position: Int): Int {
        return values[position]
    }

    fun getKey(position: Int): String {
        return keys[position]
    }

    fun getValue(position: Int): Int {
        return values[position]
    }

    fun getSum(): Int {
        return values.sum()
    }

    fun getPercentage(position: Int): Double {
        val sum = getSum().toDouble()
        val value = values[position].toDouble()
        return (value / sum) * 100
    }
}
