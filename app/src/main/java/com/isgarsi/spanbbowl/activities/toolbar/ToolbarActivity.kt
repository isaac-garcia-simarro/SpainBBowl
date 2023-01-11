package com.igs.mylibrary.activities

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.igs.mylibrary.interfaces.IToolbar

open class ToolbarActivity : AppCompatActivity(), IToolbar {

    protected var _toolbar: Toolbar? = null

    override fun toolbarToLoad(toolbar: Toolbar?) {
        _toolbar = toolbar
        //_toolbar?.let{ (it) }
        setSupportActionBar(_toolbar)
    }

    override fun enableHomeDisplay(value: Boolean) {
        actionBar?.setDisplayHomeAsUpEnabled(value)
    }


}