package com.example.familysafety

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Contactmodel(val name:String, @PrimaryKey val number:String){

}
