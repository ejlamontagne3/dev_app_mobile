package com.julien.revision_exam1

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

lateinit var reponse: TextView
lateinit var date: TextView

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

        reponse = findViewById(R.id.reponse)
        date = findViewById(R.id.date)

        var listeLangage = lireFichier()
        var listeLangageTrouve = trouveLangage(listeLangage)
        var nombreLangageTrouve: Int = listeLangageTrouve.size
        reponse.setText(nombreLangageTrouve.toString())

        try {
            val dateActuel = deserealiseDate()
            date.setText("${dateActuel.jour}  ${dateActuel.mois}  ${dateActuel.annee}")

        }catch (e: Exception){

            date.setText("1ere utilisation")
        }





    }

    fun lireFichier(): ArrayList<Langage>{

        var listeLangage: ArrayList<Langage> = ArrayList()

        val fis: FileInputStream = openFileInput("langage.txt")
        val isr: InputStreamReader = InputStreamReader(fis)
        val br: BufferedReader = BufferedReader(isr)
        br.use {

            br.forEachLine { ligne ->
                var tempListe = ligne.split(",")
                var l = Langage(tempListe.get(0), tempListe.get(1).toInt(), tempListe.get(2).toInt(), tempListe.get(3).toInt())
                listeLangage.add(l)
            }

        }
        listeLangage.removeAt(0)
        return listeLangage
    }

    fun trouveLangage(listeLangage: ArrayList<Langage>): ArrayList<Langage>{
        var tempListe: ArrayList<Langage> = ArrayList()
        for (langage in listeLangage){
            if (langage.classement3 == 99){
                tempListe.add(langage)
            }
        }
        return tempListe
    }

    fun serealiseDate(){

        val dateActuel: LocalDateTime = LocalDateTime()
        val d = Date(dateActuel.dayOfMonth, dateActuel.monthValue, dateActuel.year)
        val fos: FileOutputStream = openFileOutput("date.ser", MODE_PRIVATE)
        val oos: ObjectOutputStream = ObjectOutputStream(fos)
        oos.use {
            oos.writeObject(d)
        }



    }

    fun deserealiseDate(): Date{

        val fis: FileInputStream = openFileInput("date.ser")
        val ois: ObjectInputStream = ObjectInputStream(fis)
        ois.use {
            val d = ois.readObject() as Date
            return d
        }

    }

    override fun onStop() {
        super.onStop()
        serealiseDate()
    }
}