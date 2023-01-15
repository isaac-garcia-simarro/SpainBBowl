package com.isgarsi.spanbbowl.sqlite

import android.provider.BaseColumns

//https://developer.android.com/training/data-storage/sqlite#kotlin
object WeatherTableEntryObject : BaseColumns {
    const val TABLE_NAME = "WeatherTable"
    const val COLUMN_NAME_DICE = "dice"
    const val COLUMN_NAME_NAME = "name"
    const val COLUMN_NAME_DESCRIPTION = "description"
    const val COLUMN_NAME_LANGUAGE = "language"
}
