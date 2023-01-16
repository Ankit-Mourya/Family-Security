package com.example.familysafety

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.CAMERA,
        android.Manifest.permission.READ_CONTACTS)
    val permissioncode = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
          val bottomBar = findViewById<BottomNavigationView>(R.id.bottomBar)
        bottomBar.setOnItemSelectedListener {


            askforpermission()
            when (it.itemId) {
                R.id.guard -> {
                    inflatefragemnt(GuardFragment.newInstance())
                }
                R.id.home -> {
                    inflatefragemnt(HomeFragment.newInstance())
                }
                R.id.dashboard -> {
                    inflatefragemnt(MapsFragment())
                }
                R.id.profile -> {
                    inflatefragemnt(ProfileFragment.newInstance())
                }
            }
            true
        }
         bottomBar.selectedItemId=R.id.home
    }

    private fun askforpermission() {
              ActivityCompat.requestPermissions(this,permissions,permissioncode)
    }


    private fun inflatefragemnt(newInstance : Fragment) {
          var transaction =supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,newInstance)
        transaction.commit()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode== permissioncode){

            if(allpermissionsgranted()){
                //opencamera()
            }

            else{

            }
        }

    }

    private fun opencamera() {
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
             startActivity(intent) 
    }

    private fun allpermissionsgranted(): Boolean {
           for(item in permissions){
               if(ContextCompat.checkSelfPermission(this,item)!=PackageManager.PERMISSION_GRANTED) {
                   return false
               }
           }
        return true
    }


}