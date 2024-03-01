package com.vix.monedas.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.vix.monedas.data.EventosContrato.EventosEntry;

import java.util.Objects;

public class EventoDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Monedas.db";

    public EventoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Verifica si la tabla ya existe
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + EventosEntry.TABLE_NAME + "'", null);
        if(cursor!=null && cursor.getCount()>0) {
            cursor.close();
        } else {
            // La tabla no existe, créala e inserta datos
            String query = "CREATE TABLE " + EventosEntry.TABLE_NAME + " ("
                    + EventosEntry.ID + " TEXT PRIMARY KEY,"
                    + EventosEntry.INSERTAR + " INTEGER NOT NULL,"
                    + EventosEntry.EDITAR + " INTEGER NOT NULL,"
                    + EventosEntry.ELIMINAR + " INTEGER NOT NULL,"
                    + EventosEntry.CONSULTAR + " INTEGER NOT NULL,"
                    + EventosEntry.FECHA + " TEXT NOT NULL,"
                    + EventosEntry.HORA + " TEXT NOT NULL,"
                    + EventosEntry.DETALLES + " TEXT NOT NULL,"
                    + EventosEntry.ACTIVO + " TEXT NOT NULL,"
                    + "UNIQUE (" + EventosEntry.ID + "))";

            db.execSQL(query);
        }
    }

    private void insertEvento(SQLiteDatabase db, Evento _evento) {
        // Contenedor de valores
        ContentValues values = new ContentValues();
        // Pares clave-valor
        values.put(EventosEntry.ID, _evento.getId());
        values.put(EventosEntry.INSERTAR, _evento.getInsertar());
        values.put(EventosEntry.EDITAR, _evento.getEditar());
        values.put(EventosEntry.ELIMINAR, _evento.getEliminar());
        values.put(EventosEntry.CONSULTAR, _evento.getConsultar());
        values.put(EventosEntry.FECHA, _evento.getFecha());
        values.put(EventosEntry.HORA, _evento.getHora());
        values.put(EventosEntry.ACTIVO, _evento.getActivo());

        try {
            db.insertOrThrow(EventosEntry.TABLE_NAME, null, values);
        } catch (SQLException e) {
            Log.w("Error", Objects.requireNonNull(e.getMessage()));
        }
    }

    public long saveEvento(Evento _evento) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                EventosEntry.TABLE_NAME,
                null,
                _evento.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }

    public Cursor getAllEventos() {
        return getReadableDatabase()
                .query(
                        EventosEntry.TABLE_NAME,
                        null,
                        EventosEntry.ACTIVO + " = ?",
                        new String[]{"activo"},
                        null,
                        null,
                        null);
    }

    public Cursor getEventoById(String _eventoId) {
        return getReadableDatabase().query(
                EventosEntry.TABLE_NAME,
                null,
                EventosEntry.ID + " LIKE ? AND " + EventosEntry.ACTIVO + " = ?",
                new String[]{_eventoId, "activo"},
                null,
                null,
                null);
    }

    public int deleteEvento(String _eventoId) {
        ContentValues values = new ContentValues();
        values.put(EventosEntry.ACTIVO, "borrado");
        return getWritableDatabase().update(
                EventosEntry.TABLE_NAME,
                values,
                EventosEntry.ID + " LIKE ?",
                new String[]{_eventoId});
    }

    public int updateEvento(Moneda moneda, String _eventoId) {
        return getWritableDatabase().update(
                EventosEntry.TABLE_NAME,
                moneda.toContentValues(),
                EventosEntry.ID + " LIKE ?",
                new String[]{_eventoId}
        );
    }

    public int recuperarEventosBorrados() {
        ContentValues values = new ContentValues();
        values.put(EventosEntry.ACTIVO, "activo");
        return getWritableDatabase().update(
                EventosEntry.TABLE_NAME,
                values,
                EventosEntry.ACTIVO + " LIKE ?",
                new String[]{"borrado"});
    }
}
