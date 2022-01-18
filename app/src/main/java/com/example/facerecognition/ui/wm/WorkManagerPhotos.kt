package com.example.facerecognition.ui.wm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleObserver
import androidx.work.*
import com.example.facerecognition.R
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import java.io.File
import java.io.IOException

class WorkManagerPhotos(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams), LifecycleObserver {

    val faceList: ArrayList<Bitmap> = ArrayList()
    var size: String = ""
    val detector = FaceDetection.getClient()
    val contex = context

    override fun doWork(): Result {
        //get the type of task to start
        val state = inputData.getInt("PhotoTask", 0)
        when (state) {
            0 -> {
                processAllPhoto()
            }
            1 -> {
                processFacePhoto()
            }
            2 -> {
                processRestPhoto()
            }
        }

        return Result.success()
    }

    private fun processAllPhoto() {

        val gpath = "/sdcard/Download/oosto"
        val fullpath = File(gpath)

        val list = imageReaderNew(fullpath)

        for (str: String in list) {
            val image: InputImage
            try {
                var fileName = "$gpath/$str"
                image = InputImage.fromFilePath(contex, (Uri.fromFile(File(fileName))))
                faceList.add(image.bitmapInternal)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        //check lifecycle if the app is on foreground then post the data else post notification
        if (DataObject.stateApp.equals("onPause")) {
            createNotification("Finish detected", "Number of all photos is: ${faceList.size}")
        } else {

            DataObject.listBitmaps.postValue(faceList)
        }

    }


    private fun processFacePhoto() {

        var gpath = "/sdcard/Download/oosto"
        var fullpath = File(gpath)

        var list = imageReaderNew(fullpath)

        for (i in 0..list.size) {
            val image: InputImage
            try {
                var fileName = gpath + "/" + list[i]
                image = InputImage.fromFilePath(contex, (Uri.fromFile(File(fileName))))

                println("face on")
                // detectorFace(image)
                val result = detector.process(image)
                    .addOnSuccessListener { faces ->
                        if (faces.isNotEmpty()) {
                            faceList.add(image.bitmapInternal)
                        }

                        if (i == list.size - 1) {
                            //check lifecycle if the app is on foreground then post the data else post notification
                            if (DataObject.stateApp.equals("onPause")) {
                                createNotification("Finish detected", "Number of faces in photos is: ${faceList.size} out of ${list.size}")
                            } else {

                                DataObject.listBitmaps.value = faceList
                            }


                        }

                    }.addOnFailureListener { e ->

                    }


            } catch (e: IOException) {
                e.printStackTrace()
            }


        }

    }

    private fun processRestPhoto() {

        val gpath = "/sdcard/Download/oosto"
        val fullpath = File(gpath)

        val list = imageReaderNew(fullpath)

        for (i in 0..list.size) {
            val image: InputImage
            try {
                val fileName = gpath + "/" + list[i]
                image = InputImage.fromFilePath(contex, (Uri.fromFile(File(fileName))))
               detector.process(image)
                    .addOnSuccessListener { faces ->
                        if (faces.isNotEmpty()) {

                        } else {
                            faceList.add(image.bitmapInternal)
                        }

                        if (i == list.size - 1) {
                            //check lifecycle if the app is on foreground then post the data else post notification
                            if (DataObject.stateApp.equals("onPause")) {
                                createNotification("Finish detected", "Number of no faces in photos is: ${faceList.size} out of ${list.size}")
                            } else {

                                DataObject.listBitmaps.value = faceList
                            }

                        }

                    }.addOnFailureListener { e ->

                    }


            } catch (e: IOException) {
                e.printStackTrace()
            }


        }

    }

    //Return the list of all the files in oosto directory that end with jpg or jpeg
    fun imageReaderNew(root: File): List<String> {
        val fileList: ArrayList<String> = ArrayList()
        val listAllFiles = root.listFiles()


        if (listAllFiles != null && listAllFiles.isNotEmpty()) {
            size = listAllFiles.size.toString()
            for (currentFile in listAllFiles) {
                if (currentFile.name.endsWith(".jpeg") || currentFile.name.endsWith(".jpg")) {

                    fileList.add(currentFile.name)
                }
            }

        }
        return fileList
    }

    private fun createNotification(title: String, description: String) {

        var notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel("101", "channel", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder = NotificationCompat.Builder(applicationContext, "101")
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.face)

        notificationManager.notify(1, notificationBuilder.build())

    }


}