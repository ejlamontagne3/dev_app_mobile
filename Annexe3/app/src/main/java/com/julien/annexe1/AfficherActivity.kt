package com.julien.annexe1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

lateinit var listView : ListView

class AfficherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_afficher)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listView = findViewById(R.id.listView)

        try {
            SingletonMemo.recupererListeSerealise(this)
        }
        catch (f : FileNotFoundException){
            Toast.makeText(this, " pas de fichier récupéré", LENGTH_LONG).show()

        }


        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lireMemos())
        listView.adapter = adapter


    }

    fun lireMemos() : ArrayList<String>
    {
        var liste = ArrayList<String>()
        for (memo in SingletonMemo.liste){
            liste.add(memo.memoCommeTel)
        }

        return liste

    }

    fun LireMemoTrier() : ArrayList<String>
    {
        var newListe = ArrayList<Memo>()
        var listeTriee = ArrayList<String>()
        newListe.addAll(SingletonMemo.liste)

        while (newListe.size > 0){
            //établir le plus urgent comme étant le premier
            var plusUrgent = newListe.get(0).echeance //celui a battre
            var indicePlusUrgent = 0

            //trouver le plus urgent
            for (i in 1..newListe.size-1){

                if ( newListe.get(i).echeance.isBefore(plusUrgent)){
                    plusUrgent = newListe.get(i).echeance // remplace par celui que lon a trouvé
                    indicePlusUrgent = i
                }

            }

            listeTriee.add(newListe.get(indicePlusUrgent).memoCommeTel) //je ne veux que les text
            newListe.removeAt(indicePlusUrgent)


        }

        return listeTriee


    }

    fun LireMemoTrier2() : ArrayList<String>{

        var newListe = ArrayList<Memo>()
        var listeTriee = ArrayList<String>()
        newListe.addAll(SingletonMemo.liste)

        // avec methode sort with


        newListe.sortWith(compareBy{it.echeance})
        for (memo in newListe){
            listeTriee.add(memo.memoCommeTel)
        }
        return listeTriee


    }








}