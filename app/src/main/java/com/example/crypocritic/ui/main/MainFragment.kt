package com.example.crypocritic.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.crypocritic.R
import com.example.crypocritic.data.CryptoResponse
import com.example.crypocritic.databinding.FragmentMainBinding
import com.example.crypocritic.ui.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var fragMainBinding: FragmentMainBinding
    private var cryptoResponse: CryptoResponse? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)
        fragMainBinding = FragmentMainBinding.bind(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()

        val viewModel = ViewModelProvider(
            this
        )[MainViewModel::class.java]
        viewModel.fetchCryptoResponseList()
        viewModel.getLiveDataObserver()?.observe(viewLifecycleOwner) { it ->
            if (it != null) {
                cryptoResponse = it
                toCallApi(it)
            }
        }

        fragMainBinding.pullToRefresh.setOnRefreshListener {
            if (!cryptoResponse.isNullOrEmpty())
                toCallApi(cryptoResponse!!)

            fragMainBinding.pullToRefresh.isRefreshing = false
        }
    }

    private var quoteAssetArrayList: ArrayList<String>? = null
    private fun toCallApi(cryptoResponse: CryptoResponse) {
        try {
            if (quoteAssetArrayList == null)
                quoteAssetArrayList = arrayListOf()

            for (i in cryptoResponse.indices) {
                if (quoteAssetArrayList.isNullOrEmpty())
                    quoteAssetArrayList?.add(cryptoResponse[i].quoteAsset.toString())
                else {
                    if (!quoteAssetArrayList!!.contains(cryptoResponse[i].quoteAsset)) {
                        quoteAssetArrayList?.add(cryptoResponse[i].quoteAsset.toString())
                    }
                }
            }

            val viewPagerAdapter = ViewPagerAdapter(requireActivity())
            for (i in quoteAssetArrayList!!.indices) {
                viewPagerAdapter.add(
                    MainTabFragment.newInstance(
                        quoteAssetArrayList!![0],
                        cryptoResponse,
                        quoteAssetArrayList
                    ), quoteAssetArrayList?.get(i)
                )
            }
            fragMainBinding.vpCryptoList.adapter = viewPagerAdapter
            TabLayoutMediator(
                fragMainBinding.tlCurrency, fragMainBinding.vpCryptoList
            ) { tab, position ->
                tab.text = viewPagerAdapter.getTabTitle(position)
            }.attach()

            for (i in 0 until fragMainBinding.tlCurrency.tabCount) {
                val tab =
                    (fragMainBinding.tlCurrency.getChildAt(0) as ViewGroup).getChildAt(
                        i
                    )
                tab.requestLayout()
            }

            fragMainBinding.vpCryptoList.isUserInputEnabled = false

            fragMainBinding.tlCurrency.addOnTabSelectedListener(object :
                TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    MainTabFragment.newInstance(
                        tab?.text.toString(),
                        cryptoResponse,
                        quoteAssetArrayList
                    )
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}