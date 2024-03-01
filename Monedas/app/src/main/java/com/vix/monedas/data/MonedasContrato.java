package com.vix.monedas.data;

import android.provider.BaseColumns;

public class MonedasContrato {

    public static abstract class MonedasEntry implements BaseColumns {
        public static final String TABLE_NAME ="monedas";
        public static final String ID = "_id";
        public static final String MONEDA = "moneda";
        public static final String FECHA = "fecha";
        public static final String PRECIO = "precio";
        public static final String PAIS = "pais";
        public static final String MATERIAL = "material";
        public static final String IMAGEN = "imagen";
        public static final String ACTIVO = "activo";
    }
}