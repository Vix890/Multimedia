package com.vix.monedas.monedas;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.vix.monedas.R;
import com.vix.monedas.addeditmoneda.AddEditMonedaFragment;
import com.vix.monedas.data.Evento;
import com.vix.monedas.data.EventoDbHelper;
import com.vix.monedas.data.MonedasContrato;
import com.vix.monedas.data.MonedasDbHelper;
import com.vix.monedas.monedasdetail.MonedaDetailFragment;

public class MonedasFragment extends Fragment {

    public static final int REQUEST_UPDATE_DELETE_MONEDA = 2;
    private MonedasDbHelper dbHelper;
    private EventoDbHelper eventoDbHelper;
    private ListView monedasListView;
    private MonedasCursorAdapter monedasAdapter;

    public MonedasFragment() {}

    public static MonedasFragment newInstance() {
        return new MonedasFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monedas, container, false);

        monedasListView = view.findViewById(R.id.monedas_list);
        monedasAdapter = new MonedasCursorAdapter(requireContext(), null);

        monedasListView.setAdapter(monedasAdapter);

        monedasListView.setOnItemClickListener((AdapterView, view1, position, id) -> {
            Cursor cursor = (Cursor) monedasAdapter.getItem(position);
            int columnIndex = cursor.getColumnIndex(MonedasContrato.MonedasEntry.ID);
            if (columnIndex != -1) {
                String monedaId = cursor.getString(columnIndex);
                showDetailScreen(monedaId);
            } else {
                Toast.makeText(requireContext(), "Error al obtener el id de la moneda", Toast.LENGTH_SHORT).show();
            }
        });
        
        dbHelper = new MonedasDbHelper(requireContext());
        eventoDbHelper = new EventoDbHelper(requireContext());

        dbHelper.onCreate(dbHelper.getWritableDatabase());
        eventoDbHelper.onCreate(eventoDbHelper.getWritableDatabase());

        loadMonedas();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMonedas();
    }

    private void loadMonedas() {
        new MonedasLoadTask(dbHelper, monedasAdapter).execute();
        Evento evento = new Evento(0, 0, 0, 1, "Get all monedas");
        eventoDbHelper.saveEvento(evento);
    }

    private void showDetailScreen(String _monedaId) {

        MonedaDetailFragment detailMonedaFragment = MonedaDetailFragment.newInstance(_monedaId);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, detailMonedaFragment)
                .addToBackStack(null)
                .commit();
    }
}