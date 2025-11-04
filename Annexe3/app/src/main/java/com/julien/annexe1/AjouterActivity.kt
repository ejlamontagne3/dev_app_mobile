package com.julien.annexe1

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.time.LocalDate

lateinit var champMemo : EditText
lateinit var champDate : TextView
lateinit var ajoutBtn : Button
lateinit var dateBtn : Button
var dateChoisie = LocalDate.now().plusDays(1)


class AjouterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ajouter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        champMemo = findViewById(R.id.champMemo)
        champDate = findViewById(R.id.textDate)
        dateBtn = findViewById(R.id.dateBtn)
        ajoutBtn = findViewById(R.id.ajoutBtn)

        ajoutBtn.setOnClickListener{

            //ajouter le memo au singleton
            //SingletonMemo.ajoutMemo(champMemo)


        }


        dateBtn.setOnClickListener{
            val d = DatePickerDialog(this)
            d.setOnDateSetListener(EcouteurDate())
            d.show()
        }





    }


    inner class EcouteurDate : OnDateSetListener{
        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

                //cest une methode static
            dateChoisie = LocalDate.of(year, month+1, dayOfMonth)
            champDate.setText(dateChoisie.toString())
            SingletonMemo.ajoutMemo(Memo(champMemo.text.toString(), dateChoisie))
            finish()
        }


    }







}