package com.copernic.PuntLila.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.copernic.PuntLila.Adapters.ChatAdapter
import com.copernic.PuntLila.Models.Chat
import com.copernic.PuntLila.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*


class FragmentPuntosLila : Fragment() {

    //Declaración de variables
    private var mail = ""
    private lateinit var listChatsRecyclerView: RecyclerView
    private lateinit var newChatButton: Button
    private lateinit var newChatText: EditText

    private lateinit var auth: FirebaseAuth

    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_puntoslila, container, false)
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        mail = auth.currentUser!!.email.toString()

        //Comprueba si el email del usuario no está vacío
        if (mail.isNotEmpty()) {
            initViews(view)
        }
        return view
    }

    //Inicializar las vistas
    private fun initViews(view: View) {
        newChatButton = view.findViewById(R.id.newChatButton)
        newChatText = view.findViewById(R.id.newChatText)
        newChatButton.setOnClickListener { newChat() }

        listChatsRecyclerView = view.findViewById(R.id.listChatsRecyclerView)
        listChatsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        listChatsRecyclerView.adapter =
            ChatAdapter { chat ->
                chatSelected(chat)
            }

        val userRef = db.collection("usuarios").document(mail)

        userRef.collection("chats")
            .get()
            .addOnSuccessListener { chats ->
                val listChats = chats.toObjects(Chat::class.java)

                (listChatsRecyclerView.adapter as ChatAdapter).setData(listChats, mail)
            }

        userRef.collection("chats")
            .addSnapshotListener { chats, error ->
                if (error == null) {
                    chats?.let {
                        val listChats = it.toObjects(Chat::class.java)

                        (listChatsRecyclerView.adapter as ChatAdapter).setData(listChats, mail)
                    }
                }
            }
    }

    //Acción al seleccionar un chat
    private fun chatSelected(chat: Chat) {
        val bundle = bundleOf("chatId" to chat.id, "usuarios" to mail)
        val fragmentChat = FragmentChat()
        fragmentChat.arguments = bundle
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragmentChat)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    //Crear un nuevo chat
    private fun newChat() {
        // Genera un ID único para el nuevo chat
        val chatId = UUID.randomUUID().toString()
        // Obtiene el correo electrónico del otro usuario del EditText
        val otherUser = newChatText.text.toString()
        // Crea una lista con los usuarios involucrados en el chat
        val users = listOf(mail, otherUser)

        val chat = Chat(
            id = chatId,
            name = "Chat con $otherUser",
            users = users,
            otherUser = otherUser  //<- Asigna el valor aquí
        )

        // Agrega el nuevo chat a la base de datos
        db.collection("chats").document(chatId).set(chat)
        db.collection("usuarios").document(mail).collection("chats").document(chatId).set(chat)
        db.collection("usuarios").document(otherUser).collection("chats").document(chatId).set(chat)

        val bundle = bundleOf("chatId" to chat.id, "usuarios" to mail)
        val fragmentChat = FragmentChat()
        fragmentChat.arguments = bundle
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragmentChat)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
