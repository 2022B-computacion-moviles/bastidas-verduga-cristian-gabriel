package com.example.proybim2cgbv

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
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

        fun updateCard(id: String, newCard: Card) {
            FirebaseFirestore.getInstance().collection("users").document(
                FirebaseAuth.getInstance().currentUser?.email.toString()
            ).collection("cards").document(id).set(
                hashMapOf(
                    "title" to newCard.title, "content" to newCard.content
                )
            )
        }

        fun getCardsByEmail(email: String, callback: (ArrayList<Card>) -> Unit) {
            FirebaseFirestore.getInstance().collection("users").document(email).collection("cards")
                .get().addOnSuccessListener { result ->
                    val cards = ArrayList<Card>()
                    for (document in result) {
                        val card = Card(
                            document.id,
                            document.data["title"] as String,
                            document.data["content"] as String
                        )
                        cards.add(card)
                    }
                    callback(cards)
                }
        }

        fun deleteCard(card: Card) {
            FirebaseFirestore.getInstance().collection("users").document(
                FirebaseAuth.getInstance().currentUser?.email.toString()
            ).collection("cards").document(card.id.toString()).delete()
            // Delete also from collections
            FirebaseFirestore.getInstance().collection("users").document(
                FirebaseAuth.getInstance().currentUser?.email.toString()
            ).collection("collections").get().addOnSuccessListener { result ->
                for (document in result) {
                    val cards = document.data["cards"] as ArrayList<String>
                    if (cards.contains(card.id.toString())) {
                        cards.remove(card.id.toString())
                        FirebaseFirestore.getInstance().collection("users").document(
                            FirebaseAuth.getInstance().currentUser?.email.toString()
                        ).collection("collections").document(document.id).set(
                            hashMapOf(
                                "name" to document.id,
                                "cards" to cards
                            )
                        )
                    }
                }
            }
        }


        fun createCollection(email: String, collection: Collection) {
            FirebaseFirestore.getInstance().collection("users").document(email)
                .collection("collections").document(collection.name).set(collection)
        }

        fun getCollectionByEmail(callback: (ArrayList<Collection>) -> Unit) {
            FirebaseFirestore.getInstance().collection("users").document(
                FirebaseAuth.getInstance().currentUser?.email.toString()
            ).collection("collections").get().addOnSuccessListener { result ->
                val collections = ArrayList<Collection>()
                for (document in result) {
                    val collection = Collection(
                        document.data["name"] as String, document.data["cards"] as ArrayList<String>
                    )
                    collections.add(collection)
                }
                callback(collections)
            }
        }

        fun deleteCollection(collection: Collection) {
            FirebaseFirestore.getInstance().collection("users").document(
                FirebaseAuth.getInstance().currentUser?.email.toString()
            ).collection("collections").document(collection.name).delete()
        }

        fun addCardToCollection(collection: String, selectedCard: Card, cards: ArrayList<String>) {
            cards.add(selectedCard.id.toString())
            var cardsU = ArrayList<String>()
            for (card in cards) {
                if (!cardsU.contains(card)) {
                    cardsU.add(card)
                }
            }
            FirebaseFirestore.getInstance().collection("users").document(
                FirebaseAuth.getInstance().currentUser?.email.toString()
            ).collection("collections").document(collection).set(
                Collection(collection, cardsU)
            )
        }

        fun getCardsByArrayId(cards: ArrayList<String>, callback: (ArrayList<Card>) -> Unit) {
            FirebaseFirestore.getInstance().collection("users").document(
                FirebaseAuth.getInstance().currentUser?.email.toString()
            ).collection("cards").get().addOnSuccessListener { result ->
                val cardsU = ArrayList<Card>()
                for (document in result) {
                    if (cards.contains(document.id)) {
                        val card = Card(
                            document.id,
                            document.data["title"] as String,
                            document.data["content"] as String
                        )
                        cardsU.add(card)
                    }
                }
                callback(cardsU)
            }
        }

        fun getCardsIdOfCollection(collection: String, callback: (ArrayList<String>) -> Unit) {
            FirebaseFirestore.getInstance().collection("users").document(
                FirebaseAuth.getInstance().currentUser?.email.toString()
            ).collection("collections").document(collection).get().addOnSuccessListener { result ->
                val cards = result.data?.get("cards") as ArrayList<String>
                callback(cards)
            }
        }
    }
}