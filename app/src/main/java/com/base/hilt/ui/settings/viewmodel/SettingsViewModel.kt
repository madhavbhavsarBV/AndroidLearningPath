package com.base.hilt.ui.settings.viewmodel

import com.base.hilt.base.ViewModelBase
import com.base.hilt.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(val repository: AuthRepository) : ViewModelBase() {
}