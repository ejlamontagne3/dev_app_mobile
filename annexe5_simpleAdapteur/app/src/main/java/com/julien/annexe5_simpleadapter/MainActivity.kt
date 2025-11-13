package com.julien.annexe5_simpleadapter

import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get


class MainActivity : AppCompatActivity() {

    lateinit var listViewAlbum: ListView

    var v:ArrayList<HashMap<String, Any>> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listViewAlbum = findViewById(R.id.listViewAlbum)
        v = remplirListe()
        val adapter = SimpleAdapter(this, v, R.layout.simple_item, arrayOf("palmares", "chanson", "date", "photoAlbum"), intArrayOf(R.id.palmares, R.id.chanson, R.id.date, R.id.photoAlbum))
        listViewAlbum.adapter = adapter


        listViewAlbum.setOnItemClickListener { parent, view, position, id ->

            Toast.makeText(this, v.get(position).get("chanson").toString(), Toast.LENGTH_SHORT).show()
        }

    }

    fun remplirListe(): ArrayList<HashMap<String, Any>>{

        var listeDonnees = ArrayList<HashMap<String, Any>>()
        var chanson = HashMap<String, Any>()
        chanson.put("palmares", "3")
        chanson.put("chanson", "Touch me")
        chanson.put("date", "22/03/86")
        chanson.put("photoAlbum", R.drawable.touchme)

        listeDonnees.add(chanson)

        //Crée un nouvel objet
        chanson = HashMap()
        chanson.put("palmares", "8")
        chanson.put("chanson", "Nothings gonna me now")
        chanson.put("date", "30/05/86")
        chanson.put("photoAlbum", R.drawable.nothing)

        listeDonnees.add(chanson)

        chanson = HashMap()
        chanson.put("palmares", "31")
        chanson.put("chanson", "Santa Maria")
        chanson.put("date", "28/03/98")
        chanson.put("photoAlbum", R.drawable.santamaria)

        listeDonnees.add(chanson)

        chanson = HashMap()
        chanson["palmares"] = "108"   //on peut le faire de cette facon aussi en Kotlin
        chanson.put("chanson", "Hot Boy")
        chanson.put("date", "10/04/2018")
        chanson.put("photoAlbum", R.drawable.hotboy)

        listeDonnees.add(chanson)

        return listeDonnees


    }


}