package com.vix.monedas.monedasdetail;

import android.database.Cursor;
import android.os.AsyncTask;

import com.vix.monedas.interfaces.LoadMonedasTask;
import com.vix.monedas.data.Moneda;
import com.vix.monedas.data.MonedasDbHelper;

public class GetMonedaByIdTask extends AsyncTask<String, Void, Moneda> {

    private final MonedasDbHelper dbHelper;
    private final String id;
    private final LoadMonedasTask listener;

    public GetMonedaByIdTask(MonedasDbHelper dbHelper, String _id, LoadMonedasTask listener) {
        this.dbHelper = dbHelper;
        this.id = _id;
        this.listener = listener;
    }

    @Override
    protected Moneda doInBackground(String... strings) {
        Moneda moneda = null;

        Cursor cursor = dbHelper.getMonedaById(id);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                moneda = new Moneda(cursor);
            }
            cursor.close();
        }
        return moneda;
    }

    @Override
    protected void onPostExecute(Moneda moneda) {
        super.onPostExecute(moneda);
        listener.onTaskCompleted(moneda);
    }
}
