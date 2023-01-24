package com.copernic.PuntLila.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.copernic.PuntLila.R
import com.google.firebase.auth.FirebaseAuth
import kotlin.properties.Delegates

class LoginActivity : AppCompatActivity() {

    private var email by Delegates.notNull<String>()
    private var password by Delegates.notNull<String>()
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText


    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        initialise()
    }

    // Inicializa la vista de la actividad de inicio de sesión
    private fun initialise() {
        etEmail = findViewById(R.id.correoId) //Asigna el EditText para el correo
        etPassword = findViewById(R.id.contraId) //Asigna el EditText para la contraseña
        mAuth = FirebaseAuth.getInstance() //Inicializa la instancia de FirebaseAuth
        val prefs = getSharedPreferences(
            MainActivity.PREF_NAME,
            Context.MODE_PRIVATE
        ) //obtiene el valor guardado en las preferencias
        val mailstr = prefs.getString("emailusuario", "") //asigna el valor obtenido a una variable
        etEmail.setText(mailstr) //coloca el valor en el EditText del correo
    }

    // Inicia sesión con el correo y la contraseña ingresados
    private fun loginUser() {
        email = etEmail.text.toString()
        password = etPassword.text.toString()
//verifica que los campos no estén vacíos
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        goHome()
                    } else {
                        Toast.makeText(
                            this, "Error en auntentificación.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(this, "Introducca todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    //Navega a la actividad principal
    private fun goHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun login(view: View) { //Función para iniciar sesión al presionar el botón
        loginUser()
    }

    fun fpswrec(view: View) { //Función para navegar a la actividad de recuperación de contraseña al presionar el botón
        intent = Intent(this, RecupPsw::class.java)
        startActivity(intent)
    }

    fun register(view: View) { //Función para navegar a la actividad de registro al presionar el botón
        intent = Intent(this, Registro::class.java)
        startActivity(intent)
    }
}
