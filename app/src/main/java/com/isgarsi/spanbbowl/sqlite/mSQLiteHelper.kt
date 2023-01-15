package com.isgarsi.spanbbowl.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns


private const val SQL_WTABLE_CREATE_TABLE =
    "CREATE TABLE ${WeatherTableEntryObject.TABLE_NAME} (" +
        "${ BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
        "${WeatherTableEntryObject.COLUMN_NAME_DICE} TEXT," +
        "${WeatherTableEntryObject.COLUMN_NAME_NAME} TEXT," +
        "${WeatherTableEntryObject.COLUMN_NAME_DESCRIPTION} TEXT," +
        "${WeatherTableEntryObject.COLUMN_NAME_LANGUAGE} TEXT)"

private const val SQL_CTABLE_CREATE_TABLE =
    "CREATE TABLE ${CasualtyTableEntryObject.TABLE_NAME} (" +
            "${ BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
            "${CasualtyTableEntryObject.COLUMN_NAME_DICE} TEXT," +
            "${CasualtyTableEntryObject.COLUMN_NAME_RESULT} TEXT," +
            "${CasualtyTableEntryObject.COLUMN_NAME_EFFECT} TEXT," +
            "${CasualtyTableEntryObject.COLUMN_NAME_DESCRIPTION} TEXT," +
            "${CasualtyTableEntryObject.COLUMN_NAME_LANGUAGE} TEXT)"

private const val SQL_WTABLE_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${WeatherTableEntryObject.TABLE_NAME}"
private const val SQL_CTABLE_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${CasualtyTableEntryObject.TABLE_NAME}"

class mSQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(SQL_WTABLE_CREATE_TABLE)
        db.execSQL(SQL_CTABLE_CREATE_TABLE)

        createWeatherTableEntries(db)
        createCasualtyTableEntries(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_WTABLE_DELETE_ENTRIES)
        db.execSQL(SQL_CTABLE_DELETE_ENTRIES)
        onCreate(db)
    }

    fun createCasualtyTableEntries(db: SQLiteDatabase) {
        var insert = "INSERT INTO ${CasualtyTableEntryObject.TABLE_NAME}  (" +
                "${CasualtyTableEntryObject.COLUMN_NAME_DICE}, " +
                "${CasualtyTableEntryObject.COLUMN_NAME_RESULT}, " +
                "${CasualtyTableEntryObject.COLUMN_NAME_EFFECT}, " +
                "${CasualtyTableEntryObject.COLUMN_NAME_DESCRIPTION}, " +
                "${CasualtyTableEntryObject.COLUMN_NAME_LANGUAGE}) VALUES ";
        insert += "('11-38', 'Contusión', 'Sin efecto', 'Sin efecto adicional','es'),"
        insert += "('41-48', 'Lesión leve', 'LPPE', 'Penalización de un partido','es'),"
        insert += "('51-58', 'Lesión permamente', '-1 MO', 'Se pierde un partido y -1 en una habilidad','es'),"
        insert += "('61-68', 'Muerto', 'Muerto', 'No podrá volver a jugar en toda la liga','es'),"
        insert += "('11-38', 'Badly Hurt', 'No effect', 'No long term effect','en'),"
        insert += "('41-48', 'Minor Injury', 'LPPE', 'One match penalty','en'),"
        insert += "('51-58', 'Permanent Injury', '-1 MO', 'You lose a match and -1 on a skill','en'),"
        insert += "('61-68', 'Dead', 'Dead', 'It will not be able to play again in the entire league','en')"
        db.execSQL(insert)
    }

    fun createWeatherTableEntries(db: SQLiteDatabase){
        var insert = "INSERT INTO ${WeatherTableEntryObject.TABLE_NAME} (" +
                "${WeatherTableEntryObject.COLUMN_NAME_DICE}, " +
                "${WeatherTableEntryObject.COLUMN_NAME_NAME}, " +
                "${WeatherTableEntryObject.COLUMN_NAME_DESCRIPTION}, " +
                "${WeatherTableEntryObject.COLUMN_NAME_LANGUAGE}) VALUES ";
        insert += "('2', 'Calor asfixiante', '1D6 por jugador. Si el resultado es 1, el jugador caerá desmayado.', 'es'),"
        insert += "('3', 'Muy soleado', 'Se aplicará un -1 a todos los pases.', 'es'),"
        insert += "('4-10', 'Dia perfecto', 'Sin efecto. No se aplica ningún modificador.', 'es'),"
        insert += "('11', 'Lluvioso', 'Se aplicará un -1  a  atrapar  el  balón, en cualquier acción.', 'es'),"
        insert += "('12', 'Ventisca', 'Solo se permite hacer pases cortos durante el partido.', 'es'),"
        insert += "('2', 'Sweltering Heat', '1D6 per player. If the result is 1, the player will faint.', 'en'),"
        insert += "('3', 'Very Sunny', 'A -1 will be applied to all passes.', 'en'),"
        insert += "('4-10', 'Nice', 'Without effect. No modifier is applied.', 'en'),"
        insert += "('11', 'Pouring Rain', 'A -1 will be applied to catch the ball, in any action.', 'en'),"
        insert += "('12', 'Blizzard', 'Only short passes are allowed during the game.', 'en')"
        db.execSQL(insert)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "SpainBBowl.db"
    }
}