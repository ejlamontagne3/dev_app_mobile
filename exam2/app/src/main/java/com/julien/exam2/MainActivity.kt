package com.julien.exam2

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    lateinit var texteTitre : TextView
    lateinit var liste : ListView
    lateinit var boutonVerifier : Button
    var listeChoixRep1 : ArrayList<String>? = null
    var listeChoixRep2 : ArrayList<String>? = null
    var listeQuestions : ArrayList<String>? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        boutonVerifier = findViewById(R.id.boutonVerifier)
        liste = findViewById(R.id.liste)
        texteTitre = findViewById(R.id.texteTitre)

        listeQuestions = ArrayList()
        listeChoixRep1 = ArrayList()
        listeChoixRep2 = ArrayList()


        boutonVerifier.setOnClickListener {
            boutonVerifier.isEnabled = false
            val adapter2 = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, listeChoixRep2!!)
            texteTitre.text = listeQuestions!!.get(1)
            liste.adapter = adapter2
        }

        liste.setOnItemClickListener { parent, view, position, id ->
            var texte = view as TextView

            if (view.text == "Any" || view.text == ":"){
                boutonVerifier.isEnabled = true
                boutonVerifier.text = "Bonne reponse"
            }
            else{
                boutonVerifier.text = "Mauvaise reponse"
            }
        }

        faireRequete()

    }


    fun decomposerReponse(tab : JSONArray){

        for (i in 0..tab.length()-1){

            var titreQuestion = tab.getJSONObject(i).get("libelle").toString()

            listeQuestions!!.add(titreQuestion)
        }

        var choixQuestions = tab.getJSONObject(0).getJSONObject("choix_reponses")
        listeChoixRep1!!.add(choixQuestions.get("A").toString())
        listeChoixRep1!!.add(choixQuestions.get("B").toString())
        listeChoixRep1!!.add(choixQuestions.get("C").toString())

        var choixQuestions2 = tab.getJSONObject(1).getJSONObject("choix_reponses")
        listeChoixRep2!!.add(choixQuestions2.get("A").toString())
        listeChoixRep2!!.add(choixQuestions2.get("B").toString())
        listeChoixRep2!!.add(choixQuestions2.get("C").toString())


        val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, listeChoixRep1!!)
        texteTitre.text = listeQuestions!!.get(0)
        liste.adapter = adapter

    }

    fun faireRequete(){

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.jsonbin.io/v3/b/6927b373d0ea881f40029217?meta=false"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                val tab = response.getJSONArray("questions")
                decomposerReponse(tab)

            },
            { Toast.makeText(this@MainActivity, "Erreur", Toast.LENGTH_SHORT).show() })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)


    }





}