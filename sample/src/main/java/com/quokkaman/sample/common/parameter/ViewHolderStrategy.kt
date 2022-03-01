package com.quokkaman.sample.common.parameter

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.SeekBar
import com.quokkaman.sample.common.BooleanParameter
import com.quokkaman.sample.common.SeekBarParameter
import com.quokkaman.sample.common.StateParameter
import com.quokkaman.sample.common.StringParameter

abstract class ViewHolderStrategy(open val stateParameter: StateParameter<out Any>) {

    fun update(viewHolder: ParameterListAdapter.ViewHolder) {
        viewHolder.apply {
            this.layoutStringParameter.visibility = View.GONE
            this.layoutSwitchParameter.visibility = View.GONE
            textWatcher?.let {
                this.editText.removeTextChangedListener(it)
            }
            this.layoutSeekBarParameter.visibility = View.GONE
        }
        apply(viewHolder)
    }
    protected abstract fun apply(viewHolder: ParameterListAdapter.ViewHolder)

    companion object {
        fun create(stateParameter: StateParameter<out Any>): ViewHolderStrategy {
            return when(stateParameter) {
                is StringParameter -> {
                    EditTextViewHolderStrategy(stateParameter)
                }
                is BooleanParameter -> {
                    SwitchViewHolderStrategy(stateParameter)
                }
                is SeekBarParameter -> {
                    SeekBarViewHolderStrategy(stateParameter)
                }
                else -> {
                    throw RuntimeException("Invalid state Parameter")
                }
            }
        }
    }
}

private class EditTextViewHolderStrategy(override val stateParameter: StringParameter) : ViewHolderStrategy(
    stateParameter
) {

    override fun apply(viewHolder: ParameterListAdapter.ViewHolder) {
        viewHolder.apply {
            layoutStringParameter.visibility = View.VISIBLE
            textInputLayout.hint = stateParameter.name
            editText.setText(stateParameter.initValue)
            textWatcher?.let {
                editText.removeTextChangedListener(it)
            }
            textWatcher = object: TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    stateParameter.onValueChange(p0.toString())
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            }
            editText.addTextChangedListener(textWatcher)
        }
    }
}

private class SwitchViewHolderStrategy(override val stateParameter: BooleanParameter) : ViewHolderStrategy(
    stateParameter
) {

    override fun apply(viewHolder: ParameterListAdapter.ViewHolder) {
        viewHolder.apply {
            layoutSwitchParameter.visibility = View.VISIBLE
            switchTextView.text = stateParameter.name
            switchView.isChecked = stateParameter.initValue
            switchView.setOnCheckedChangeListener { _, b ->
                stateParameter.onValueChange(b)
            }
        }
    }
}

private class SeekBarViewHolderStrategy(override val stateParameter: SeekBarParameter) : ViewHolderStrategy(
    stateParameter
) {

    override fun apply(viewHolder: ParameterListAdapter.ViewHolder) {
        viewHolder.apply {
            layoutSeekBarParameter.visibility = View.VISIBLE
            "${stateParameter.name} value : ${stateParameter.initValue}".apply {
                seekBarTextView.text = this
            }
            seekBarView.apply {
                min = (stateParameter.minValue / stateParameter.stepSize).toInt()
                max = (stateParameter.maxValue / stateParameter.stepSize).toInt()
                progress = (stateParameter.initValue / stateParameter.stepSize).toInt()
            }
            seekBarView.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    if (p0 == null) {
                        return
                    }
                    "${stateParameter.name} value : ${stateParameter.stepSize * p0.progress}".apply {
                        seekBarTextView.text = this
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                    if (p0 == null) {
                        return
                    }
                    val newValue = stateParameter.stepSize * p0.progress
                    stateParameter.onValueChange(newValue)
                }
            })
        }
    }
}