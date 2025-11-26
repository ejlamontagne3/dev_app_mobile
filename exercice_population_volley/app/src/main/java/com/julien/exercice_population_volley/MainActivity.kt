package com.julien.exercice_population_volley

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon

class MainActivity : AppCompatActivity() {

    lateinit var listView : ListView
    var listeAdapter : ArrayList<HashMap<String, String>>? = null
    var listeVille : ListeVille? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listView = findViewById(R.id.ListView)

        faireRequete(this)



    }

    fun creerListe(liste: ListeVille): ArrayList<HashMap<String, String>> {

        val listeHashmap = ArrayList<HashMap<String, String>>()

        for (ville in liste.record) {

            val hashMap = HashMap<String, String>()

            hashMap["ville"] = ville.ville
            hashMap["population"] = ville.population.toString()

            listeHashmap.add(hashMap)
        }

        return listeHashmap
    }

    fun faireRequete(context : Context){
        // Crée la queue de requete
        val queue = Volley.newRequestQueue(context)
        val url = "https://api.jsonbin.io/v3/b/691c8be1d0ea881f40f0243b"

        // notre requete demande une string au server
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {response ->
                listeVille = Klaxon().parse<ListeVille>(response) ?: ListeVille(emptyList(), null)
                if (listeVille!!.record.isNotEmpty()){

                    listeAdapter = creerListe(listeVille!!)
                    val adapter = SimpleAdapter(this, listeAdapter, R.layout.simple_item, arrayOf("ville", "population"), intArrayOf(R.id.champNom, R.id.champReponse))
                    listView.adapter = adapter
                }
            },
            Response.ErrorListener { Toast.makeText(this@MainActivity, "error", Toast.LENGTH_SHORT).show()  })

// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }



}