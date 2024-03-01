package com.vix.monedas.monedasdetail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vix.monedas.R;
import com.vix.monedas.addeditmoneda.AddEditMonedaFragment;
import com.vix.monedas.data.Evento;
import com.vix.monedas.data.EventoDbHelper;
import com.vix.monedas.data.MonedasDbHelper;

import java.io.File;

public class MonedaDetailFragment extends Fragment {

    private static final String ARG_MONEDA_ID = "arg_moneda_id";
    private String monedaId;
    private ImageView imagenMoneda;
    private TextView nombreMoneda;
    private TextView precioMoneda;
    private TextView paisMoneda;
    private TextView anioMoneda;
    private TextView materialMoneda;
    private Button btnVolver;
    private MonedasDbHelper dbHelper;
    private EventoDbHelper eventoDbHelper;

    public MonedaDetailFragment() {}

    public static MonedaDetailFragment newInstance(String _monedaId) {
        MonedaDetailFragment fragment = new MonedaDetailFragment();

        Bundle args = new Bundle();
        args.putString(ARG_MONEDA_ID, _monedaId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            monedaId = getArguments().getString(ARG_MONEDA_ID);
        }
        dbHelper = new MonedasDbHelper(requireContext());
        eventoDbHelper = new EventoDbHelper(requireContext());

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
        editItem.setVisible(true);
        deleteItem.setVisible(true);
        homeItem.setVisible(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moneda_detail, container, false);

        requireActivity().setTitle("Detalle Moneda");

        loadMoneda(monedaId);
        InitializeFields(view);


        btnVolver.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.edit_item_moneda) {
            AddEditMonedaFragment addEditMonedaFragment = AddEditMonedaFragment.newInstance(monedaId);
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, addEditMonedaFragment)
                    .addToBackStack(null)
                    .commit();
        }
        if (item.getItemId() == R.id.delete_item_moneda) {
            deleteMoneda(monedaId);
        }
        if (item.getItemId() == R.id.home_item_moneda) {
            requireActivity().getSupportFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteMoneda(String _id) {
        int result = dbHelper.deleteMoneda(_id);

        if (result > 0) {
            Toast.makeText(requireContext(), "Moneda eliminada", Toast.LENGTH_SHORT).show();
            Evento evento = new Evento(0, 0, 1, 0, "Deleted moneda" + _id);
            eventoDbHelper.saveEvento(evento);
        } else {
            Toast.makeText(requireContext(), "No se pudo eliminar la moneda", Toast.LENGTH_SHORT).show();
        }
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void InitializeFields(View view) {
        imagenMoneda = view.findViewById(R.id.imagen);
        nombreMoneda = view.findViewById(R.id.nombre_moneda);
        precioMoneda = view.findViewById(R.id.precio_moneda);
        paisMoneda = view.findViewById(R.id.pais_moneda);
        anioMoneda = view.findViewById(R.id.fecha_moneda);
        materialMoneda = view.findViewById(R.id.material_moneda);
        btnVolver = view.findViewById(R.id.volver);
    }

    private void loadMoneda(String _id) {
        new GetMonedaByIdTask(dbHelper, _id, moneda -> {
            if (moneda != null) {
                File imagePath = requireContext().getDir("imagenes", Context.MODE_PRIVATE);
                Glide
                        .with(requireActivity())
                        .asBitmap()
                        .load(Uri.parse("file://" + imagePath + "/" + moneda.getImagen()))
                        .error(R.drawable.exit_icon)
                        .fitCenter()
                        .into(imagenMoneda);
                nombreMoneda.setText(moneda.getMoneda());
                precioMoneda.setText(moneda.getPrecio());
                paisMoneda.setText(moneda.getPais());
                anioMoneda.setText(moneda.getFecha());
                materialMoneda.setText(moneda.getMaterial());

                Evento evento = new Evento(0, 0, 0, 1, "Get moneda by id" + _id);
                eventoDbHelper.saveEvento(evento);
            } else {
                Toast.makeText(requireContext(), "No se encontró la moneda", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }
}