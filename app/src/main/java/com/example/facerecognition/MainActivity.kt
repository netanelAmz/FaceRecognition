package com.example.facerecognition

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.facerecognition.databinding.ActivityMainBinding
import com.example.facerecognition.ui.wm.DataObject
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    val READ_STORAGE_PERMISSION_REQUEST_CODE: Int = 41
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment_content_main)
        if (!checkPermission()) {
            requestPermissionForReadExtertalStorage()
        }
    }

    private fun checkPermission(): Boolean {
        val result = this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    @Throws(Exception::class)
    fun requestPermissionForReadExtertalStorage() {
        try {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_STORAGE_PERMISSION_REQUEST_CODE
            )
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }


    override fun onResume() {
        //handle lifecycle
        DataObject.stateApp = "onResume"
        super.onResume()
    }

    override fun onPause() {
        //handle lifecycle
        DataObject.stateApp = "onPause"
        super.onPause()
    }


}