package com.example.proybim2cgbv

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore

class AlertsFirebaseHelpers {
    companion object {
        fun createUser(username: String, email: String) {
            FirebaseFirestore.getInstance().collection("users").document(email).set(
                hashMapOf(
                    "username" to username, "email" to email
                )
            )
        }

        fun showError(context: Context, message: String) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Error")
            builder.setMessage(message)
            builder.setPositiveButton("Aceptar", null)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun createCard(email: String, card: Card) {
            FirebaseFirestore.getInstance().collection("users").document(email).collection("cards")
                .document().set(
                    hashMapOf(
                        "title" to card.title, "content" to card.content
                    )
                )
        }

        fun createCollection(email: String, collection: Collection) {
            FirebaseFirestore.getInstance().collection("users").document(email)
                .collection("collections").document(collection.name).set(collection)
        }
    }
}