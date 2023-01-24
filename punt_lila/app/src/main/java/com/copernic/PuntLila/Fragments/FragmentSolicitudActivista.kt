package com.copernic.PuntLila.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.copernic.PuntLila.databinding.FragmentSolicitudActivistaBinding


class FragmentSolicitudActivista : Fragment() {
    private var _bin: FragmentSolicitudActivistaBinding? = null
    private val bin get() = _bin!!
    private val _url = "https://forms.gle/ZNbssPTaP41rMzFA9"

    //Infla la vista de layout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bin = FragmentSolicitudActivistaBinding.inflate(layoutInflater)

    }

    //Evento click en boton enviar formulario
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bin.EnviarFormulari.setOnClickListener{
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(_url)
            startActivity(openURL)
        }

    }

    //Asocia la vista con el layout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bin = FragmentSolicitudActivistaBinding.inflate(inflater, container, false)
        return bin.root
    }
}
