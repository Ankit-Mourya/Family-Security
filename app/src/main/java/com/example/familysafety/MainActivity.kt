package com.example.familysafety

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
          val bottomBar = findViewById<BottomNavigationView>(R.id.bottomBar)
        bottomBar.setOnItemSelectedListener {

            if(it.itemId==R.id.guard){
            inflatefragemnt(GuardFragment.newInstance())
                }
            else if(it.itemId==R.id.home){

                inflatefragemnt(HomeFragment.newInstance())
            }
            true
        }

    }



    private fun inflatefragemnt(newInstance : Fragment) {
          var transaction =supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,newInstance)
        transaction.commit()
    }


}