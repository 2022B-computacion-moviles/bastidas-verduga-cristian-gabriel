package com.example.movcompcgbv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class IFirebaseUIAuth : AppCompatActivity() {

    lateinit var btnLogin: Button
    lateinit var btnLogout: Button
    lateinit var tvNombre: TextView


    fun cambiarNombre(nombre: String) {
        tvNombre.text = nombre
    }

    private val respuestaLoginIntent = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res: FirebaseAuthUIAuthenticationResult ->
        if (res.resultCode == RESULT_OK) {
            if (res.idpResponse != null) {
                this.seLogeo(res.idpResponse!!)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ifirebase_ui_auth)

        btnLogin = findViewById(R.id.btn_login_firebase)
        btnLogin.setOnClickListener {
            enviarIntentLogin()
        }

        btnLogout = findViewById(R.id.btn_logout_firebase)
        btnLogout.setOnClickListener {
            logout()
        }

        tvNombre = findViewById(R.id.tv_nombre_firebase)
        cambiarNombre("Ingresa con tu correo electrónico")
    }

    fun enviarIntentLogin() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        respuestaLoginIntent.launch(signInIntent)
    }

    fun logout() {
        btnLogout.visibility = View.INVISIBLE
        btnLogin.visibility = View.VISIBLE

        cambiarNombre("Ingresa con tu correo electrónico")
        FirebaseAuth.getInstance().signOut()
    }

    fun seLogeo(res: IdpResponse) {
        val btnLogin = findViewById<Button>(R.id.btn_intent_firebase_ui)
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)

        btnLogout.visibility = View.VISIBLE
        btnLogin.visibility = View.INVISIBLE
        tvNombre.text = res.email

        if (res.isNewUser) {
            registrarUsuarioPorPrimeraVez(res)
        }
    }

    fun registrarUsuarioPorPrimeraVez(res: IdpResponse) {
        // LOGICA PARA REGISTRAR USUARIO POR PRIMERA VEZ
    }
}