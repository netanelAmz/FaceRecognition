package com.example.facerecognition.ui.wm

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData

object DataObject {
    //list of the bitmaps result
    val listBitmaps = MutableLiveData<List<Bitmap>>()
    var stateApp : String = ""
}