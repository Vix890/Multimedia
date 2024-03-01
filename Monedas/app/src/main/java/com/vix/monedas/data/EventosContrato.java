package com.vix.monedas.data;

import android.provider.BaseColumns;

public class EventosContrato {
    public static abstract class EventosEntry implements BaseColumns {
        public static final String TABLE_NAME ="eventos";
        public static final String ID = "_id";
        public static final String INSERTAR = "insertar";
        public static final String EDITAR = "editar";
        public static final String ELIMINAR = "eliminar";
        public static final String CONSULTAR = "consultar";
        public static final String FECHA = "fecha";
        public static final String HORA = "hora";
        public static final String DETALLES = "detalles";
        public static final String ACTIVO = "activo";
    }
}
