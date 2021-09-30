package com.apolis.rnxjavapractice.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.apolis.rnxjavapractice.R
import com.apolis.rnxjavapractice.databinding.ActivityRegisterBinding
import com.apolis.twowaydatabindingpractice.data.response.RegisterResponseData
import com.apolis.twowaydatabindingpractice.ui.register.RegisterViewModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        binding.viewModel = viewModel

        /*viewModel.registerResponse.observe(this) {
            Observable.just(it).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver())
        }*/
        Observable.just(viewModel.registerResponse).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(getObserver())



    }
    /*private fun setupObservers() {

        viewModel.error.observe(this) {
            Toast.makeText(baseContext, it, Toast.LENGTH_LONG).show()
        }
    }*/

    fun getObserver(): Observer<MutableLiveData<RegisterResponseData>> {
        return object: Observer<MutableLiveData<RegisterResponseData>> {
            override fun onSubscribe(d: Disposable) {
                Log.d("called","onSubscribe ${d.toString()}")
            }

            override fun onNext(it: MutableLiveData<RegisterResponseData>) {
                it.value?.error?.let {
                        hasError ->
                    if(hasError) {
                        it?.value?.message?.let {
                                msg ->
                            Toast.makeText(baseContext, msg, Toast.LENGTH_LONG).show()
                        }
                    }
                }
                Log.d("called","onNext ${it.value?.data.toString()}")
            }

            override fun onError(e: Throwable) {
                Log.d("called","onError")
            }

            override fun onComplete() {
                Log.d("called","onComplete")
            }

        }
    }
}