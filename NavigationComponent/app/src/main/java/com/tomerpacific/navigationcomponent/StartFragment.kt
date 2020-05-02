package com.tomerpacific.navigationcomponent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class StartFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.fragment_start, container, false)

        val activityABtn : Button = root.findViewById(R.id.fragment_a_btn)
        val activityBBtn : Button = root.findViewById(R.id.fragment_b_btn)

        activityABtn.setOnClickListener {button ->
            val action = StartFragmentDirections.actionStartFragmentToFragmentA()
            button.findNavController().navigate(action)
        }

        activityBBtn.setOnClickListener {button ->
            val action = StartFragmentDirections.actionStartFragmentToFragmentB()
            button.findNavController().navigate(action)
        }

        return root
    }
}