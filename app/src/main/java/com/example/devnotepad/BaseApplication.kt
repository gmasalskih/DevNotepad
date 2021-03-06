package com.example.devnotepad

import com.example.devnotepad.di.AppComponent
import com.example.devnotepad.di.DaggerAppComponent
import com.example.devnotepad.utils.InternetConnectionChecker
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BaseApplication : DaggerApplication() {

    companion object {
        lateinit var baseApplication: BaseApplication
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        baseApplication = this
        initializeAppComponent()
        initializeInternetConnectionChecker()
    }

    private fun initializeInternetConnectionChecker() {
        val internetConnectionChecker = InternetConnectionChecker(this)
    }

    private fun initializeAppComponent() {
        appComponent = DaggerAppComponent.builder().application(this).build()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}