package com.copernic.PuntLila.Models

import java.util.*

data class Message (
    var message: String = "", //Texto del mensaje
    var from: String = "",    //Remitente del mensaje
    var dob: Date = Date()    //Fecha de envío del mensaje
)
