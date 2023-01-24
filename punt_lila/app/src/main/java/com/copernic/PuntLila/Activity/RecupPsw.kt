package com.copernic.PuntLila.Activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.copernic.PuntLila.R
import com.copernic.PuntLila.databinding.RecupPasswActivityBinding
import com.google.firebase.auth.FirebaseAuth

class RecupPsw : AppCompatActivity() {
    private var etEmail: EditText? = null
    private var btnSend: Button? = null


    //Inicializar FirebaseAuth
    private var mAuth: FirebaseAuth? = null
    lateinit var binding: RecupPasswActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecupPasswActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.recup_passw_activity)
        initialise()

    }

    // Inicializar los elementos de la vista
    private fun initialise() {
        etEmail = findViewById<View>(R.id.RecuperarCorreo) as EditText
        btnSend = findViewById<View>(R.id.RecuperarCuenta) as Button
        mAuth = FirebaseAuth.getInstance()
        btnSend!!.setOnClickListener {
            sendPasswordResetEmail()
            // Crear una notificación cuando se envía el correo
            val builder = NotificationCompat.Builder(this, MainActivity.CHANNEL_ID).also {
                it.setSmallIcon(R.drawable.splash)
                it.setContentTitle(getString(R.string.channel_name))
                it.setContentText(getString(R.string.channel_description))
                it.setPriority(NotificationCompat.PRIORITY_HIGH)
            }.build()

            val notification = NotificationManagerCompat.from(this)
            notification.notify(0, builder)
        }
    }

    // Función para enviar el correo para restablecer la contraseña
    private fun sendPasswordResetEmail() {
        val email = etEmail?.text.toString()
        if (!TextUtils.isEmpty(email)) {
            mAuth!!
                .sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Email Enviado", Toast.LENGTH_SHORT).show()
                        goMain()
                    } else {
                        Toast.makeText(
                            this,
                            "No se encontró el usuario con este correo",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(this, "Agregue el correo", Toast.LENGTH_SHORT).show()
        }
    }

    // Ir a la pantalla de inicio de sesión después de enviar el correo
    private fun goMain() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
