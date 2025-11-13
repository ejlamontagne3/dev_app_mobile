package com.julien.annexe_planete

import android.icu.text.DisplayContext
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.util.Scanner

lateinit var listView : ListView

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

        listView = findViewById(R.id.listView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lireFichier())

        listView.adapter = adapter


        try {
            lireFichier()
        }catch (e: Exception){
            Toast.makeText(this, "Impossible d'ouvrir le fichier planète",
                LENGTH_SHORT
            )
        }


    }

    fun lireFichier() :ArrayList<String>{

        var listePlanetes : ArrayList<String> = ArrayList()
        val fis = openFileInput("planete.txt") //ouvre le fichier
        val s = Scanner(fis)
        s.useDelimiter("\\s+") //un ou plusieurs caractere blanc
        // On lit deux par deux : nom + valeur
        while (s.hasNext()) {
            var mot = s.next()       // ex : "Terre"
            if (s.hasNext()) {
                var mot2 = s.next()  // ex : "99"
                listePlanetes.add("$mot $mot2")
            }
        }

        s.close()
        return listePlanetes
    }



}