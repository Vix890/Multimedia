package com.vix.monedas.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vix.monedas.data.MonedasContrato.MonedasEntry;

import android.database.SQLException;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public class MonedasDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Monedas.db";
    private Context context;

    public MonedasDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Verifica si la tabla ya existe
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + MonedasEntry.TABLE_NAME + "'", null);
        if(cursor!=null && cursor.getCount()>0) {
            cursor.close();
        } else {
            // La tabla no existe, créala e inserta datos
            String query = "CREATE TABLE " + MonedasEntry.TABLE_NAME + " ("
                    + MonedasEntry.ID + " TEXT PRIMARY KEY,"
                    + MonedasEntry.MONEDA + " TEXT NOT NULL,"
                    + MonedasEntry.FECHA + " TEXT NOT NULL,"
                    + MonedasEntry.PRECIO + " TEXT NOT NULL,"
                    + MonedasEntry.MATERIAL + " TEXT NOT NULL,"
                    + MonedasEntry.PAIS + " TEXT NOT NULL,"
                    + MonedasEntry.IMAGEN + " TEXT NOT NULL,"
                    + MonedasEntry.ACTIVO + " TEXT NOT NULL,"
                    + "UNIQUE (" + MonedasEntry.ID + "))";

            db.execSQL(query);

            copyImagesFromAssets(context);

            insertData(db);
        }
    }

    private void copyImagesFromAssets(Context context) {
        Log.v("MonedasDbHelper", "Copiando imágenes desde assets a la carpeta interna");
        AssetManager assetManager = context.getAssets();
        String[] imageNames;
        try {
            imageNames = assetManager.list(""); // Esto obtiene todos los archivos en "assets"
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        File internalDir = context.getDir("imagenes", Context.MODE_PRIVATE);

        assert imageNames != null;
        for (String imageName : imageNames) {
            try (InputStream in = assetManager.open(imageName);
                 OutputStream out = new FileOutputStream(new File(internalDir, imageName))) {

                byte[] buffer = new byte[1024];
                int read;

                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertMoneda(SQLiteDatabase db, Moneda _moneda) {
        // Contenedor de valores
        ContentValues values = new ContentValues();
        // Pares clave-valor
        values.put(MonedasEntry.ID, _moneda.getId());
        values.put(MonedasEntry.MONEDA, _moneda.getMoneda());
        values.put(MonedasEntry.FECHA, _moneda.getFecha());
        values.put(MonedasEntry.PRECIO, _moneda.getPrecio());
        values.put(MonedasEntry.MATERIAL, _moneda.getMaterial());
        values.put(MonedasEntry.PAIS, _moneda.getPais());
        values.put(MonedasEntry.IMAGEN, _moneda.getImagen());
        values.put(MonedasEntry.ACTIVO, _moneda.getActivo());

        try {
            db.insertOrThrow(MonedasEntry.TABLE_NAME, null, values);
        } catch (SQLException e) {
            Log.w("Error", Objects.requireNonNull(e.getMessage()));
        }
    }

    private void insertData(SQLiteDatabase db) {

        try {
            insertMoneda(db, new Moneda(
                    "Denario",
                    "-216 AC",
                    "300000",
                    "Roma",
                    "Plata",
                    "denario.jpg"
            ));

            insertMoneda(db, new Moneda(
                    "Calco",
                    "-212 AC",
                    "555555",
                    "Grecia",
                    "Plata",
                    "calco.jpg"
            ));

            insertMoneda(db, new Moneda(
                    "Caria",
                    "-400 AC",
                    "236600",
                    "Grecia",
                    "Bronce",
                    "caria.jpg"
            ));

            insertMoneda(db, new Moneda(
                    "Áureo de Julio César",
                    "-40 AC",
                    "3000000",
                    "Roma",
                    "Oro",
                    "aureo.jpg"
            ));

            insertMoneda(db, new Moneda(
                    "Doblón Español de 8 Escudos",
                    "1600",
                    "2500000",
                    "España",
                    "Oro",
                    "doblon_esp.jpg"
            ));

            insertMoneda(db, new Moneda(
                    "Dólar Flowing Hair",
                    "1794",
                    "2000000",
                    "EEUU",
                    "Plata",
                    "dollar.jpg"
            ));

            insertMoneda(db, new Moneda(
                    "Doblón de Brasher",
                    "1787",
                    "7000000",
                    "EEUU",
                    "Cobre",
                    "doblon_brasher.jpg"
            ));

            insertMoneda(db, new Moneda(
                    "Dracma",
                    "-237 AC",
                    "1500000",
                    "Grecia",
                    "Plata",
                    "dracma.jpg"
            ));


        } catch (SQLException e) {
            Log.e("Error", Objects.requireNonNull(e.getMessage()));
        }
    }

    public long saveMoneda(Moneda moneda) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                MonedasEntry.TABLE_NAME,
                null,
                moneda.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }

    public Cursor getAllMonedas() {
        return getReadableDatabase()
                .query(
                        MonedasEntry.TABLE_NAME,
                        null,
                        MonedasEntry.ACTIVO + " = ?",
                        new String[]{"activo"},
                        null,
                        null,
                        null);
    }

    public Cursor getMonedaById(String monedaId) {
        return getReadableDatabase().query(
                MonedasEntry.TABLE_NAME,
                null,
                MonedasEntry.ID + " LIKE ? AND " + MonedasEntry.ACTIVO + " = ?",
                new String[]{monedaId, "activo"},
                null,
                null,
                null);
    }

    public int deleteMoneda(String monedaId) {
        ContentValues values = new ContentValues();
        values.put(MonedasEntry.ACTIVO, "borrado");
        return getWritableDatabase().update(
                MonedasEntry.TABLE_NAME,
                values,
                MonedasEntry.ID + " LIKE ?",
                new String[]{monedaId});
    }

    public int updateMoneda(Moneda moneda, String monedaId) {
        return getWritableDatabase().update(
                MonedasEntry.TABLE_NAME,
                moneda.toContentValues(),
                MonedasEntry.ID + " LIKE ?",
                new String[]{monedaId}
        );
    }

    public int recuperarMonedasBorradas() {
        ContentValues values = new ContentValues();
        values.put(MonedasEntry.ACTIVO, "activo");
        return getWritableDatabase().update(
                MonedasEntry.TABLE_NAME,
                values,
                MonedasEntry.ACTIVO + " LIKE ?",
                new String[]{"borrado"});
    }
}