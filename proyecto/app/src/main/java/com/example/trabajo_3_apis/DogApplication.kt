package com.example.trabajo_3_apis

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory

class DogApplication : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader =
        ImageLoader.Builder(this)
            .respectCacheHeaders(false)
            .build()
}
