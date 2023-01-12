package com.isgarsi.spanbbowl.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.isgarsi.spanbbowl.R
import com.isgarsi.spanbbowl.adapters.MainMenuItemsAdapter
import com.isgarsi.spanbbowl.adapters.SkillItemsAdapter
import com.isgarsi.spanbbowl.databinding.FragmentLoginBinding
import com.isgarsi.spanbbowl.databinding.FragmentSkillsBinding
import com.isgarsi.spanbbowl.models.MainMenuItem
import com.isgarsi.spanbbowl.models.SkillItem
import java.util.*
import kotlin.collections.ArrayList

class SkillsFragment : Fragment() {

    private lateinit var binding: FragmentSkillsBinding

    // get reference to the adapter class
    private var skillsList = ArrayList<SkillItem>()
    private lateinit var mAdaper: SkillItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSkillsBinding.inflate(layoutInflater, container, false)
        getSkillsFromFirebase()
        return binding.root
    }

    private fun getSkillsFromFirebase(){
        val db = Firebase.firestore
        val dbCollection = if(Locale.getDefault().language == "es") "skills_es" else "skills"

        db.collection(dbCollection)
            .get()
            .addOnSuccessListener { result ->
                skillsList.clear()
                for (document in result) {
                    val skill: SkillItem? = document.toObject(SkillItem::class.java)
                    skill?.let{ skillsList.add(it) }
                }
                setUpRecyclerView()
            }
            .addOnFailureListener { exception ->
                Log.w("wiii", "Error getting documents.", exception)
            }
    }

    private fun setUpRecyclerView() {
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        mAdaper = SkillItemsAdapter(skillsList)
        binding.rvList.adapter = mAdaper
    }

    companion object {
        @JvmStatic
        fun newInstance() = SkillsFragment()
    }
}