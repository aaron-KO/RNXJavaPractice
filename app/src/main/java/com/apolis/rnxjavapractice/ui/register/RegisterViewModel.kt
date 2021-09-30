package com.apolis.twowaydatabindingpractice.ui.register

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apolis.mvvm_demo1.api.ApiClient
import com.apolis.twowaydatabindingpractice.data.request.RegisterRequestData
import com.apolis.twowaydatabindingpractice.data.response.RegisterResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel:ViewModel() {
    val emailId = ObservableField<String>("")
    val password = ObservableField<String>("")
    val repeatedPassword = ObservableField<String>("")
    val name = ObservableField<String>("")
    val mobile = ObservableField<String>("")
    val emailIdError = ObservableField<String>()
    val passwordError = ObservableField<String>()
    val nameError = ObservableField<String>()
    val mobileError = ObservableField<String>()

    val registerResponse = MutableLiveData<RegisterResponseData>()
    val error = MutableLiveData<String>()
    val processing = ObservableField<Boolean>()

    fun register() {
        var hasError = false
        emailId.get()?.let {
            if(it.isEmpty()) {
                emailIdError.set("Please enter email id")
                hasError = true
            } else {
                emailIdError.set(null)
            }
        }
        name.get()?.let {
            if(it.isEmpty()) {
                nameError.set("Please enter name")
                hasError = true
            } else {
                nameError.set(null)
            }
        }
        mobile.get()?.let {
            if(it.isEmpty()||it.length<10) {
                mobileError.set("Please enter phone number")
                hasError = true
            } else {
                mobileError.set(null)
            }
        }
        password.get()?.let {
            if(it.isEmpty()) {
                passwordError.set("Please enter password")
                hasError = true
            } else if(it!=repeatedPassword.get()){
                passwordError.set("Please confirm you enter the same password")
                hasError = true
            }
            else
             {
                passwordError.set(null)
            }
        }

        if(hasError) {
            return
        }

        val registerRequest = RegisterRequestData(emailId.get()?:"NA", password.get()?:"NA",name.get()?:"NA",mobile.get()?:"NA")

        val call: Call<RegisterResponseData> = ApiClient.apiService.register(registerRequest)

        call.enqueue(object : Callback<RegisterResponseData> {
            override fun onResponse(
                call: Call<RegisterResponseData>,
                response: Response<RegisterResponseData>
            ) {
                processing.set(false)
                if(!response.isSuccessful) {
                    error.postValue("Failed to authenticate. Error code: ${response.code()}")
                    return
                }
                registerResponse.postValue(response.body())
            }
            override fun onFailure(call: Call<RegisterResponseData>, t: Throwable) {
                processing.set(false)
                error.postValue("Failed to authenticate. Error is : ${t.toString()}")
            }
        })

        processing.set(true)
    }
}