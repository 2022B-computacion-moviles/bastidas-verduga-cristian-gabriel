package com.example.proybim2cgbv

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.Spinner
import androidx.activity.OnBackPressedDispatcher
import com.example.proybim2cgbv.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType {
    BASIC
}

class MainActivity : AppCompatActivity() {
    var selectedCard: Card? = null
    var cards = ArrayList<Card>()

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
            var stringTitles = ""
            var stringContents = ""

            for (card in cards) {
                stringTitles += card.title + "<|>"
                stringContents += card.content + "<|>"
            }

            val intent = Intent(this, PlayActivity::class.java).apply {
                putExtra("titles", stringTitles)
                putExtra("contents", stringContents)
            }
            startActivity(intent)
        }

        val btnLogout = findViewById<MaterialButton>(R.id.btn_account)
        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressedDispatcher.onBackPressed()
        }

        loadCards()
    }

    private fun loadCards() {
        val gridView = findViewById<GridView>(R.id.gv_main)
        AlertsFirebaseHelpers.getCardsByEmail(
            FirebaseAuth.getInstance().currentUser?.email.toString()
        ) { cards ->
            this.cards = cards
            val cardAdapter = CardAdapter(this, cards)
            gridView.adapter = cardAdapter
            cardAdapter.notifyDataSetChanged()
            registerForContextMenu(gridView)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val gridView = findViewById<GridView>(R.id.gv_main)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        selectedCard = gridView.getItemAtPosition(info.position) as Card
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val card = selectedCard!!
        return when (item.itemId) {
            R.id.card_menu_edit -> {
                //
                true
            }
            R.id.card_menu_delete -> {
                AlertsFirebaseHelpers.deleteCard(card)
                loadCards()
                true
            }
            R.id.card_menu_add -> {
                addToDialog(
                    this, layoutInflater.inflate(R.layout.activity_add_to, null)
                )
                true
            }
            else -> super.onContextItemSelected(item)
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

                loadCards()
            }.setNegativeButton("Cancelar") { _, _ ->
                // Respond to negative button press
            }.show()
    }

    private fun addToDialog(context: Context, activity: View) {
        var spinner = activity.findViewById<Spinner>(R.id.spinner_collections)
        var cards = ArrayList<ArrayList<String>>()
        AlertsFirebaseHelpers.getCollectionByEmail { collections ->
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item,
                collections.map { it.name })
            cards = collections.map { it.cards } as ArrayList<ArrayList<String>>
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        MaterialAlertDialogBuilder(context).setTitle("Añadir a colección").setView(activity)
            .setPositiveButton("Añadir") { _, _ ->
                AlertsFirebaseHelpers.addCardToCollection(
                    spinner.selectedItem.toString(),
                    selectedCard!!,
                    cards[spinner.selectedItemPosition]
                )
            }.setNegativeButton("Cancelar") { _, _ ->
                // Respond to negative button press
            }.show()
    }
}