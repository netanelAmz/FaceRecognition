package com.example.facerecognition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.facerecognition.databinding.ActivityMainBinding
import com.example.facerecognition.ui.wm.DataObject
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
         navController = findNavController(R.id.nav_host_fragment_content_main)
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