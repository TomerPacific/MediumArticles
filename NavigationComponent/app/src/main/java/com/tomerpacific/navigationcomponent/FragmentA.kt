package com.tomerpacific.navigationcomponent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

class FragmentA: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args : FragmentAArgs by navArgs()
        val root = inflater.inflate(R.layout.fragment_a, container, false)
        val textView : TextView = root.findViewById(R.id.textView)
        textView.text = args.message
        return root
    }
}