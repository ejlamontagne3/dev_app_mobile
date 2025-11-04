package com.julien.annexe1

object SingletonMemo {

    var liste :ArrayList<Memo> = ArrayList()

    // ajoute un Memo dans la liste
    fun ajoutMemo (memo:Memo){
        liste.add(memo)
    }

}