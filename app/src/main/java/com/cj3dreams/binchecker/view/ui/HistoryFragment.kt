package com.cj3dreams.binchecker.view.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.cj3dreams.binchecker.MainActivity
import com.cj3dreams.binchecker.R
import com.cj3dreams.binchecker.model.entity.BinHistoryEntity
import com.cj3dreams.binchecker.view.adapter.HistoryAdapter
import com.cj3dreams.binchecker.vm.HistoryViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment(), View.OnClickListener {
    private val historyViewModel: HistoryViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyRcView.layoutManager = LinearLayoutManager(requireContext())
        historyViewModel.getAllBinHistoryFromLocal()
        historyViewModel.binHistoryLiveData.observe(viewLifecycleOwner, {
            historyRcView.adapter = HistoryAdapter(it, this)
        })
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.itemBinHistoryCardView -> {
                val bundle = Bundle()
                bundle.putSerializable("binHistory", (v.tag as BinHistoryEntity))
                (activity as MainActivity)
                    .navController.navigate(R.id.action_historyFragment_to_detailFragment, bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isNormalAppBar(true, false)
    }

    override fun onResume() {
        super.onResume()
        isNormalAppBar(false, true)
    }

    private fun isNormalAppBar(isNormal: Boolean, isVisibleCleaner: Boolean){
        val actionBar: androidx.appcompat.app.ActionBar? = (activity as MainActivity).supportActionBar
        actionBar?.setHomeButtonEnabled(!isNormal)
        actionBar?.setDisplayHomeAsUpEnabled(!isNormal)
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu[0].isVisible = isNormal
                menu[1].isVisible = isVisibleCleaner
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                if (menuItem.itemId == R.id.nav_remove_history){
                    historyViewModel.cleanHistoryFromLocal()
                }
                return true
            }
        })
    }
}