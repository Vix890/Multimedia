package com.vix.monedas.monedas;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.vix.monedas.data.MonedasDbHelper;

public class MonedasLoadTask extends AsyncTask<Void, Void, Cursor> {

    private final MonedasDbHelper dbHelper;
    private final MonedasCursorAdapter monedasAdapter;

    public MonedasLoadTask(MonedasDbHelper dbHelper, MonedasCursorAdapter monedasAdapter) {
        this.dbHelper = dbHelper;
        this.monedasAdapter = monedasAdapter;
    }

    @Override
    protected Cursor doInBackground(Void... voids) {
        return dbHelper.getAllMonedas();
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        monedasAdapter.changeCursor(cursor);
    }
}
