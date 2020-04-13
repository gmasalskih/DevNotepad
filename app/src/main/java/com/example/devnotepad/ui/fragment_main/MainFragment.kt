package com.example.devnotepad.ui.fragment_main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.devnotepad.DirectionOfStudy

import com.example.devnotepad.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment(), DirectionsAdapter.OnItemClickListener {

    companion object {
        fun newInstance() =
            MainFragment()
    }

    private lateinit var fragmentViewModel: MainFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragmentViewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        val adapter = DirectionsAdapter(requireContext(), this)
        mainRecyclerView.adapter = adapter
        mainRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        fragmentViewModel.allDirections.observe(viewLifecycleOwner, Observer { directions ->
            directions?.let { adapter.setArticles(it) }
        })
    }

    override fun onItemClick(directionOfStudy: DirectionOfStudy) {
        Toast.makeText(requireContext(), "${directionOfStudy.name}", Toast.LENGTH_SHORT).show()
    }

}
