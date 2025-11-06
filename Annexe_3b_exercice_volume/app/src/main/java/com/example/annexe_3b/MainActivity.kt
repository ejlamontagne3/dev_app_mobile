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
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.ObjectInputStream
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

        sonnerieBar = findViewById(R.id.sonnerieBar)
        mediaBar = findViewById(R.id.mediaBar)
        notificationsBar = findViewById(R.id.nofiticationsBar)

        try {

            val v = deserealiseVolume()
            sonnerieBar.progress = v.sonnerie
            mediaBar.progress = v.media
            notificationsBar.progress = v.notifications

        }
        catch (f : FileNotFoundException){
            Toast.makeText(this, "pas de volume sauvegardé", LENGTH_LONG).show()
        }



    }

    override fun onStop() {
        super.onStop()

        serialiseVolume()

    }


    fun serialiseVolume(){
        val v : Volume = Volume(sonnerieBar.progress, mediaBar.progress, notificationsBar.progress)
        val fos : FileOutputStream = openFileOutput("fichier.ser", MODE_PRIVATE)
        val oos = ObjectOutputStream (fos)
        oos.use {
            oos.writeObject(v)
        }
    }

    fun deserealiseVolume() : Volume{

        val fis : FileInputStream = openFileInput("fichier.ser")
        val ois = ObjectInputStream (fis)
        ois.use {
            val v = ois.readObject() as Volume
            return v
        }

    }




}