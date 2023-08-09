package com.yoti.android.cryptocurrencychallenge.ui.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.yoti.android.cryptocurrencychallenge.databinding.FragmentMarketBinding
import com.yoti.android.cryptocurrencychallenge.presentation.model.MarketUiItem
import com.yoti.android.cryptocurrencychallenge.presentation.viewmodel.MarketError
import com.yoti.android.cryptocurrencychallenge.presentation.viewmodel.MarketState
import com.yoti.android.cryptocurrencychallenge.presentation.viewmodel.MarketViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MarketFragment : Fragment() {

    private lateinit var binding: FragmentMarketBinding

    private val args: MarketFragmentArgs by navArgs()

    private val marketViewModel: MarketViewModel by lazy {
        ViewModelProvider(this)[MarketViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMarketBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        observe()
        marketViewModel.getMarket(args.assetId)
    }

    private fun initUI() {
        binding.errorLayout.retryButton.setOnClickListener {
            binding.errorLayout.root.isVisible = false
            marketViewModel.getMarket(args.assetId)
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            marketViewModel.marketState.collectLatest { state ->
                when (state) {
                    is MarketState.Loading -> {
                        handleLoading(true)
                    }
                    is MarketState.Success -> {

                        handleAssetsData(state.data)
                    }
                    is MarketState.Error -> {
                        handleLoading(false)
                        handleError(state.error)
                    }
                }
            }
        }
    }

    private fun handleLoading(showLoading: Boolean) {
        binding.apply {
            progressBarAssets.isVisible = showLoading
        }
    }

    private fun handleAssetsData(data: MarketUiItem) {
        handleLoading(false)
        binding.apply {
            marketDetailsLayout.isVisible = true
            errorLayout.root.isVisible = false

            textViewExchangeId.text = data.exchangeId
            textViewRank.text = data.rank
            textViewPrice.text = data.price
            textViewDate.text = data.formattedDate
        }
    }

    private fun handleError(error: MarketError) {
        handleLoading(false)
        binding.apply {
            marketDetailsLayout.isVisible = false
            errorLayout.root.isVisible = true
        }
    }
}