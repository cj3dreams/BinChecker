package com.cj3dreams.binchecker.view.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.buildSpannedString
import androidx.core.text.underline
import com.cj3dreams.binchecker.MainActivity
import com.cj3dreams.binchecker.R
import com.cj3dreams.binchecker.model.response.BinResponseModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(), View.OnClickListener {
    private val u = "Unknown"
    lateinit var info: BinResponseModel

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
        else if(arguments?.getSerializable("binFromLocal") != null){
            info = requireArguments().getSerializable("binFromLocal") as BinResponseModel
            parseDataWithUI(info)
        }
        else {
            (activity as MainActivity)
                .navController.navigate(R.id.action_detailFragment_to_binFragment)
            Toast.makeText(requireContext(), "Неизвестная ошибка", Toast.LENGTH_SHORT).show()
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

    private fun parseDataWithUI(info: BinResponseModel){

        val cardNumb = info.cardNumb.toString()
        for (i in cardNumb.indices)
            detailCardNumberTx.append(if(i%4 == 0) " ${cardNumb[i]}" else "${cardNumb[i]}")

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
}