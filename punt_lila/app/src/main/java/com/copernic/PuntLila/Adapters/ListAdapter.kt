package com.copernic.PuntLila.Adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.copernic.PuntLila.Fragments.FragmentEventos
import com.copernic.PuntLila.databinding.ListEventosBinding

class ListAdapter: RecyclerView.Adapter<ListAdapter.ListViewHolder>(){
    var lista:MutableList<FragmentEventos.ListaMenu> = ArrayList()
    lateinit var context: Context
    private lateinit var binding: ListEventosBinding

    // Este método se utiliza para actualizar la lista de eventos y el contexto
    fun ListaRecyclerAdapter(list:MutableList<FragmentEventos.ListaMenu>, context: Context){
        this.lista = list
        this.context = context
    }

    // Este método se utiliza para crear la vista del elemento de la lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        binding = ListEventosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    // Este método se utiliza para vincular los datos del evento a la vista del elemento
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with(holder){
            with(lista[position]){
                binding.boton1.text = this.eventName
                binding.image1.load(this.eventPhoto)
            }
        }
        val item = lista[position]
        holder.bind(item)

        // Este evento se activa al hacer clic en el elemento de la lista
        holder.itemView.setOnClickListener {
            Toast.makeText(context, lista[position].eventName, Toast.LENGTH_LONG).show()
        }

        // Este evento se activa al hacer clic en el botón1
        binding.boton1.setOnClickListener{
            showMap(item.eventUri)
        }

        // Este evento se activa al hacer clic en el botón2
        binding.apply {
            boton2.setOnClickListener{
                val sharedPreferences = context.getSharedPreferences("eventosApuntado", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("eventosApuntado", item.eventName)
                editor.apply()
            }
        }
    }

    // Este método se utiliza para obtener el número de elementos en la lista
    override fun getItemCount(): Int {
        return lista.size
    }

    // Esta clase se utiliza para vincular los datos del evento a la vista del elemento
    class ListViewHolder(val binding: ListEventosBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(lista: FragmentEventos.ListaMenu) {}
    }

    // Este método se utiliza para mostrar la ubicación del evento en un mapa
    fun showMap(geoLocation: String?) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoLocation))
        context.startActivity(intent)

    }
}
