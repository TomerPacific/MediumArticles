package com.tomerpacific.navigationcomponent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

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
            val myFragment : FragmentA = FragmentA()
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container, myFragment).addToBackStack(null).commit()
        }

        activityBBtn.setOnClickListener {button ->
            val myFragment : FragmentB = FragmentB()
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container, myFragment).addToBackStack(null).commit()
        }

        return root
    }
}