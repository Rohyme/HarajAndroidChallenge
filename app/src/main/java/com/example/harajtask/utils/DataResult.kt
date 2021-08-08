package com.example.harajtask.utils

sealed interface DataResult {
    data class Success<T>(val data: T): DataResult
    data class Error(val error: Throwable): DataResult
    object Loading : DataResult
    object Empty: DataResult
    object Initial: DataResult
}