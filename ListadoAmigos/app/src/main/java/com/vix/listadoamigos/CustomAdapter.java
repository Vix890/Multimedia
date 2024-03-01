package com.vix.listadoamigos;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<Contacto> list;

    public CustomAdapter(Context context, List<Contacto> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ImageView ImageViewContacto;
        TextView TextViewNombre;
        TextView TextViewDescripcion;

        Contacto contact = list.get(i);

        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.listview_contact, null);

        ImageViewContacto = view.findViewById(R.id.imageViewContacto);
        TextViewNombre = view.findViewById(R.id.textViewNombre);
        TextViewDescripcion = view.findViewById(R.id.textViewDescripcion);

        ImageViewContacto.setImageResource(contact.getImagen());
        TextViewNombre.setText(contact.getNombre());
        TextViewDescripcion.setText(contact.getDesc());

        return view;
    }
}