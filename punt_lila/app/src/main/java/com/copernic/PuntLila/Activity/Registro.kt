package com.copernic.PuntLila.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.copernic.PuntLila.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class Registro : AppCompatActivity() {


    // Declaración de variables para enlazar las vistas con la clase
    private lateinit var bin: ActivityRegistroBinding
    // Declaración de la instancia de FirebaseAuth para la autenticación
    private lateinit var auth: FirebaseAuth
    // Declaración de la instancia de FirebaseFirestore para la base de datos
    private val db = FirebaseFirestore.getInstance()
    // TAG para mostrar en el log
    private companion object {
        private const val TAG = "Login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enlaza las vistas con la clase
        bin = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(bin.root)

        // Inicializa FirebaseAuth
        auth = Firebase.auth

        // Añade el evento click al botón de registro
        bin.botonRegistro.setOnClickListener {
            // Llama al método para crear el usuario con los datos introducidos en los campos de correo, nombre y contraseña
            crearUsuari(
                bin.correoId.text.toString(),
                bin.Nombre.text.toString(),
                bin.contraId.text.toString()
            )
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        // Comprueba si hay un usuario actualmente iniciado sesión
        currentUser?.let {

        }
    }

    // Método para crear un nuevo usuario con correo y contraseña
    private fun crearUsuari(email: String, nom: String, password: String) {
        Log.d(TAG, "Creacion usuario: $email, $nom, $password")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    // Comprueba si el nombre introducido tiene más de un caracter
                    if (nom.length > 1) posaNomUser(nom)
                    // Llama al método para ir a la pantalla principal
                    goHome()
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Método para asignar el nombre introducido al usuario creado
    private fun posaNomUser(nom: String) {
        val profileUpdates = userProfileChangeRequest {
            displayName = nom
        }
// Se utiliza el método updateProfile para actualizar el perfil del usuario
        auth.currentUser!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
// Si la actualización del perfil es exitosa, se procede a agregar los datos del usuario a la base de datos
                    agregarDatos()
                }
            }
    }

    private fun agregarDatos() {
        // Crea un hashmap con los datos del usuario
        val userdates = hashMapOf(
            "email" to bin.correoId.text.toString(),
            "name" to bin.Nombre.text.toString(),
            "password" to bin.contraId.text.toString()
        )

        //Agrega los datos al documento del usuario en la colección "users" en la base de datos
        db.collection("users").document(auth.currentUser!!.uid).set(userdates)
            .addOnSuccessListener { Log.d("TAG", "Se ha guardado correctamente") }
            .addOnFailureListener { e ->
                Log.w("TAG", "error $e")

            }
    }

    private fun goHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
