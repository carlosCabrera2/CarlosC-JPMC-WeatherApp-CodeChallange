package com.example.carlosc_jpmc_weatherapp_codechallange.Tools

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.carlosc_jpmc_weatherapp_codechallange.ViewModel.AppViewModel

open class BaseFragment : Fragment() {

    protected val appViewModel:AppViewModel by lazy {
        ViewModelProvider(requireActivity())[AppViewModel::class.java]
    }
}