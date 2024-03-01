package com.vix.listadoamigos;


public class Contacto {

    private int id_contacto;
    private int imagen;
    private String nombre;
    private String desc;

    public Contacto(int id_contacto, int imagen, String nombre, String desc) {
        this.id_contacto = id_contacto;
        this.imagen = imagen;
        this.nombre = nombre;
        this.desc = desc;
    }

    public int getId_contacto() {
        return id_contacto;
    }

    public void setId_contacto(int id_contacto) {
        this.id_contacto = id_contacto;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}