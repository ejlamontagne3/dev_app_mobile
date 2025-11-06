package com.julien.annexe_planete

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.FileOutputStream
import java.io.InputStreamReader

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



    }

    fun lireFichier() :ArrayList<String>{

        var listePlanetes : ArrayList<String> = ArrayList()
        val fis = openFileInput("planete.txt") //ouvre le fichier
        val isr = InputStreamReader(fis) //Crée un flux de donnée
        val br = BufferedReader(isr) //Crée un buffer pour lire plus vite
         br.forEachLine { ligne ->
             listePlanetes.add(ligne)
         }

        return listePlanetes

    }



}