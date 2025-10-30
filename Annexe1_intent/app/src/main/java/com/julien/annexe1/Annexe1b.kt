package com.julien.annexe1

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.Scanner

lateinit var question1 : TextView
lateinit var question2 : TextView
lateinit var question3 : TextView

class Annexe1b : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_annexe1b)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        question1 = findViewById(R.id.question1)
        question2 = findViewById(R.id.question2)
        question3 = findViewById(R.id.question3)

        question1.setText("Nombre de lignes du fichier: "+ calculNbLigne())
        question2.setText("Nombre de caractères du fichier: "+ calculNbCaractere())
        question3.setText("Nombre de &quot;c&quot; du fichier:  "+ calculNbC())

        val nom : String = "Julien"

        ecrireMonNom(nom)


    }

    fun calculNbLigne () : Int{

        var compteur : Int = 0
        val fis = openFileInput("Lorem.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)

        br.forEachLine{ ligne ->
            compteur += 1
        }

        return compteur

    }

    fun calculNbCaractere () : Int{

        var compteur : Int = 0
        val fis = openFileInput("Lorem.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)

        br.forEachLine{ ligne ->
           compteur += br.readLine().length //Li une ligne a la fois
        }

        return compteur

    }

    fun calculNbC () : Int{
        var compteur : Int = 0
        val fis = openFileInput("Lorem.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)

        br.forEachLine{ ligne ->

            compteur += ligne.count { it == 'c' }
        }

        return compteur
    }


    fun ecrireMonNom (nom:String) : Unit{

        val fos = openFileOutput("Lorem.txt", MODE_APPEND)
        val osw = OutputStreamWriter(fos)
        val bw = BufferedWriter(osw)

        bw.use{

            bw.write(nom)
            bw.newLine()

        }

    }

    fun calculNbMots () : Int{

        val fis = openFileInput("Lorem.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)
        var compteur : Int = 0

        br.forEachLine{ ligne ->

            compteur += ligne.split(" ").size
        }

        return compteur

    }

    fun nombreMotScanner () : Int{
        var compteur : Int =0
        val fis = openFileInput("Lorem.txt")
        val sc = Scanner(fis) //le delimiteur par defaut cest un caractere blanc (espace, retour de ligne)
        //sc.useDelimiter("\\d")
        fis.use {

            while(sc.hasNext()){ //tant quil y a des morceau

                compteur++
                sc.next()
            }

        }

        return compteur

    }



}