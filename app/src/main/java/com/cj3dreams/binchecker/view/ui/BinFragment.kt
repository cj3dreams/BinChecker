package com.cj3dreams.binchecker.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cj3dreams.binchecker.R
import com.cj3dreams.binchecker.model.response.BinResponseModel
import com.cj3dreams.binchecker.vm.BinViewModel
import kotlinx.android.synthetic.main.fragment_bin.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BinFragment : Fragment() {
    private val binViewModel: BinViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_bin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainBinContinueBtn.setOnClickListener {
             binViewModel.checkBin(mainBinTextInputEditText.text.toString().toInt()){ bin ->
                println(bin.toString())
            }

        }

    }
}