package com.example.proybim2cgbv

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class CollectionsActivity : AppCompatActivity() {

    var selectedCollection: Collection? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collections)

        val bundle = intent.extras
        val email = bundle?.getString("email")

        val btnBack = findViewById<MaterialToolbar>(R.id.collections_app_bar)
        btnBack.setOnClickListener {
            finish()
        }

        val btnNewCollection = findViewById<FloatingActionButton>(R.id.collections_fab_add)
        btnNewCollection.setOnClickListener {
            newDialog(
                this,
                layoutInflater.inflate(R.layout.activity_new_collection, null),
                resources.getString(R.string.new_collection_dialog_title),
                email.toString()
            )
        }

        loadCollections()
    }

    private fun loadCollections() {
        val gridView = findViewById<GridView>(R.id.gv_collections)
        AlertsFirebaseHelpers.getCollectionByEmail { collections ->
            val collectionAdapter = CollectionAdapter(this, collections)
            gridView.adapter = collectionAdapter
            collectionAdapter.notifyDataSetChanged()
            registerForContextMenu(gridView)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val gridView = findViewById<GridView>(R.id.gv_collections)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_collection, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        selectedCollection = gridView.getItemAtPosition(info.position) as Collection
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val collection = selectedCollection!!
        return when (item.itemId) {
            R.id.collection_menu_edit -> {
                //
                true
            }
            R.id.collection_menu_delete -> {
                AlertsFirebaseHelpers.deleteCollection(collection)
                loadCollections()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }


    private fun newDialog(context: Context, activity: View, title: String, email: String) {
        var collectionName: String
        //Removed: setMessage()
        MaterialAlertDialogBuilder(context).setTitle(title).setView(activity)
            .setPositiveButton("Crear") { _, _ ->
                collectionName = activity.findViewById<TextInputEditText>(
                    R.id.et_name_new_collection
                ).text.toString()

                AlertsFirebaseHelpers.createCollection(
                    email, Collection(collectionName, ArrayList())
                )
                loadCollections()
            }.setNegativeButton("Cancelar") { _, _ ->
                // Respond to negative button press
            }.show()
    }
}