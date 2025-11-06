package com.julien.annexe3c

import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText

import android.widget.LinearLayout

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.FileOutputStream
import java.io.ObjectOutputStream


class MainActivity : AppCompatActivity() {

    lateinit var dent1: LinearLayout
    lateinit var dent2: LinearLayout
    var numeroDent1 : Int = 0
    var numeroDent2 : Int = 0
    var estTrait1 : Boolean = false
    var estTrait2 : Boolean = false
    var noteDent1 : String = ""
    var noteDent2 : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dent1 = findViewById(R.id.dent1)
        dent2 = findViewById(R.id.dent2)

        /*for (i in 0 until dent1.childCount  ){

        }*/
        var champDent1 = dent1.getChildAt(0) as EditText

        var estTraitCanal1 = dent1.getChildAt(1) as CheckBox

        var champNote1 = dent1.getChildAt(2) as EditText

        var champDent2 = dent2.getChildAt(0) as EditText

        var estTraitCanal2 = dent2.getChildAt(1) as CheckBox

        var champNote2 = dent2.getChildAt(2) as EditText

    }

    override fun onStop() {
        super.onStop()

        numeroDent1 = champDent1.text.toString().toInt()
        estTrait1 = estTraitCanal1.isChecked
        noteDent1 = champNote1.text.toString()

        numeroDent2 = champDent2.text.toString().toInt()
        estTrait2 = estTraitCanal2.isChecked
        noteDent2 = champNote2.text.toString()

        serealiseDent(numeroDent1, estTrait1, noteDent1)
        serealiseDent(numeroDent2, estTrait2, noteDent2)

    }

    fun serealiseDent(numeroDent: Int, estTraitCanal: Boolean, champNote: String){
        var dent = Dent(numeroDent, estTraitCanal, champNote)
        val fos: FileOutputStream = openFileOutput("dent.sert", MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)
        oos.use {
            oos.writeObject(dent)
        }
    }


}