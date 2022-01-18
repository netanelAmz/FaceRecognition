package com.example.facerecognition.ui

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.facerecognition.ui.wm.DataObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val mainRepo: MainRepo
) : ViewModel() {


     fun loadFacesPhotos(state : Int) {
        mainRepo.loadPhotos(state)
    }

    var listPhotos: LiveData<List<Bitmap>> = Transformations
        .map(DataObject.listBitmaps) {
            it
        }


}