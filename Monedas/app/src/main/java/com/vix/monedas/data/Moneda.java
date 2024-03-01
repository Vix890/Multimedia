package com.vix.monedas.data;

import android.content.ContentValues;
import android.database.Cursor;

import com.vix.monedas.data.MonedasContrato.MonedasEntry;

import java.util.UUID;

public class Moneda {

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    private final String id;
    private String moneda;
    private String fecha;
    private String precio;
    private String pais;
    private String material;
    private String imagen;
    private String activo;

    public Moneda(
            String _moneda,
            String _fecha,
            String _precio,
            String _pais,
            String _material,
            String _imagen
    ) {
        this.id = UUID.randomUUID().toString();
        this.moneda = _moneda;
        this.fecha = _fecha;
        this.precio = _precio;
        this.pais = _pais;
        this.material = _material;
        this.imagen = _imagen;
        this.activo = "activo";
    }

//    * posible error en el constructor
    public Moneda(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(MonedasEntry.ID);
        int monedaIndex = cursor.getColumnIndex(MonedasEntry.MONEDA);
        int fechaIndex = cursor.getColumnIndex(MonedasEntry.FECHA);
        int precioIndex = cursor.getColumnIndex(MonedasEntry.PRECIO);
        int paisIndex = cursor.getColumnIndex(MonedasEntry.PAIS);
        int materialIndex = cursor.getColumnIndex(MonedasEntry.MATERIAL);
        int imagenIndex = cursor.getColumnIndex(MonedasEntry.IMAGEN);
        int activoIndex = cursor.getColumnIndex(MonedasEntry.ACTIVO);

        id = idIndex != -1 ? cursor.getString(idIndex) : null;
        moneda = monedaIndex != -1 ? cursor.getString(monedaIndex) : null;
        fecha = fechaIndex != -1 ? cursor.getString(fechaIndex) : null;
        precio = precioIndex != -1 ? cursor.getString(precioIndex) : null;
        pais = paisIndex != -1 ? cursor.getString(paisIndex) : null;
        material = materialIndex != -1 ? cursor.getString(materialIndex) : null;
        imagen = imagenIndex != -1 ? cursor.getString(imagenIndex) : null;
        activo = activoIndex != -1 ? cursor.getString(activoIndex) : null;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(MonedasEntry.ID, id);
        values.put(MonedasEntry.MONEDA, moneda);
        values.put(MonedasEntry.FECHA, fecha);
        values.put(MonedasEntry.PRECIO, precio);
        values.put(MonedasEntry.PAIS, pais);
        values.put(MonedasEntry.MATERIAL, material);
        values.put(MonedasEntry.IMAGEN, imagen);
        values.put(MonedasEntry.ACTIVO, activo);

        return values;
    }

    public String getId() {
        return id;
    }

    public String getMoneda() {
        return moneda;
    }

    public String getFecha() {
        return fecha;
    }

    public String getPrecio() {
        return precio;
    }

    public String getPais() {
        return pais;
    }

    public String getMaterial() {
        return material;
    }

    public String getImagen() {
        return imagen;
    }

    public String getActivo() {
        return activo;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
}
