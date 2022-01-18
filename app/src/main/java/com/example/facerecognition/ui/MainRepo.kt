package com.example.facerecognition.ui

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.facerecognition.ui.wm.WorkManagerPhotos
import javax.inject.Inject
import androidx.work.Data


class MainRepo @Inject constructor(val workManager: WorkManager){


    fun loadPhotos(state : Int) {

            try {
                val data = Data.Builder()
                data.putInt("PhotoTask",state )
                var request = OneTimeWorkRequestBuilder<WorkManagerPhotos>()
                    .setInputData(data.build())
                    .build()
               workManager.enqueue(request)

            } catch (e: Exception) {

            }


    }



}