package com.copernic.PuntLila.Fragments


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.copernic.PuntLila.databinding.FragmentRecursosBinding


class fragment_recursos : Fragment() {
    private var _bin: FragmentRecursosBinding? = null
    private val bin get() = _bin!!
    private val url = "https://www.terrassa.cat/es/com-muntar-un-punt-lila-"

    //Infla la vista de layout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bin = FragmentRecursosBinding.inflate(layoutInflater)
    }

    //Evento click boton lila
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bin.botonLila.setOnClickListener{
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(url)
            startActivity(openURL)
        }
    }

    //Asocia la vista con el layout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bin = FragmentRecursosBinding.inflate(inflater, container, false)
        return bin.root
    }
}
