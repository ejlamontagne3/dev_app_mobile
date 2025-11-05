package com.example.annexe_3b

import android.content.Context
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.ObjectOutputStream

lateinit var sonnerieBar : SeekBar
lateinit var mediaBar : SeekBar
lateinit var notificationsBar : SeekBar


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var ec = Ecouteur()
        sonnerieBar = findViewById(R.id.sonnerieBar)
        mediaBar = findViewById(R.id.mediaBar)
        notificationsBar = findViewById(R.id.nofiticationsBar)

        sonnerieBar.setOnSeekBarChangeListener(ec)
        mediaBar.setOnSeekBarChangeListener(ec)
        notificationsBar.setOnSeekBarChangeListener(ec)


        try {

        }
        catch (f : FileNotFoundException){
            Toast.makeText(this, "pas de volume sauvegardé", LENGTH_LONG).show()
        }



    }

    inner class Ecouteur : OnSeekBarChangeListener{
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBar?.setProgress(progress)

        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

        }


    }

    fun serialiseVolume(){
        val fos : FileOutputStream = openFileOutput(" fichier.ser", MODE_PRIVATE)
        val oos = ObjectOutputStream (fos)
        oos.use {
            oos.writeObject(liste)
        }
    }




}