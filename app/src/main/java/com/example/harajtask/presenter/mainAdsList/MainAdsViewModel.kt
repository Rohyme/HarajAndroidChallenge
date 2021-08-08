package com.example.harajtask.presenter.mainAdsList

import android.util.Log
import androidx.lifecycle.*
import com.example.harajtask.data.entities.AdInfo
import com.example.harajtask.data.mainScreen.DataListRepository
import com.example.harajtask.utils.DataResult
import com.example.harajtask.utils.EnhancedLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainAdsViewModel @Inject constructor(private val repository: DataListRepository) :
    ViewModel() {
    val scrollToTop =  EnhancedLiveEvent<Unit>()
    private val _dataState = MutableLiveData<DataResult>()
    val dataState: LiveData<DataResult> = _dataState
    val showLoading = dataState.map {
        it is DataResult.Loading
    }
    val showContent = dataState.map { it is DataResult.Success<*> }
    private val textSearch = MutableLiveData<String>()
    val data = MediatorLiveData<List<AdInfo>>().apply {
      addSource(dataState){
          if (it is DataResult.Success<*>){
              value = (it.data as List<AdInfo>).filter {
                  if (textSearch.value.isNullOrBlank()) return@filter true
                  else {
                      return@filter it.title.contains(textSearch.value!!)
                  }
              }
          }
      }
        addSource(textSearch){searchTxt ->
            if (dataState.value is DataResult.Success<*>){
               value = ((dataState.value as DataResult.Success<*>).data as? List<AdInfo>).takeIf { it.isNullOrEmpty().not() }?.filter { it.title.contains(searchTxt) }
            }
        }
    }
    val showEmptyContent = data.map { it.isNullOrEmpty()  && (dataState.value !is DataResult.Loading && dataState.value !is DataResult.Error)}

    val showError = EnhancedLiveEvent<String>()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
           repository.getDataList().stateIn(viewModelScope).collect {
               _dataState.postValue(it)
               if (it is DataResult.Error){
                   Log.e("getting Data error ","${it.error.message}")
                   showError.postValue(it.error.message)
               }
           }
        }
    }

    fun retryFetchingData(){
        loadData()
    }

    fun onSearchTextChange(searchTxt: String?) {
        textSearch.value = searchTxt ?: ""
        if (searchTxt.isNullOrBlank()){
            scrollToTop.call()

        }

    }
}