package com.vix.monedas.eventos;

import android.database.Cursor;
import android.os.AsyncTask;

import com.vix.monedas.data.EventoDbHelper;
import com.vix.monedas.monedas.EventosCursorAdapter;

public class EventosLoadTask extends AsyncTask<Void, Void, Cursor> {

    private final EventoDbHelper dbHelper;
    private final EventosCursorAdapter eventosAdapter;

    public EventosLoadTask(EventoDbHelper _dbHelper, EventosCursorAdapter _eventosAdapter) {
        this.dbHelper = _dbHelper;
        this.eventosAdapter = _eventosAdapter;
    }

    @Override
    protected Cursor doInBackground(Void... voids) {
        return dbHelper.getAllEventos();
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        eventosAdapter.changeCursor(cursor);
    }
}
