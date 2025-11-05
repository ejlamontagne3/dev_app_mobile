package com.julien.annexe1

import android.content.Context
import android.content.Context.MODE_PRIVATE
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object SingletonMemo {

    var liste :ArrayList<Memo> = ArrayList()

    // ajoute un Memo dans la liste
    fun ajoutMemo (memo:Memo){
        liste.add(memo)
    }

    fun serialiseListe(context: Context){
        val fos : FileOutputStream = context.openFileOutput(" fichier.ser", MODE_PRIVATE)
        val oos = ObjectOutputStream (fos) // buffer stepcial pour les objets serealisable
        oos.use {
            oos.writeObject(liste)
        }
    }

    fun recupererListeSerealise(context: Context) : ArrayList<Memo>{
        val fis : FileInputStream = context.openFileInput(" fichier.ser")
        val ois = ObjectInputStream (fis) // buffer stepcial pour les objets serealisable
        ois.use {
            liste = ois.readObject() as ArrayList<Memo>
        }
        return ArrayList(liste)

    }

}