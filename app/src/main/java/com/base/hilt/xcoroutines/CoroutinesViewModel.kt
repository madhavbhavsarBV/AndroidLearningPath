package com.base.hilt.xcoroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.base.hilt.base.ViewModelBase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class CoroutinesViewModel:ViewModelBase() {



    fun performBackgroundTask() {
        // Launch a coroutine within the ViewModel scope
        viewModelScope.launch {
            try {
                // Simulate a background task
                delay(2000)
                println("Background task completed")
            } catch (e: CancellationException) {
                // Handle cancellation if needed
                println("Coroutine was canceled")
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        // Cancel all coroutines when the ViewModel is cleared (e.g., when the associated UI component is destroyed)
        viewModelScope.cancel()
    }

}