package com.yoti.android.cryptocurrencychallenge.ui.assets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yoti.android.cryptocurrencychallenge.R
import com.yoti.android.cryptocurrencychallenge.databinding.FragmentAssetsBinding
import com.yoti.android.cryptocurrencychallenge.presentation.model.AssetUiItem
import com.yoti.android.cryptocurrencychallenge.presentation.viewmodel.AssetsError
import com.yoti.android.cryptocurrencychallenge.presentation.viewmodel.AssetsState
import com.yoti.android.cryptocurrencychallenge.presentation.viewmodel.AssetsViewModel
import com.yoti.android.cryptocurrencychallenge.ui.market.MarketFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AssetsFragment : Fragment() {

    private lateinit var binding: FragmentAssetsBinding

    private val assetsViewModel: AssetsViewModel by lazy {
        ViewModelProvider(this)[AssetsViewModel::class.java]
    }

    @Inject
    lateinit var assetsAdapter: AssetsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAssetsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        observe()
    }

    private fun initUI() {
        binding.recyclerViewAssets.adapter = assetsAdapter
        assetsAdapter.setOnItemClickListener {
            navigateToMarketScreen(it.id)
        }

        binding.refreshLayout.setOnRefreshListener {
            assetsViewModel.refreshAssets()
        }

        binding.errorLayout.retryButton.setOnClickListener {
            assetsViewModel.refreshAssets()
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            assetsViewModel.assetsState.collectLatest { state ->
                when (state) {
                    is AssetsState.Loading -> {
                        handleLoading(true)
                    }
                    is AssetsState.Success -> {
                        handleAssetsData(state.data)
                    }
                    is AssetsState.Error -> {
                        if (assetsAdapter.itemCount == 0) {
                            handleFullScreenError(state.error)
                        } else {
                            showErrorInToast(state.error)
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            assetsViewModel.refreshingState.collectLatest {
                binding.refreshLayout.isRefreshing = it
            }
        }
    }

    private fun navigateToMarketScreen(id: String) {
        val direction = MarketFragmentDirections.startDetailsFragment(id)
        findNavController().navigate(direction)
    }

    private fun handleLoading(showLoading: Boolean) {
        binding.apply {
            progressBarAssets.isVisible = showLoading
            errorLayout.root.isVisible = false
        }
    }

    private fun handleAssetsData(data: List<AssetUiItem>) {
        handleLoading(false)
        binding.apply {
            errorLayout.root.isVisible = false
            refreshLayout.isVisible = true
        }
        assetsAdapter.updateList(data)
    }

    private fun handleFullScreenError(error: AssetsError) {
        handleLoading(false)
        binding.apply {
            errorLayout.root.isVisible = true
            refreshLayout.isVisible = false
            when (error) {
                is AssetsError.NetworkError -> {
                    errorLayout.errorMessageTextView.text =
                        getString(R.string.connection_error_message)
                }
                is AssetsError.UnknownError -> {
                    errorLayout.errorMessageTextView.text =
                        getString(R.string.connection_error_message)
                }
            }
        }
    }

    private fun showErrorInToast(error: AssetsError) {
        // TODO Complete this implementation
        handleLoading(false)
        binding.apply {
            refreshLayout.isVisible = true
            when (error) {
                is AssetsError.NetworkError -> {
                    Toast.makeText(context, getString(R.string.connection_error_message), Toast.LENGTH_SHORT).show()
                }
                is AssetsError.UnknownError -> {
                    Toast.makeText(context, getString(R.string.connection_error_message), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}