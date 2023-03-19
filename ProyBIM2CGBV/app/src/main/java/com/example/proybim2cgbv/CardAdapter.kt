package com.example.proybim2cgbv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class CardAdapter(
    context: Context, val cards: ArrayList<Card>
) : BaseAdapter() {
    val context = context
    override fun getCount(): Int {
        return cards.size
    }

    override fun getItem(position: Int): Any {
        return cards[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val card = this.cards[position]
        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val cardView = inflator.inflate(R.layout.card_item, null)
        cardView.findViewById<TextView>(R.id.tv_card_title).text = card.title
        cardView.findViewById<TextView>(R.id.tv_card_content).text = card.content
        val cardHandle = cardView.findViewById<MaterialCardView>(R.id.card)
        //cardHandle.setOnClickListener {
        // TODO
        //}

        // cardHandle.setOnLongClickListener {
        // cardHandle.isChecked = !cardHandle.isChecked
        // true
        // TODO
        //  true
        //}

        return cardView
    }
}