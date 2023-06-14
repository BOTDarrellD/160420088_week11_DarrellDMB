package com.example.advweek4_160420088.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.advweek4_160420088.R
import com.example.advweek4_160420088.util.createNotificationChannel
import com.example.advweek4_160420088.util.showNotification
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    init {
        instance = this
    }

    companion object {
        var instance:MainActivity ?= null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab=findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener()
        {
            val observable=Observable.timer(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d("Messages", "five seconds")
                    showNotification("Dummy",
                        "A new notification created",
                        R.drawable.ic_baseline_error_24)
                }
        }

        val observable = Observable.just("a stream of data","hellow","world")
        val observer = object : Observer<String> {
            override fun onSubscribe(d: Disposable?) {
                Log.d("Messages", "begin subscribe")
            }

            override fun onNext(t: String?) {
                Log.d("Messages", "data: $t")
            }

            override fun onError(e: Throwable?) {
                Log.e("Messages", "error: ${e!!.message.toString()}")
            }

            override fun onComplete() {
                Log.d("Messages", "complete")
            }
        }
        observable.apply { //di slide sama saja//
            subscribeOn(Schedulers.io())
            observeOn(AndroidSchedulers.mainThread())
            subscribe(observer)
        }

        createNotificationChannel(this,
            NotificationManagerCompat.IMPORTANCE_DEFAULT, false,
            getString(R.string.app_name), "App notification channel.")

    }
}