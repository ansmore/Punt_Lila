package com.copernic.PuntLila.Models

data class Chat(
    var id: String = "",   //ID del chat
    var name: String = "", //Nombre del chat
    var users: List<String> = emptyList(), //Lista de usuarios involucrados en el chat
    var otherUser: String = ""  //Otro usuario involucrado en el chat
)
