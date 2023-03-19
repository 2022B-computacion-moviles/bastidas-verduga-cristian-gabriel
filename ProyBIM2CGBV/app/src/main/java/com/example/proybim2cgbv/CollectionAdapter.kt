package com.example.proybim2cgbv

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CollectionAdapter(context: Context, private val collections: ArrayList<Collection>) :
    BaseAdapter() {
    val context = context
    override fun getCount(): Int {
        return collections.size
    }

    override fun getItem(position: Int): Any {
        return collections[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val collection = this.collections[position]
        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val collectionView = inflator.inflate(R.layout.collection_item, null)
        collectionView.findViewById<TextView>(R.id.tv_collection_name).text = collection.name
        val collectionHandle = collectionView.findViewById<View>(R.id.collection)


        collectionHandle.setOnClickListener {
            AlertsFirebaseHelpers.getCardsIdOfCollection(collection.name) { cardsIds ->
                if (cardsIds.isEmpty()) {
                    AlertsFirebaseHelpers.showToast(context, "No hay cartas es esta colección")
                } else {
                    AlertsFirebaseHelpers.getCardsByArrayId(cardsIds) { cards ->
                        var stringTitles = ""
                        var stringContents = ""

                        for (card in cards) {
                            stringTitles += card.title + "<|>"
                            stringContents += card.content + "<|>"
                        }

                        val intent = Intent(context, PlayActivity::class.java).apply {
                            putExtra("titles", stringTitles)
                            putExtra("contents", stringContents)
                        }
                        context.startActivity(intent)
                    }
                }

            }
        }

        //collectionHandle.setOnLongClickListener {
        // collectionHandle.isChecked = !collectionHandle.isChecked
        // true
        // TODO
        //    true
        //}

        return collectionView
    }
}