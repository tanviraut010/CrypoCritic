package com.example.crypocritic.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crypocritic.R
import com.example.crypocritic.data.CryptoResponse
import com.example.crypocritic.data.CryptoResponseItem
import com.example.crypocritic.databinding.FragmentMainTabBinding
import com.example.crypocritic.ui.interfaces.ItemClickInterface
import com.example.crypocritic.ui.adapter.MainAdapter
import com.example.crypocritic.ui.detail.CryptoDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainTabFragment : Fragment(), ItemClickInterface {

    private var mainAdapter: MainAdapter? = null
    private var toGet: Boolean = false
    private var toPassQuote: String = ""
    private lateinit var fragmentMainTabBinding: FragmentMainTabBinding

    companion object {
        var toSelect = ""
        var mCryptoResponse: CryptoResponse? = null
        var mQuoteAssetArrayList: ArrayList<String>? = null
        fun newInstance(
            bundle: String,
            cryptoResponse: CryptoResponse,
            quoteAssetArrayList: ArrayList<String>?
        ): MainTabFragment {
            toSelect = bundle
            mCryptoResponse = cryptoResponse
            mQuoteAssetArrayList = quoteAssetArrayList
            val fragment = MainTabFragment()
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_main_tab, container, false)
        fragmentMainTabBinding = FragmentMainTabBinding.bind(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        try {
            if (!toSelect.isNullOrEmpty()) {
                for (i in mQuoteAssetArrayList!!.indices) {
                    if (toSelect.equals(mQuoteAssetArrayList!![i], true)) {
                        toGet = true
                        toPassQuote = mQuoteAssetArrayList!![i]
                        break
                    }
                }

                if (toGet) {
                    when {
                        toSelect.equals(toPassQuote, true) -> {
                            val sortCryptoResponse: List<CryptoResponseItem> =
                                mCryptoResponse!!.filter { s -> s.quoteAsset == toSelect }
                            mainAdapter = MainAdapter(sortCryptoResponse, this)
                            fragmentMainTabBinding.rvMainList.layoutManager =
                                LinearLayoutManager(context)
                            fragmentMainTabBinding.rvMainList.adapter = mainAdapter
                            fragmentMainTabBinding.rvMainList.visibility = View.VISIBLE
                        }
                        else -> {
                            val sortCryptoResponse: List<CryptoResponseItem> =
                                mCryptoResponse!!.filter { s -> s.quoteAsset == "usdt" }
                            mainAdapter = MainAdapter(sortCryptoResponse, this)
                            fragmentMainTabBinding.rvMainList.layoutManager =
                                LinearLayoutManager(context)
                            fragmentMainTabBinding.rvMainList.adapter = mainAdapter
                            fragmentMainTabBinding.rvMainList.visibility = View.VISIBLE
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onSymbolClick(bundle: Bundle) {
        try {
            activity?.supportFragmentManager?.beginTransaction()?.addToBackStack("Main Fragment")
                ?.apply {
                    replace(R.id.flFragment, CryptoDetailFragment.newInstance(bundle))
                    commit()
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}