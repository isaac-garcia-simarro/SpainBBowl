package com.isgarsi.spanbbowl.fragments

import android.os.Bundle
import android.provider.BaseColumns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.isgarsi.spanbbowl.R
import com.isgarsi.spanbbowl.adapters.CasualtyTableAdapter
import com.isgarsi.spanbbowl.adapters.SkillItemsAdapter
import com.isgarsi.spanbbowl.adapters.WeatherTableAdapter
import com.isgarsi.spanbbowl.databinding.FragmentDicesBinding
import com.isgarsi.spanbbowl.databinding.FragmentTablesBinding
import com.isgarsi.spanbbowl.models.CasualtyTableItem
import com.isgarsi.spanbbowl.models.SkillItem
import com.isgarsi.spanbbowl.models.WeatherTableItem
import com.isgarsi.spanbbowl.sqlite.CasualtyTableEntryObject
import com.isgarsi.spanbbowl.sqlite.WeatherTableEntryObject
import com.isgarsi.spanbbowl.sqlite.mSQLiteHelper
import java.util.*
import kotlin.collections.ArrayList

class TablesFragment : Fragment() {

    private lateinit var binding: FragmentTablesBinding

    private val weatherList = ArrayList<WeatherTableItem>()
    private val casualtyList = ArrayList<CasualtyTableItem>()
    private lateinit var weatherAdapter: WeatherTableAdapter
    private lateinit var casualtyAdapter: CasualtyTableAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTablesBinding.inflate(layoutInflater, container, false)


        loadDataFromDataBase()
        setUpRecyclersView()

        return binding.root
    }

    private fun setUpRecyclersView() {
        binding.rvWeatherTable.layoutManager = LinearLayoutManager(requireContext())
        weatherAdapter = WeatherTableAdapter(weatherList)
        binding.rvWeatherTable.adapter = weatherAdapter
        binding.rvWeatherTable.itemAnimator = DefaultItemAnimator()
        binding.rvWeatherTable.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        binding.rvCasualtyTable.layoutManager = LinearLayoutManager(requireContext())
        casualtyAdapter = CasualtyTableAdapter(casualtyList)
        binding.rvCasualtyTable.adapter = casualtyAdapter
        binding.rvCasualtyTable.itemAnimator = DefaultItemAnimator()
        binding.rvCasualtyTable.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    private fun loadDataFromDataBase(){

        val language = if(Locale.getDefault().language == "es") "es" else "en"

        val dbHelper = mSQLiteHelper(requireContext())
        val db = dbHelper.readableDatabase
        val cursorW = db.rawQuery("select ${BaseColumns._ID}, " +
                "${WeatherTableEntryObject.COLUMN_NAME_DICE}, " +
                "${WeatherTableEntryObject.COLUMN_NAME_NAME}, " +
                "${WeatherTableEntryObject.COLUMN_NAME_DESCRIPTION} " +
                "FROM ${WeatherTableEntryObject.TABLE_NAME} " +
                "WHERE ${WeatherTableEntryObject.COLUMN_NAME_LANGUAGE}='$language'", null)
        with(cursorW) {
            while (moveToNext()) {
                val item = WeatherTableItem(getLong(getColumnIndexOrThrow(BaseColumns._ID)),
                    getString(getColumnIndexOrThrow(WeatherTableEntryObject.COLUMN_NAME_DICE)),
                    getString(getColumnIndexOrThrow(WeatherTableEntryObject.COLUMN_NAME_NAME)),
                    getString(getColumnIndexOrThrow(WeatherTableEntryObject.COLUMN_NAME_DESCRIPTION)))
                weatherList.add(item)
            }
        }
        cursorW.close()

        val cursorC = db.rawQuery("select ${BaseColumns._ID}, " +
                "${CasualtyTableEntryObject.COLUMN_NAME_DICE}, " +
                "${CasualtyTableEntryObject.COLUMN_NAME_RESULT}, " +
                "${CasualtyTableEntryObject.COLUMN_NAME_EFFECT}, " +
                "${CasualtyTableEntryObject.COLUMN_NAME_DESCRIPTION} " +
                "FROM ${CasualtyTableEntryObject.TABLE_NAME} " +
                "WHERE ${CasualtyTableEntryObject.COLUMN_NAME_LANGUAGE}='$language' ", null)
        with(cursorC) {
            while (moveToNext()) {
                val item = CasualtyTableItem(getLong(getColumnIndexOrThrow(BaseColumns._ID)),
                    getString(getColumnIndexOrThrow(CasualtyTableEntryObject.COLUMN_NAME_DICE)),
                    getString(getColumnIndexOrThrow(CasualtyTableEntryObject.COLUMN_NAME_RESULT)),
                    getString(getColumnIndexOrThrow(CasualtyTableEntryObject.COLUMN_NAME_EFFECT)),
                    getString(getColumnIndexOrThrow(CasualtyTableEntryObject.COLUMN_NAME_DESCRIPTION)))
                casualtyList.add(item)
            }
        }
        cursorC.close()

        db.close()
    }

    companion object {
        @JvmStatic
        fun newInstance() = TablesFragment()
    }
}