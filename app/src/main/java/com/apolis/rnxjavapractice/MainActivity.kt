package com.apolis.rnxjavapractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.apolis.twowaydatabindingpractice.data.request.RegisterRequestData
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.operators.observable.ObservableError
import io.reactivex.schedulers.Schedulers

import java.util.*

//observable is something will do some work to emit data which is the liva data
// //observer to receive the data are the observable
//scheduler provide threads to the observable for execution to carry on the heavy operation in the background   provide the background thread (I/O thread) also provide main thread to the observer

class MainActivity : AppCompatActivity() {
    lateinit var rd:RegisterRequestData
    lateinit var rda:RegisterRequestData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rd= RegisterRequestData("984568@hafakl.com","b","c","111")
        rda= RegisterRequestData("222222984568@hafakl.com","b","c","111")
        getObservable().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(getObserver())

    }

    fun getObservable() :Observable<RegisterRequestData>{
        return Observable.just(rd,rda,rda)
    }
    fun getObserver():Observer<RegisterRequestData>{
        return object:Observer<RegisterRequestData>{
            override fun onSubscribe(d: Disposable) {
                Log.d("called","onSubscribe ${d.toString()}")
            }

            override fun onNext(t: RegisterRequestData) {

                Log.d("called","onNext ${t.email}")
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