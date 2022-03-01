package com.quokkaman.sample.common.parameter

import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.quokkaman.sample.R
import com.quokkaman.sample.common.StateParameter

class ParameterListAdapter(private val parameters: List<StateParameter<out Any>>) : RecyclerView.Adapter<ParameterListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_parameter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        ViewHolderStrategy.create(parameters[position]).update(holder)
    }

    override fun getItemCount(): Int = parameters.size

    open inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layoutStringParameter: ViewGroup
        val textInputLayout: TextInputLayout
        val editText: TextInputEditText

        val layoutSwitchParameter: ViewGroup
        val switchView: SwitchCompat
        val switchTextView: TextView

        val layoutSeekBarParameter: ViewGroup
        val seekBarTextView: TextView
        val seekBarView: SeekBar

        init {
            layoutStringParameter = itemView.findViewById<ViewGroup?>(R.id.layout_text_parameter).apply {
                textInputLayout = findViewById(R.id.text_input_layout)
                editText = findViewById(R.id.edit_query)
            }

            layoutSwitchParameter = itemView.findViewById<ViewGroup?>(R.id.layout_switch_parameter).apply {
                switchView = findViewById(R.id.switch_view)
                switchTextView = findViewById(R.id.switch_text_view)
            }

            layoutSeekBarParameter = itemView.findViewById<ViewGroup>(R.id.layout_seekbar_parameter).apply {
                seekBarTextView = findViewById(R.id.seekbar_text_view)
                seekBarView = findViewById(R.id.seekbar)
            }
        }

        var textWatcher : TextWatcher? = null
    }
}