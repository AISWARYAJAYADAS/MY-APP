package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.myapplication.graphs.AuthScreen
import com.example.myapplication.graphs.Graph
import com.example.myapplication.pref.SharedPref
import com.example.myapplication.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
     val sharedPref: SharedPref,
    private val repository: ProfileRepository,
    ) : ViewModel() {

//
//    private val _forceLogout = MutableSharedFlow<Int>()
//    val forceLogout = _forceLogout.asSharedFlow()
//
//    fun forceLogoutUser(flowDetails: Int) {
//        viewModelScope.launch {
//            _forceLogout.emit(flowDetails)
//        }
//    }
//
//    fun clearSharePrefValues() {
//        sharedPref.clear()
//    }
//
//    fun addDevice() {
//        if (sharedPref.getIsFCMTokenAdded()) {
//            return
//        }
//    }


  companion object{
      // Declare MutableLiveData
      val forceLogout: MutableLiveData<Int> = MutableLiveData()

      // Function to force logout user
      fun forceLogoutUser(flowDetails: Int) {
          forceLogout.postValue(flowDetails)
      }
  }

    fun addDevice(){
        if (sharedPref.getIsFCMTokenAdded()){
            return
        }
    }










}