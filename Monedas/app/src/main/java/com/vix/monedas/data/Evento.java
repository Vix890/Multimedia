package com.vix.monedas.data;

import android.content.ContentValues;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import com.vix.monedas.data.EventosContrato.EventosEntry;

public class Evento {

    private final String id;
    private int editar;
    private int eliminar;
    private int consultar;
    private String fecha;
    private String hora;
    private String detalles;
    private String activo;

    public Evento(
            int _insertar,
            int _editar,
            int _eliminar,
            int _consultar,
            String _detalles
    ) {
        this.id = UUID.randomUUID().toString();
        this.insertar = _insertar;
        this.editar = _editar;
        this.eliminar = _eliminar;
        this.consultar = _consultar;
        Date date = new Date();
        this.fecha = new SimpleDateFormat("dd-MM-yyyy").format(date);
        this.hora = new SimpleDateFormat("HH:mm:ss").format(date);
        this.detalles = _detalles;
        this.activo = "activo";
    }

//    * posible error en el constructor
    public Evento(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(EventosEntry.ID);
        int insertarIndex = cursor.getColumnIndex(EventosEntry.INSERTAR);
        int editarIndex = cursor.getColumnIndex(EventosEntry.EDITAR);
        int eliminarIndex = cursor.getColumnIndex(EventosEntry.ELIMINAR);
        int consultarIndex = cursor.getColumnIndex(EventosEntry.CONSULTAR);
        int fechaIndex = cursor.getColumnIndex(EventosEntry.FECHA);
        int horaIndex = cursor.getColumnIndex(EventosEntry.HORA);
        int detallesIndex = cursor.getColumnIndex(EventosEntry.DETALLES);
        int activoIndex = cursor.getColumnIndex(EventosEntry.ACTIVO);

        id = idIndex != -1 ? cursor.getString(idIndex) : null;
        insertar = insertarIndex != -1 ? cursor.getInt(insertarIndex) : 0;
        editar = editarIndex != -1 ? cursor.getInt(editarIndex) : 0;
        eliminar = eliminarIndex != -1 ? cursor.getInt(eliminarIndex) : 0;
        consultar = consultarIndex != -1 ? cursor.getInt(consultarIndex) : 0;
        fecha = fechaIndex != -1 ? cursor.getString(fechaIndex) : null;
        hora = horaIndex != -1 ? cursor.getString(horaIndex) : null;
        detalles = detallesIndex != -1 ? cursor.getString(detallesIndex) : null;
        activo = activoIndex != -1 ? cursor.getString(activoIndex) : null;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(EventosEntry.ID, id);
        values.put(EventosEntry.INSERTAR, insertar);
        values.put(EventosEntry.EDITAR, editar);
        values.put(EventosEntry.ELIMINAR, eliminar);
        values.put(EventosEntry.CONSULTAR, consultar);
        values.put(EventosEntry.FECHA, fecha);
        values.put(EventosEntry.HORA, hora);
        values.put(EventosEntry.DETALLES, detalles);
        values.put(EventosEntry.ACTIVO, activo);

        return values;
    }

    public String getId() {
        return id;
    }

    public int getInsertar() {
        return insertar;
    }

    public void setInsertar(int insertar) {
        this.insertar = insertar;
    }

    private int insertar;

    public int getEliminar() {
        return eliminar;
    }

    public String getFecha() {
        return fecha;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getConsultar() {
        return consultar;
    }

    public void setConsultar(int consultar) {
        this.consultar = consultar;
    }

    public void setEliminar(int eliminar) {
        this.eliminar = eliminar;
    }

    public int getEditar() {
        return editar;
    }

    public void setEditar(int editar) {
        this.editar = editar;
    }

}
