package com.example.proybim2cgbv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.android.material.textfield.TextInputEditText

class ForgotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        val email = findViewById<TextInputEditText>(R.id.et_email_forgot)

        val btnBack = findViewById<MaterialToolbar>(R.id.forgot_app_bar)
        btnBack.setOnClickListener {
            finish()
        }

        val btnSend = findViewById<Button>(R.id.btn_forgot_forgot)
        btnSend.setOnClickListener {
            if (email.text.toString().isNotEmpty()) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            AlertsFirebaseHelpers.showToast(
                                this, "Se ha enviado un correo para restablecer la contraseña"
                            )
                            finish()
                        } else {
                            AlertsFirebaseHelpers.showToast(
                                this, "El correo no está registrado"
                            )
                        }
                    }
            }
        }
    }
}