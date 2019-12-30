package com.example.notescamera_daggermvvm

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.RequestManager
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class AuthActivity: DaggerAppCompatActivity() {

    @Inject
    lateinit var testInject : String

    @Inject
    lateinit var logo:Drawable

    @Inject
    lateinit var requestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        Log.d("AuthActivity","onCreate: $testInject")
        setLogo()
    }

    private fun setLogo(){
        requestManager.load(logo).into(findViewById(R.id.login_logo))
    }

}