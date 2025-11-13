package com.examen1.julien.lamontagne.myapplication

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.Scanner

lateinit var analyseBtn: Button
lateinit var texteMeilleur: TextView

var info:String? = null

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        analyseBtn = findViewById(R.id.analyseBtn)
        texteMeilleur = findViewById(R.id.texteMeilleur)

        analyseBtn.setOnClickListener {
            val p = lireFichier()
            analyseBtn.setText("Analyse terminée")
            info = "${p.nom} ${p.taux}"
            texteMeilleur.setText(info)
        }

        try {
            info = lireInfo()
        }catch (e: Exception){
            info = ""
        }

        texteMeilleur.setText(info)

    }

    fun lireFichier(): Produit{
        var listeTaux = ArrayList<Produit>()
        val fis = resources.openRawResource(R.raw.taux)

        var s = Scanner(fis)
        s.useDelimiter("\\s+")

        while (s.hasNext()){

            var p = Produit(s.next(), s.next().toDouble())
            listeTaux.add(p)

        }

        var newListe = ArrayList<Produit>()
        newListe.addAll(listeTaux)
        //Meilleur taux à battre
        var plusUrgent = newListe.get(0).taux //celui a battre
        var listeTriee = ArrayList<Produit>()
        listeTriee.addAll(listeTaux)
        var indicePlusUrgent = 0

        while (listeTriee.size > 0){

            //trouver le plus urgent
            for (i in 0..newListe.size-1){

                if ( newListe.get(i).taux < newListe.get(indicePlusUrgent).taux){
                    plusUrgent = newListe.get(i).taux // remplace par celui que lon a trouvé
                    indicePlusUrgent = i
                }


            }
                listeTriee.removeAt(0)

        }

        return listeTaux.get(indicePlusUrgent)


    }

    fun lireInfo(): String{
        var taux:String = ""
        val fis: FileInputStream = openFileInput("meilleurTaux.txt")
        var isr = InputStreamReader(fis)
        var br = BufferedReader(isr)

        br.use {
            taux = br.readLine()
        }

        return taux
    }

    override fun onStop() {
        super.onStop()

        val fos = openFileOutput("meilleurTaux.txt", MODE_PRIVATE)
        val osw = OutputStreamWriter(fos)
        val bw = BufferedWriter(osw)
        bw.use {
            bw.write(info)
        }

    }
}