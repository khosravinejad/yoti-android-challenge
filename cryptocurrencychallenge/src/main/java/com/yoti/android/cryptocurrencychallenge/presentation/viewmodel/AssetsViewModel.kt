package com.yoti.android.cryptocurrencychallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoti.android.cryptocurrencychallenge.domain.repository.AssetRepository
import com.yoti.android.cryptocurrencychallenge.presentation.mapper.AssetDomainToPresentationMapper
import com.yoti.android.cryptocurrencychallenge.presentation.model.AssetUiItem
import com.yoti.android.cryptocurrencychallenge.utils.CoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
open class AssetsViewModel @Inject constructor(
    private val contextProvider: CoroutineContextProvider,
    private val assetRepository: AssetRepository,
    private val domainToPresentationMapper: AssetDomainToPresentationMapper
) : ViewModel() {

    val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        // TODO handle coroutine error
    }

    private val _assetsState by lazy {
        MutableStateFlow<AssetsState>(AssetsState.Loading)
    }
    val assetsState: StateFlow<AssetsState> = _assetsState

    private val _refreshingState by lazy {
        MutableStateFlow(false)
    }
    val refreshingState: StateFlow<Boolean> = _refreshingState

    init {
        loadAssets()
    }

    fun loadAssets() {
        viewModelScope.launch(contextProvider.io + coroutineExceptionHandler) {
            try {
                assetRepository.assetsFlow.collect { result ->
//                    _refreshingState.value = false
                    result.onSuccess {
                        _assetsState.value = AssetsState.Success(
                            domainToPresentationMapper.mapTo(it)
                        )
                    }.onFailure {
                        _assetsState.value = AssetsState.Error(handleError(it))
                    }
                    _refreshingState.value = false
                }
            } catch (e: Exception) {
                _refreshingState.value = false
                _assetsState.value = AssetsState.Error(handleError(e))
            }
        }
    }

    fun refreshAssets() {
        viewModelScope.launch(contextProvider.io + coroutineExceptionHandler) {
            // if there is no data, show the main loading, else keep the data and show the refreshing view
            if (_assetsState.value !is AssetsState.Success) {
                _assetsState.value = AssetsState.Loading
                _refreshingState.value = false
            } else {
                _refreshingState.value = true
            }
            assetRepository.refreshAssets().onFailure {
                _refreshingState.value = false
                _assetsState.value = AssetsState.Error(handleError(it))
            }

        }
    }

    private fun handleError(throwable: Throwable): AssetsError {
        return when (throwable) {
            is IOException -> AssetsError.NetworkError
            else -> AssetsError.UnknownError(throwable.message)
        }
    }
}

sealed class AssetsState {
    object Loading : AssetsState()
    data class Success(val data: List<AssetUiItem>) : AssetsState()
    data class Error(val error: AssetsError) : AssetsState()
}

sealed class AssetsError {
    object NetworkError : AssetsError()
    data class UnknownError(val message: String?) : AssetsError()
}