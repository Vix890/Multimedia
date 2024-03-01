package com.vix.monedas.eventos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vix.monedas.R;
import com.vix.monedas.data.EventoDbHelper;
import com.vix.monedas.monedas.MonedasFragment;

public class EventosFragment extends Fragment {

    private EventoDbHelper dbHelper;
    private ListView eventosListView;
    private EventosCursorAdapter eventosAdapter;

    public EventosFragment() {}

    public static EventosFragment newInstance() {
        return new EventosFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventos, container, false);

        eventosListView = view.findViewById(R.id.eventos_list);
        eventosAdapter = new EventosCursorAdapter(requireContext(), null);

        requireActivity().setTitle("Eventos");

        eventosListView.setAdapter(eventosAdapter);

        dbHelper = new EventoDbHelper(requireContext());

        loadEventos();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem addItem = menu.findItem(R.id.add_item_moneda);
        MenuItem editItem = menu.findItem(R.id.edit_item_moneda);
        MenuItem deleteItem = menu.findItem(R.id.delete_item_moneda);
        MenuItem homeItem = menu.findItem(R.id.home_item_moneda);

        addItem.setVisible(false);
        editItem.setVisible(false);
        deleteItem.setVisible(false);
        homeItem.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home_item_moneda) {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MonedasFragment()).commit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadEventos() {
        new EventosLoadTask(dbHelper, eventosAdapter).execute();
    }
}