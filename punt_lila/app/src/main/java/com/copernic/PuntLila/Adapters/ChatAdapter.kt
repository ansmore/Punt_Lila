package com.copernic.PuntLila.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.copernic.PuntLila.R
import com.copernic.PuntLila.Models.Chat


class ChatAdapter(private val chatClick: (Chat) -> Unit) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    var chats: List<Chat> = emptyList() //lista de chats
    private var currentUserEmail = "" //correo del usuario actual

    //asigna los datos a las variables
    fun setData(list: List<Chat>, currentUserEmail: String) {
        chats = list
        this.currentUserEmail = currentUserEmail
        notifyDataSetChanged()
    }

    //devuelve el correo del usuario con el que se está chateando
    fun getOtherUser(chat: Chat): String {
        for (user in chat.users) {
            if (user != currentUserEmail) {
                return user
            }
        }
        return ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
// Crea una nueva vista (invocada por el administrador de diseño)
        return ChatViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_chat,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        // Reemplaza el contenido de la vista.
        val chat = chats[position]
        val otherUser = getOtherUser(chat)
        holder.otherUserTextView.text = otherUser
        holder.itemView.setOnClickListener { chatClick(chat) }
    }

    override fun getItemCount() = chats.size

    class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val otherUserTextView: TextView = view.findViewById(R.id.otherUserTextView)
    }
}
