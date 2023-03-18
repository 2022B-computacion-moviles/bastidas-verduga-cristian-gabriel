package com.example.proybim2cgbv

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class CollectionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collections)

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
            )
        }
    }

    fun newDialog(context: Context, activity: View, title: String) {
        var collectionName: String
        //Removed: setMessage()
        MaterialAlertDialogBuilder(context).setTitle(title).setView(activity)
            .setPositiveButton("Crear") { dialog, which ->
                collectionName = activity.findViewById<TextInputEditText>(
                    R.id.et_name_new_collection
                ).text.toString()
            }.setNegativeButton("Cancelar") { dialog, which ->
                // Respond to negative button press
            }.show()
    }
}