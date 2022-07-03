package com.example.crypocritic.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.crypocritic.R
import com.example.crypocritic.databinding.FragmentCryptoDetailBinding
import com.example.crypocritic.utils.nullCheck
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptoDetailFragment : Fragment() {

    private lateinit var fragmentCryptoDetailBinding: FragmentCryptoDetailBinding

    companion object {
        fun newInstance(bundle: Bundle = Bundle()): CryptoDetailFragment {
            val fragment = CryptoDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_crypto_detail, container, false)
        fragmentCryptoDetailBinding = FragmentCryptoDetailBinding.bind(view)
        fragmentCryptoDetailBinding.tvSymbolName.text = arguments?.getString("symbol")
        fragmentCryptoDetailBinding.progress.visibility = View.VISIBLE
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        init()
    }

    private fun init() {
        try {
            viewModel = ViewModelProviders.of(this)[DetailViewModel::class.java]
            arguments?.getString("symbol")?.let { viewModel.fetchCryptoDetail(it) }

            viewModel.getLiveDataObserver()?.observe(viewLifecycleOwner) { it ->
                if (it != null) {
                    fragmentCryptoDetailBinding.progress.visibility = View.GONE
                    if (!it.baseAsset.isNullOrEmpty()) {
                        fragmentCryptoDetailBinding.tvBaseAsset.visibility = View.VISIBLE
                        fragmentCryptoDetailBinding.tvBaseAsset.text =
                            "Base Asset : " + it.baseAsset.nullCheck()
                    } else
                        fragmentCryptoDetailBinding.tvBaseAsset.visibility = View.GONE

                    if (!it.quoteAsset.isNullOrEmpty()) {
                        fragmentCryptoDetailBinding.tvQuote.visibility = View.VISIBLE
                        fragmentCryptoDetailBinding.tvQuote.text =
                            "Quote Asset : " + it.quoteAsset.nullCheck()
                    } else
                        fragmentCryptoDetailBinding.tvQuote.visibility = View.GONE

                    if (!it.symbol.isNullOrEmpty()) {
                        fragmentCryptoDetailBinding.tvSymbol.visibility = View.VISIBLE
                        fragmentCryptoDetailBinding.tvSymbol.text =
                            "Symbol : " + it.symbol.nullCheck()
                    } else
                        fragmentCryptoDetailBinding.tvSymbol.visibility = View.GONE

                    if (!it.openPrice.isNullOrEmpty()) {
                        fragmentCryptoDetailBinding.tvOpenprice.visibility = View.VISIBLE
                        fragmentCryptoDetailBinding.tvOpenprice.text =
                            "Open Price : " + it.openPrice.nullCheck()
                    } else
                        fragmentCryptoDetailBinding.tvOpenprice.visibility = View.GONE

                    if (!it.lowPrice.isNullOrEmpty()) {
                        fragmentCryptoDetailBinding.tvLowprice.visibility = View.VISIBLE
                        fragmentCryptoDetailBinding.tvLowprice.text =
                            "Low Price : " + it.lowPrice.nullCheck()
                    } else
                        fragmentCryptoDetailBinding.tvLowprice.visibility = View.GONE

                    if (!it.highPrice.isNullOrEmpty()) {
                        fragmentCryptoDetailBinding.tvHighprice.visibility = View.VISIBLE
                        fragmentCryptoDetailBinding.tvHighprice.text =
                            "High Price : " + it.highPrice.nullCheck()
                    } else
                        fragmentCryptoDetailBinding.tvHighprice.visibility = View.GONE

                    if (!it.lastPrice.isNullOrEmpty()) {
                        fragmentCryptoDetailBinding.tvLastprice.visibility = View.VISIBLE
                        fragmentCryptoDetailBinding.tvLastprice.text =
                            "Last Price : " + it.lastPrice.nullCheck()
                    } else
                        fragmentCryptoDetailBinding.tvLastprice.visibility = View.GONE

                    if (!it.volume.isNullOrEmpty()) {
                        fragmentCryptoDetailBinding.tvVolume.visibility = View.VISIBLE
                        fragmentCryptoDetailBinding.tvVolume.text =
                            "Volume : " + it.volume.nullCheck()
                    } else
                        fragmentCryptoDetailBinding.tvVolume.visibility = View.GONE

                    if (!it.bidPrice.isNullOrEmpty()) {
                        fragmentCryptoDetailBinding.tvBidPrice.visibility = View.VISIBLE
                        fragmentCryptoDetailBinding.tvBidPrice.text =
                            "Bid Price : " + it.bidPrice.nullCheck()
                    } else
                        fragmentCryptoDetailBinding.tvBidPrice.visibility = View.GONE

                    if (!it.askPrice.isNullOrEmpty()) {
                        fragmentCryptoDetailBinding.tvAskPrice.visibility = View.VISIBLE
                        fragmentCryptoDetailBinding.tvAskPrice.text =
                            "Ask Price : " + it.askPrice.nullCheck()
                    } else
                        fragmentCryptoDetailBinding.tvAskPrice.visibility = View.GONE

                } else {
                    fragmentCryptoDetailBinding.progress.visibility = View.GONE
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
