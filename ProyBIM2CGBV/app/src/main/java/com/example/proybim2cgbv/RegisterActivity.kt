package com.example.proybim2cgbv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val username = findViewById<TextInputEditText>(R.id.et_username_register)
        val email = findViewById<TextInputEditText>(R.id.et_email_register)
        val password = findViewById<TextInputEditText>(R.id.et_password_register)
        val passwordConfirm = findViewById<TextInputEditText>(R.id.et_password_confirm_register)

        val btnBack = findViewById<MaterialToolbar>(R.id.register_app_bar)
        btnBack.setOnClickListener {
            finish()
        }

        val btnRegister = findViewById<Button>(R.id.btn_register_register)
        btnRegister.setOnClickListener {
            if (username.text.toString().isNotEmpty() && email.text.toString()
                    .isNotEmpty() && password.text.toString()
                    .isNotEmpty() && passwordConfirm.text.toString().isNotEmpty()
            ) {
                if (password.text.toString() == passwordConfirm.text.toString()) {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        email.text.toString(), password.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            AlertsFirebaseHelpers.createUser(
                                username.text.toString(), email.text.toString()
                            )
                            finish()
                        } else {
                            AlertsFirebaseHelpers.showError(
                                this, "Se ha producido un error al crear el usuario"
                            )
                        }
                    }
                }
            }
        }
    }
}