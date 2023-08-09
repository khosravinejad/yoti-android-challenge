package com.yoti.android.cryptocurrencychallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoti.android.cryptocurrencychallenge.domain.repository.MarketRepository
import com.yoti.android.cryptocurrencychallenge.presentation.mapper.MarketDomainToPresentationMapper
import com.yoti.android.cryptocurrencychallenge.presentation.model.MarketUiItem
import com.yoti.android.cryptocurrencychallenge.utils.CoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val contextProvider: CoroutineContextProvider,
    private val marketRepository: MarketRepository,
    private val domainToPresentationMapper: MarketDomainToPresentationMapper
) : ViewModel() {

    val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        // TODO handle coroutine error
    }

    private val _marketState by lazy {
        MutableStateFlow<MarketState>(MarketState.Loading)
    }
    val marketState: StateFlow<MarketState> = _marketState

    fun getMarket(id: String) {
        viewModelScope.launch(contextProvider.io + coroutineExceptionHandler) {
            _marketState.value = MarketState.Loading
            marketRepository.getMarketById(id)
                .onSuccess {
                    _marketState.value = MarketState.Success(
                        domainToPresentationMapper.mapTo(it)
                    )
                }
                .onFailure { _marketState.value = MarketState.Error(handleError(it)) }

        }
    }

    private fun handleError(throwable: Throwable): MarketError {
        return when (throwable) {
            is IOException -> MarketError.NetworkError
            else -> MarketError.UnknownError(throwable.message)
        }
    }
}

sealed class MarketState {
    object Loading : MarketState()
    data class Success(val data: MarketUiItem) : MarketState()
    data class Error(val error: MarketError) : MarketState()
}

sealed class MarketError {
    object NetworkError : MarketError()
    data class UnknownError(val message: String?) : MarketError()
}