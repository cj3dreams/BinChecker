package com.cj3dreams.binchecker.view.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.underline
import androidx.core.view.MenuProvider
import androidx.core.view.get
import com.cj3dreams.binchecker.MainActivity
import com.cj3dreams.binchecker.R
import com.cj3dreams.binchecker.model.entity.BinHistoryEntity
import com.cj3dreams.binchecker.model.response.Bank
import com.cj3dreams.binchecker.model.response.BinResponseModel
import com.cj3dreams.binchecker.model.response.Country
import com.cj3dreams.binchecker.model.response.Number
import com.cj3dreams.binchecker.utils.AppConstants.u
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(), View.OnClickListener {
    private lateinit var info: BinResponseModel
    private val listOfColor = listOf(
        R.color.teal_200, R.color.purple_200, R.color.fire, R.color.gray, R.color.green, R.color.indigo,
        R.color.amber, R.color.blue_gray)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments?.getSerializable("binResponse") != null){
            info = requireArguments().getSerializable("binResponse") as BinResponseModel
         parseDataWithUI(info)
        }
        else if(arguments?.getSerializable("binHistory") != null){
            val entity = requireArguments().getSerializable("binHistory") as BinHistoryEntity
            info = BinResponseModel(Bank(entity.city, entity.bankName, entity.phone, entity.url),
            entity.brand, Country(entity.currency, entity.emoji, entity.latitude,
                    entity.longitude, entity.countryName),
                Number(entity.length, when (entity.luhn) {  1 -> true 0 -> false else -> null }),
                when (entity.prepaid) {  1 -> true 0 -> false else -> null }, entity.scheme, entity.type,
                entity.cardNumb)

            parseDataWithUI(info)
        }
        else {
            (activity as MainActivity)
                .navController.navigate(R.id.action_detailFragment_to_binFragment)
            Toast.makeText(requireContext(), "?????????????????????? ????????????", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.detailCardBankUrlTx -> if (info.bank?.url != null)
                startIntent(Intent.ACTION_VIEW,"http://" + info.bank?.url)
            R.id.detailCardBankPhoneTx -> if (info.bank?.phone != null)
                startIntent(Intent.ACTION_DIAL,"tel:" + info.bank?.phone)
            R.id.detailCardCountryTx -> if (info.country?.longitude != null)
                startIntent(Intent.ACTION_VIEW,"geo:" +
                        "${info.country?.latitude?.toFloat()},${info.country?.longitude?.toFloat()}")
        }
    }

    override fun onResume() {
        super.onResume()
        isNormalAppBar(false, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isNormalAppBar(true, false)
    }


    private fun parseDataWithUI(info: BinResponseModel){

        val cardNumb = info.cardNumb.toString()
        for (i in cardNumb.indices)
            detailCardNumberTx.append(if(i%4 == 0) " ${cardNumb[i]}" else "${cardNumb[i]}")

        detailCardView.setCardBackgroundColor(ContextCompat
            .getColor(requireContext(), listOfColor[(listOfColor.indices).random()]))

        detailCardNetworkTx.append(info.scheme?.uppercase() ?: u)
        detailCardInsideBankTx.append(info.bank?.name ?: u)

        detailCardIsPrepaidTx.append(if(info.prepaid == true) "Yes" else if(info.prepaid == false) "No" else u)
        detailCardTypeTx.append(info.type?.capitalize() ?: u)
        detailCardBrandTx.append(info.brand ?: u)

        if (info.country?.emoji != null ) detailCardCountryTx.append(info.country.emoji + " ")
        detailCardCountryTx.append(info.country?.name ?: u)
        if (info.country?.currency != null) detailCardCountryTx.append(", currency: " + info.country.currency)
        if (info.country?.latitude != null && info.country.longitude != null)
            detailCardCountryTx.append(buildSpannedString{underline{
                append("\n(latitude: ${info.country.latitude.toFloat()}, longitude: ${info.country.longitude.toFloat()})")}})
        detailCardCountryTx.setOnClickListener(this)

        detailCardNumLengthTx.append(if (info.number?.length != null) info.number.length.toString() else u)
        detailCardNumIsLuhnTx.append(if(info.number?.luhn == true) "Yes" else if(info.number?.luhn == false) "No" else u)

        detailCardBankNameCityTx.append(info.bank?.name ?: u)
        if (info.bank?.url != null) detailCardBankUrlTx.append(buildSpannedString{underline{append(info.bank.url)}})
        else detailCardBankUrlTx.append(u)
        detailCardBankUrlTx.setOnClickListener(this)
        if (info.bank?.phone != null) detailCardBankPhoneTx.append(buildSpannedString{underline{append(info.bank.phone)}})
        else detailCardBankPhoneTx.append(u)
        detailCardBankPhoneTx.setOnClickListener(this)
    }

    private fun startIntent(action: String, uriString: String) =
        startActivity(Intent(action, Uri.parse(uriString)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

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

                return false
            }
        })
    }
}