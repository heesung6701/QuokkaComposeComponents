package com.quokkaman.sample.common

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quokkaman.sample.R
import com.quokkaman.sample.common.parameter.ParameterListAdapter

class ParameterDialogHelper {


    companion object {
        fun openDialog(context: Context, parameters: List<StateParameter<out Any>>, done: (() -> Unit)?) {
            val dialog = AlertDialog.Builder(context)
                .setTitle("modify parameter")
                .setView(LayoutInflater.from(context).inflate(R.layout.dialog_parameter, null, false).apply {
                    findViewById<RecyclerView>(R.id.recyclerview).apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = ParameterListAdapter(parameters)
                    }
                }
                ).setOnDismissListener {
                    done?.invoke()
                }.create()
            dialog.show()
        }
    }
}
