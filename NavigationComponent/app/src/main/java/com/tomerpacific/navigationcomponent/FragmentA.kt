package com.tomerpacific.navigationcomponent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class FragmentA: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = arguments
        val root = inflater.inflate(R.layout.fragment_a, container, false)
        val textView : TextView = root.findViewById(R.id.textView)
        textView.text = bundle?.getString("message")
        return root
    }
}