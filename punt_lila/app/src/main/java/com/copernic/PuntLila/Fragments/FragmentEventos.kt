package com.copernic.PuntLila.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.copernic.PuntLila.Adapters.ListAdapter
import com.copernic.PuntLila.R
import com.copernic.PuntLila.databinding.FragmentEventosBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FragmentEventos : Fragment() {
    private var _bin: FragmentEventosBinding? = null
    private val bin get() = _bin!!
    private val myAdapter: ListAdapter = ListAdapter()
    private val db = Firebase.firestore

    data class ListaMenu(val item: String,  val link: String, val photo: String) {
        var eventName: String? = null
        var eventPhoto: String? = null
        var eventUri: String? = null

        init {
            this.eventName = item
            this.eventPhoto = photo
            this.eventUri = link
        }
    }

    // Infla la vista de layout y la asigna a una variable de binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _bin = FragmentEventosBinding.inflate(inflater, container, false)
        return bin.root
    }

    // Inicializa el RecyclerView al crear la vista
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        bin.mostrarMyEvents.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_fragment_eventos_to_fragment_mis_eventos)
        }
    }

    // Obtiene los datos de la base de datos al crear el fragmento
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bin = FragmentEventosBinding.inflate(layoutInflater)
        getList()
    }

    // Configura el tamaño fijo del RecyclerView
    private fun initRecyclerView() {
        bin.ReciclerView.setHasFixedSize(true)
        bin.ReciclerView.layoutManager = LinearLayoutManager(context)
    }

    // Código para obtener la lista de eventos de Firestore
    // La lista se almacena en una lista mutable de tipo ListaMenu
    // Luego se establece el adapter con la lista y se establece en el RecyclerView
    @SuppressLint("SuspiciousIndentation")
    private fun getList() {
        val lista: MutableList<ListaMenu> = arrayListOf()
        db.collection("Eventos") // se obtiene la coleccion de eventos en firestore
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    lista.add( ListaMenu(document["item"] as String,
                        document["link"] as String, document["photo"] as String
                    ))
                }
                myAdapter.ListaRecyclerAdapter(lista, requireContext()) // se establece el adapter con la lista
                bin.ReciclerView.adapter = myAdapter // se establece el adapter en el RecyclerView
            }
            .addOnFailureListener { exception ->
                Log.w("FragmentEventos", "Error getting documents: ", exception) // En caso de error se muestra en el log
            }
    }
}





