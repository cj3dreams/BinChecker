package com.cj3dreams.binchecker.view.ui

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cj3dreams.binchecker.MainActivity
import com.cj3dreams.binchecker.R
import com.cj3dreams.binchecker.vm.BinViewModel
import kotlinx.android.synthetic.main.fragment_bin.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BinFragment : Fragment() {
    private val binViewModel: BinViewModel by viewModel()
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_bin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainBinContinueBtn.setOnClickListener {
            if (mainBinTextInputEditText.text.isNullOrEmpty() && mainBinTextInputEditText.text!!.isBlank()) Toast.makeText(requireContext(), "Введите BIN код", Toast.LENGTH_SHORT).show()
            else if (mainBinTextInputEditText.text.toString().length <= 3) Toast.makeText(requireContext(), "Длина BIN кода должна быть более 3-х цифр", Toast.LENGTH_SHORT).show()
            else {
                progressDialog = ProgressDialog(requireContext())
                progressDialog.setTitle("Подождите")
                progressDialog.setCanceledOnTouchOutside(true)
                progressDialog.show()
                val bin = mainBinTextInputEditText.text.toString().toLong()
                binViewModel.checkBinAndSave(bin)
                binViewModel.checkBinResponse.observe(viewLifecycleOwner, {
                    if (it != null) {
                        val bundle = Bundle()
                        bundle.putSerializable("binResponse", it.copy(cardNumb = bin))
                        progressDialog.dismiss()
                        (activity as MainActivity)
                            .navController.navigate(R.id.action_binFragment_to_detailFragment, bundle).also {
                                binViewModel.checkBinResponse.value = null
                                binViewModel.checkBinResponse.removeObservers(this)
                            }
                    }
                    else {

                    }
                })
            }
        }
    }
}