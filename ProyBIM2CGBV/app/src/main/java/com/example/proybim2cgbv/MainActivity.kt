package com.example.proybim2cgbv

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedDispatcher
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType {
    BASIC
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")


        val btnNewCard = findViewById<FloatingActionButton>(R.id.main_fab_add)
        btnNewCard.setOnClickListener {
            newDialog(
                this,
                layoutInflater.inflate(R.layout.activity_new_card, null),
                resources.getString(R.string.new_card_dialog_title),
                email.toString()
            )
        }

        val btnCollection = findViewById<FloatingActionButton>(R.id.main_fab_collection)
        btnCollection.setOnClickListener {
            val intent = Intent(this, CollectionsActivity::class.java).apply {
                putExtra("email", email)
            }
            startActivity(intent)
        }

        val btnPlay = findViewById<FloatingActionButton>(R.id.main_fab_play)
        btnPlay.setOnClickListener {
            val intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)
        }

        val btnLogout = findViewById<MaterialButton>(R.id.btn_account)
        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun newDialog(context: Context, activity: View, title: String, email: String) {
        var cardTitle: String
        var cardContent: String
        //Removed: setMessage()
        MaterialAlertDialogBuilder(context).setTitle(title).setView(activity)
            .setPositiveButton("Crear") { _, _ ->
                cardTitle = activity.findViewById<TextInputEditText>(
                    R.id.et_title_new_card
                ).text.toString()

                cardContent = activity.findViewById<TextInputEditText>(
                    R.id.et_content_new_card
                ).text.toString()

                val card = Card("", cardTitle, cardContent)
                AlertsFirebaseHelpers.createCard(
                    email, card
                )
            }.setNegativeButton("Cancelar") { _, _ ->
                // Respond to negative button press
            }.show()
    }
}