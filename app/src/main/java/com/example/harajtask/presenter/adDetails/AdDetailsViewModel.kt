package com.example.harajtask.presenter.adDetails

import android.util.Log
import androidx.lifecycle.*
import com.example.harajtask.data.dataDetails.AdDetailsRepository
import com.example.harajtask.data.entities.AdInfo
import com.example.harajtask.utils.DataResult
import com.example.harajtask.utils.EnhancedLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdDetailsViewModel @Inject constructor(private val adDetailsRepository: AdDetailsRepository) :
    ViewModel() {
    private val adDetailsStates = MutableLiveData<DataResult>(DataResult.Initial)
    val showError = EnhancedLiveEvent<String>()
    val showLoading = adDetailsStates.map { it is DataResult.Loading }
    val showContent = adDetailsStates.map { it is DataResult.Success<*> }
    val content = MediatorLiveData<AdInfo>().apply {
        addSource(adDetailsStates) {
            if (it is DataResult.Success<*>) {
                value = it.data as AdInfo
            }
        }
    }

    fun loadAdDetails(id: Int) {

        viewModelScope.launch {
            adDetailsRepository.loadAdDetails(id).collect {
                adDetailsStates.postValue(it)
                if (it is DataResult.Error) {
                    Log.e("getting Data error ", "${it.error.message}")
                    showError.postValue(it.error.message)
                }
            }
        }
    }

}