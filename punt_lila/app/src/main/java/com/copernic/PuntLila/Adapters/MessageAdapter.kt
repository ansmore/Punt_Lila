package com.copernic.PuntLila.Adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.copernic.PuntLila.R
import com.copernic.PuntLila.Models.Message

class MessageAdapter(private val user: String): RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    private var messages: List<Message> = emptyList()

    //Lista vacía de mensajes
    fun setData(list: List<Message>) {
        messages = list
        notifyDataSetChanged()
    }
//Función para establecer datos en la lista de mensajes y notificar cambios en la vista

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_mensaje,
                parent,
                false
            )
        )
    }
//Función para crear el ViewHolder de cada mensaje

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        if (user == message.from) {
            holder.myMessageLayout.visibility = View.VISIBLE
            holder.otherMessageLayout.visibility = View.GONE

            holder.myMessageTextView.text = message.message
            //Muestra el layout de mensaje propio y oculta el de otro usuario
            //y establece el texto del mensaje en el TextView correspondiente

        } else {
            holder.myMessageLayout.visibility = View.GONE
            holder.otherMessageLayout.visibility = View.VISIBLE

            holder.othersMessageTextView.text = message.message
            //Muestra el layout de mensaje de otro usuario y oculta el propio
            //y establece el texto del mensaje en el TextView correspondiente
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val myMessageLayout: ConstraintLayout = itemView.findViewById(R.id.myMessageLayout)
        val otherMessageLayout: ConstraintLayout = itemView.findViewById(R.id.otherMessageLayout)
        val myMessageTextView: TextView = itemView.findViewById(R.id.myMessageTextView)
        val othersMessageTextView: TextView = itemView.findViewById(R.id.othersMessageTextView)
    }
}
