package com.julien.exam_final

import android.os.Bundle
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    lateinit var progressPapa : ProgressBar
    lateinit var progressMaman : ProgressBar
    lateinit var  progressCaroline : ProgressBar
    lateinit var titreLabel : TextView
    var total_maman = 0
    var total_papa = 0
    var total_caroline = 0
    var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        progressMaman = findViewById(R.id.progressMaman)
        progressPapa = findViewById(R.id.progressPapa)
        progressCaroline = findViewById(R.id.progressCaroline)
        titreLabel = findViewById(R.id.titreLabel)
        faireRequeteVolley()




    }

    fun faireRequeteVolley(){

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.jsonbin.io/v3/b/6763118eacd3cb34a8bbdcb1?meta=false"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                val tab = response.getJSONArray("cadeaux")
                decomposerReponse(tab)

            },
            { Toast.makeText(this@MainActivity, "Grave erreur", Toast.LENGTH_SHORT).show() })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)


    }

    fun decomposerReponse(tab : JSONArray){



        for (i in 0..tab.length()-1){


            if (tab.getJSONObject(i).getString("provenance") == "Maman"){
                total_maman += 1
            }
            else if (tab.getJSONObject(i).getString("provenance") == "Papa"){
                total_papa += 1
            }
            else if (tab.getJSONObject(i).getString("provenance") == "Caroline"){
                total_caroline += 1
            }

            total += 1

        }

        progressMaman.max = total
        progressPapa.max = total
        progressCaroline.max = total

        progressMaman.setProgress(total_maman)
        progressPapa.setProgress(total_papa)
        progressCaroline.setProgress(total_caroline)


    }


}