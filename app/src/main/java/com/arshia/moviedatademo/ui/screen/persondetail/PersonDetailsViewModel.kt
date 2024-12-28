package com.arshia.moviedatademo.ui.screen.persondetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.moviedatademo.data.model.persondetails.PersonCredits
import com.arshia.moviedatademo.data.model.persondetails.PersonDetails
import com.arshia.moviedatademo.data.repository.remote.DataRepository
import com.arshia.moviedatademo.utils.network.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailsViewModel @Inject constructor(private val repository: DataRepository) :
    ViewModel() {
    val personDetails: MutableState<DataResult<PersonDetails>?> = mutableStateOf(null)
    val personMovieCredits: MutableState<DataResult<PersonCredits>?> = mutableStateOf(null)

    fun fetchPersonDetails(personId: Int) {
        viewModelScope.launch {
            repository.personDetails(personId).onEach {
                personDetails.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun fetchPersonMovieCredits(personId: Int) {
        viewModelScope.launch {
            repository.personMovieCredits(personId).onEach {
                personMovieCredits.value = it
            }.launchIn(viewModelScope)
        }
    }
}