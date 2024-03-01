package com.vix.monedas.eventos;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.vix.monedas.R;

public class EventosCursorAdapter extends CursorAdapter {

    public EventosCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_evento, parent, false);
    }

    // ! pendiente de revisar
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView taskViewInsertar = view.findViewById(R.id.task_view_insertar);
        TextView taskViewEditar = view.findViewById(R.id.task_view_editar);
        TextView taskViewEliminar = view.findViewById(R.id.task_view_eliminar);
        TextView taskViewConsultar = view.findViewById(R.id.task_view_consultar);
        TextView taskViewFecha = view.findViewById(R.id.task_view_fecha);
        TextView taskViewHora = view.findViewById(R.id.task_view_hora);
        TextView taskViewDetalles = view.findViewById(R.id.task_view_detalles);

        int insertar = cursor.getInt(cursor.getColumnIndexOrThrow("insertar"));
        int editar = cursor.getInt(cursor.getColumnIndexOrThrow("editar"));
        int eliminar = cursor.getInt(cursor.getColumnIndexOrThrow("eliminar"));
        int consultar = cursor.getInt(cursor.getColumnIndexOrThrow("consultar"));
        String fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"));
        String hora = cursor.getString(cursor.getColumnIndexOrThrow("hora"));
        String detalles = cursor.getString(cursor.getColumnIndexOrThrow("detalles"));

        taskViewInsertar.setText(String.valueOf(insertar));
        taskViewEditar.setText(String.valueOf(editar));
        taskViewEliminar.setText(String.valueOf(eliminar));
        taskViewConsultar.setText(String.valueOf(consultar));
        taskViewFecha.setText(fecha);
        taskViewHora.setText(hora);
        taskViewDetalles.setText(detalles);
    }
}
