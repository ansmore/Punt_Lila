package com.copernic.PuntLila.Activity


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.copernic.PuntLila.R
import com.copernic.PuntLila.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {


    // Declaración de variables para autenticación de Firebase
    private lateinit var auth: FirebaseAuth

    // Declaración de variable para vincular el layout
    lateinit var binding: ActivityMainBinding

    // Declaración de objeto de tipo Companion
    companion object {
        val CHANNEL_ID = "pantallasApp" // ID para notificaciones
        val PREF_NAME = "datosusuario" // Nombre del archivo de preferencias
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        createNotificationChannel() // Crea un canal de notificaciones
    }

    // Infla el menú en la barra de acción
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    // Maneja los eventos de selección del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.myNavHostFragment)
        return when (item.itemId) {


            R.id.eventos -> {
                //Navega al fragmento de eventos
                navController.navigate(R.id.action_global_fragment_eventos)
                //Cambia el título de la ActionBar a "Eventos"
                supportActionBar!!.title = getString(R.string.Eventos)
                return true
            }
            R.id.recursos -> {
                //Navega al fragmento de recursos
                navController.navigate(R.id.action_global_fragment_recursos)
                //Cambia el título de la ActionBar a "Recursos"
                supportActionBar!!.title = getString(R.string.Recursos)
                return true
            }
            R.id.puntos_lila -> {
                // obtiene el usuario actualmente autenticado
                val currentUser = auth.currentUser// si hay un usuario autenticado
                if (currentUser != null) {
                    val bundle = Bundle()
                    // agrega el email del usuario a un Bundle
                    bundle.putString("user", currentUser.email)
                    // navega a la pantalla de puntos lila
                    navController.navigate(R.id.action_global_fragment_puntoslila, bundle)
                    // cambia el título de la barra de acción
                    supportActionBar!!.title = getString(R.string.Chat)
                }
                return true
            }
            R.id.activista -> {
                // navega a la pantalla de solicitud de activista
                navController.navigate(R.id.action_global_fragment_solicitud_activista2)
                // cambia el título de la barra de acción
                supportActionBar!!.title = getString(R.string.activista)
                return true
            }
            R.id.mis_eventos -> {
                // navega a la pantalla de eventos
                navController.navigate(R.id.action_global_fragment_Mis_Eventos)
                // cambia el título de la barra de acción
                supportActionBar!!.title = getString(R.string.MisEvents)
                return true
            }
            R.id.logout -> {
                // Verifica si hay un usuario con sesión iniciada
                if (auth.currentUser != null) {
                    // Obtiene preferencias para almacenar el email del usuario
                    val prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                    val editor = prefs.edit()
                    // Almacena el email del usuario actual en las preferencias
                    editor.putString("emailusuario", auth.currentUser!!.email)
                    editor.commit()
                }
                // Crea un Intent para ir a la pantalla de inicio de sesión
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createNotificationChannel() {
        // Solo se crea el canal de notificación si la versión de Android es mayor o igual a O
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Obtiene el nombre y descripción del canal de notificación desde los recursos
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            // Crea el canal de notificación con el nombre, descripción, y configuraciones especificadas
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                lightColor = Color.BLUE
                enableLights(true)
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
