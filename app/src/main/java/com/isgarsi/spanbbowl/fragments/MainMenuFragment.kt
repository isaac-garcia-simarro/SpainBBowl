package com.isgarsi.spanbbowl.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.igs.passwordmanager.listeners.RecyclerMainActivityListener
import com.isgarsi.spanbbowl.R
import com.isgarsi.spanbbowl.adapters.MainMenuItemsAdapter
import com.isgarsi.spanbbowl.databinding.FragmentLoginBinding
import com.isgarsi.spanbbowl.databinding.FragmentMainMenuBinding
import com.isgarsi.spanbbowl.models.MainMenuItem

const val MM_SHIFTS = 1
const val MM_DICES = 2
const val MM_SKILLS = 3
const val MM_PASSES = 4
const val MM_TABLES = 5

class MainMenuFragment : Fragment(),
    RecyclerMainActivityListener {

    private lateinit var binding: FragmentMainMenuBinding
    private val mList: ArrayList<MainMenuItem> = ArrayList()
    private lateinit var mAdapter: MainMenuItemsAdapter
    private var mListener: IMainMenuFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainMenuBinding.inflate(layoutInflater, container, false)
        setUpRecyclerView()
        return binding.root
    }

    override fun onAttach(context: Context) {
        mListener = if(context is IMainMenuFragmentListener)  context else null
        super.onAttach(context)
    }

    override fun onDetach() {
        mListener = null
        super.onDetach()
    }

    private fun setUpRecyclerView() {
        mList.clear()
        mList.add(MainMenuItem(MM_SHIFTS, R.drawable.ic_shifts, getString(R.string.nav_item_shifts)))
        mList.add(MainMenuItem(MM_DICES, R.drawable.ic_dices, getString(R.string.nav_item_dices)))
        mList.add(MainMenuItem(MM_PASSES, R.drawable.ic_passes2, getString(R.string.nav_item_passes)))
        mList.add(MainMenuItem(MM_SKILLS, R.drawable.ic_skills, getString(R.string.nav_item_skills)))
        mList.add(MainMenuItem(MM_TABLES, R.drawable.ic_tables, getString(R.string.nav_item_tables)))

        val layoutManager = LinearLayoutManager(context)
        mAdapter = MainMenuItemsAdapter(mList, this, requireContext())

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        binding.recyclerView.adapter = mAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainMenuFragment()
    }


    /* ***************************************
    RecyclerMainActivityListener
     */
    override fun onItemSelected(item: MainMenuItem) {
        mListener?.onClickMenuItem(item)
    }


    interface IMainMenuFragmentListener{
        fun onClickMenuItem(item: MainMenuItem)
    }
}