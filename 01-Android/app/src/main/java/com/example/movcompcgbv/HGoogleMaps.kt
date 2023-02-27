package com.example.movcompcgbv

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions

class HGoogleMaps : AppCompatActivity() {
    private lateinit var mapa: GoogleMap
    var permisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hgoogle_maps)

        val boton = findViewById<Button>(R.id.btn_ir_carolina)
        boton.setOnClickListener {
            irCarolina()
        }
        solicitarPermisos()
        iniciarLogicaMapa()
    }

    fun irCarolina() {
        val latitudCarolina = 4.598056
        val longitudCarolina = -74.075833
        val zoom = 17f
        val latLngCarolina = LatLng(latitudCarolina, longitudCarolina)
        moverCamaraConZoom(latLngCarolina, zoom)
    }

    fun solicitarPermisos() {
        val contexto = this.applicationContext
        val permisosFineLocation = ContextCompat.checkSelfPermission(
            contexto,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED

        if (tienePermisos) {
            permisos = true
        } else {
            ActivityCompat.requestPermissions(
                this, // Contexto
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), // Array de permisos
                1 // Código de petición
            )
        }
    }

    fun anadirMarcador(latLng: LatLng, title: String) {
        mapa.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
        )
    }

    fun moverCamaraConZoom(latLng: LatLng, zoom: Float = 10f) {
        mapa.moveCamera(
            CameraUpdateFactory.newLatLngZoom(latLng, zoom)
        )
    }

    fun escucharListeners() {
        mapa.setOnPolygonClickListener {
            Log.i("mapa", "Click en polígono ${it.id}")
            it.tag
        }

        mapa.setOnPolylineClickListener {
            Log.i("mapa", "Click en polilínea ${it.id}")
            it.tag
        }

        mapa.setOnMarkerClickListener {
            Log.i("mapa", "Click en marcador ${it.id}")
            it.tag
            return@setOnMarkerClickListener (true)
        }

        mapa.setOnCameraMoveListener {
            Log.i("mapa", "Moviendo cámara")
        }

        mapa.setOnCameraMoveStartedListener {
            Log.i("mapa", "Moviendo cámara ${it}")
        }
    }

    fun iniciarLogicaMapa() {
        val fragmentoMapa = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync { googleMap ->
            if (googleMap != null) {
                mapa = googleMap
                establecerConfiguracionMapa()
                irCarolina()
                val quicentro = LatLng(4.598056, -74.075833)
                val titulo = "Quicentro"
                val zoom = 17f
                anadirMarcador(quicentro, titulo)

                val poliLineaUno = googleMap.addPolyline(
                    PolylineOptions()
                        .clickable(true)
                        .add(
                            LatLng(4.598056, -74.075833),
                            LatLng(5.598056, -75.075833),
                            LatLng(3.598056, -73.075833),
                            LatLng(2.598056, -72.075833)
                        )
                )
                poliLineaUno.tag = "linea-1"


                val poligonoUno = googleMap.addPolygon(
                    PolygonOptions()
                        .clickable(true)
                        .add(
                            LatLng(4.598056, -74.075833),
                            LatLng(5.598056, -75.075833),
                            LatLng(3.598056, -73.075833),
                            LatLng(2.598056, -72.075833)
                        )
                )
                poligonoUno.fillColor = -0xc771c4
                poligonoUno.tag = "poligono-1"
                escucharListeners()
            }
        }
    }

    fun establecerConfiguracionMapa() {
        val contexto = this.applicationContext
        with(mapa) {
            val permisosFineLocation = ContextCompat.checkSelfPermission(
                contexto,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
            if (tienePermisos) {
                uiSettings.isZoomControlsEnabled = true
                mapa.isMyLocationEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
        }
    }

}