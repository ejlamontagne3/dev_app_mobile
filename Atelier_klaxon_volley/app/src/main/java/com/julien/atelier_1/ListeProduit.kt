package com.julien.atelier_1

import com.beust.klaxon.Json

class ListeProduit (){
    @Json( name = "accessoires") //si on veut utiliser un autre nom que celui dans le fichier json
    var articles : List<Produit> = emptyList()
}