package com.julien.pratique_volley_population_sansklaxon

import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var listView : ListView

    var listeAdapter : ArrayList<HashMap<String, Any>>? = null


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

        listeAdapter = ArrayList<HashMap<String, Any>>()



        faireRequeteVolley()




    }

    fun decomposerReponse(tab : JSONArray){
        var total = 0
        for (i in 0..tab.length()-1){

            total += tab.getJSONObject(i).getInt("population")

        }

        for (i in 0..tab.length()-1){
            var pop = tab.getJSONObject(i).getInt("population")
            var temp = HashMap<String, Any>()
            temp.put("ville", tab.getJSONObject(i).get("ville").toString())
            temp.put("population", tab.getJSONObject(i).get("population").toString())
            temp.put("pourcentage", (pop*100)/total)

            //meilleur choix pour un pourcentage
            //val pourcentage = (pop.toDouble() * 100) / total
            //temp["pourcentage"] = String.format("%.1f%%", pourcentage)

            listeAdapter!!.add(temp)
        }

        val adapter = SimpleAdapter(this, listeAdapter, R.layout.simple_item, arrayOf("ville", "population", "pourcentage"), intArrayOf(R.id.champVille, R.id.champPopulation, R.id.champPourcentage))
        listView.adapter = adapter

    }

    fun faireRequeteVolley(){

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.jsonbin.io/v3/b/691c8be1d0ea881f40f0243b"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                val tab = response.getJSONArray("record")
                decomposerReponse(tab)

            },
            { Toast.makeText(this@MainActivity, "Grave erreur", Toast.LENGTH_SHORT).show() })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)


    }



}