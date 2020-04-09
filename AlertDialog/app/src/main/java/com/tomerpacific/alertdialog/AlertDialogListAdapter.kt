package com.tomerpacific.alertdialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class AlertDialogListAdapter(private val context: Context) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val data : ArrayList<String> = arrayListOf("One", "Two", "Three")

    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        val listItem = inflater.inflate(R.layout.list_item, p2, false)
        val textView = listItem.findViewById(R.id.text_view) as TextView
        textView.text = data[position]

        return listItem
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
      return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }
}