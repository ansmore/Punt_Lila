package com.copernic.PuntLila.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.copernic.PuntLila.R
import com.copernic.PuntLila.databinding.FragmentMisEventosBinding


class FragmentMisEventos : Fragment() {
    //Se declara una variable privada de tipo FragmentMisEventosBinding
    private var _binding: FragmentMisEventosBinding? = null
    //Se crea una variable de tipo FragmentMisEventosBinding que se inicializa con _binding
    private val binding get() = _binding!!
    //Se declara una variable privada de tipo String
    private var nombreEvento: String? = null

    //Se sobreescribe el metodo onCreate de la clase Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Se obtiene el argumento "eventosApuntado" y se asigna a la variable nombreEvento
        arguments?.let {
            nombreEvento = it.getString("eventosApuntado")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout del fragmento
        inflater.inflate(R.layout.fragment_mis_eventos, container, false)
        // Asocia el binding con el layout del fragmento
        _binding = FragmentMisEventosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtiene las preferencias compartidas
        val sharedPreferences = requireContext().getSharedPreferences("eventosApuntado", Context.MODE_PRIVATE)
        // Obtiene el nombre del evento guardado en las preferencias compartidas
        nombreEvento = sharedPreferences.getString("eventosApuntado", "")
        // Establece el texto del evento en el TextView correspondiente
        binding.eventosApuntado.text = nombreEvento
    }
}
