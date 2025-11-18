package com.julien.atelier_1

import android.os.Bundle
import android.widget.LinearLayout
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
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject


class vraiActivity : AppCompatActivity() {

    lateinit var texteLabel :TextView
    lateinit var prixLabel :TextView
    lateinit var liste : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vrai)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        liste = findViewById(R.id.listView)


        val queue = Volley.newRequestQueue(this)
        val url = "https://api.jsonbin.io/v3/b/67fe6a908a456b796689f63d?meta=false"

        //Créer la requête

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val tab = response.getJSONArray("accessoires")
                decomposerReponse(tab)
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Il y a une erreur", Toast.LENGTH_SHORT).show()
                error.printStackTrace()
            }




        )

        queue.add((jsonObjectRequest))

        liste.setOnItemClickListener { parent, view, position, id ->
            val linear = view as LinearLayout
            val champPrix: TextView = linear.findViewById(R.id.prixLabel)
            Toast.makeText(this, champPrix.text, Toast.LENGTH_SHORT).show()
        }

    }








    fun decomposerReponse(tab : JSONArray){

        //faites une arraylist de hashmap
        var remplir = ArrayList<HashMap<String, String>>()


        for (i in 0..tab.length()-1){ // tour des 3 objets du tableau
            var temp = HashMap<String, String>()
            temp.put("nom", tab.getJSONObject(i).get("nom").toString())
            temp.put("prix", tab.getJSONObject(i).get("prix").toString())

            remplir.add(temp)
        }





        //Créer un simpleAdapter
        val adapter = SimpleAdapter(this, remplir, R.layout.simple_item, arrayOf("nom", "prix"), intArrayOf(R.id.texteLabel, R.id.prixLabel))
        liste.adapter = adapter


    }

}