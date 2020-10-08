package com.madchan.migratedatademo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.madchan.migratedatademo.R
import com.madchan.migratedatademo.adapter.MessageListAdapter
import com.madchan.migratedatademo.dummy.DummyContent

class MessageFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_message, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = MessageListAdapter(DummyContent.ITEMS)
        }
        return view
    }

}