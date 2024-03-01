package com.vix.monedas.addeditmoneda;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.vix.monedas.R;
import com.vix.monedas.data.Evento;
import com.vix.monedas.data.EventoDbHelper;
import com.vix.monedas.data.Moneda;
import com.vix.monedas.data.MonedasDbHelper;
import com.vix.monedas.monedas.MonedasFragment;
import com.vix.monedas.monedasdetail.GetMonedaByIdTask;

public class AddEditMonedaFragment extends Fragment {

    private static final String ARG_MONEDA_ID = "arg_moneda_id";
    private String monedaId;
    private EditText nombreMoneda;
    private String nombreMonedaString;
    private EditText precioMoneda;
    private String precioMonedaString;
    private EditText paisMoneda;
    private String paisMonedaString;
    private EditText fechaMoneda;
    private String fechaMonedaString;
    private EditText materialMoneda;
    private String materialMonedaString;
    private Moneda monedaAEditar;
    private MonedasDbHelper dbHelper;
    private EventoDbHelper eventoDbHelper;

    public AddEditMonedaFragment() {
    }

    public static AddEditMonedaFragment newInstance(String _monedaId) {
        AddEditMonedaFragment fragment = new AddEditMonedaFragment();

        Bundle args = new Bundle();
        args.putString(ARG_MONEDA_ID, _monedaId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            monedaId = getArguments().getString(ARG_MONEDA_ID);
        }
        dbHelper = new MonedasDbHelper(requireContext());
        eventoDbHelper = new EventoDbHelper(requireContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_edit_moneda, container, false);

        inicializarComponentes(view);

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void inicializarComponentes(View view) {

        Button saveButton = view.findViewById(R.id.btn_add_edit_moneda);
        TextView textView = view.findViewById(R.id.text_add_edit_moneda);
        nombreMoneda = view.findViewById(R.id.editTextMoneda);
        precioMoneda = view.findViewById(R.id.editTextPrecio);
        paisMoneda = view.findViewById(R.id.editTextPais);
        fechaMoneda = view.findViewById(R.id.editTextFecha);
        materialMoneda = view.findViewById(R.id.editTextMaterial);

        if (monedaId != null) {
            requireActivity().setTitle("Editar Moneda");
            textView.setText("Editar Moneda");
            saveButton.setText("Actualizar");
            loadMoneda(monedaId);
            saveButton.setOnClickListener(v -> updateMoneda());
        } else {
            requireActivity().setTitle("Agregar Moneda");
            textView.setText("Agregar Moneda");
            saveButton.setText("Guardar");
            saveButton.setOnClickListener(v -> addMoneda());
        }
    }

    private void updateMoneda() {
        boolean result = getFormData();

        if (result) {
            if (monedaAEditar != null) {
                monedaAEditar.setMoneda(nombreMonedaString);
                monedaAEditar.setPrecio(precioMonedaString);
                monedaAEditar.setPais(paisMonedaString);
                monedaAEditar.setFecha(fechaMonedaString);
                monedaAEditar.setMaterial(materialMonedaString);

                dbHelper.updateMoneda(monedaAEditar, monedaId);

                Evento evento = new Evento(0, 1, 0, 0, "Updated moneda " + monedaId);
                eventoDbHelper.saveEvento(evento);
            }
            goBack();
        }
    }

    private void loadMoneda(String _monedaId) {

        new GetMonedaByIdTask(dbHelper, _monedaId, moneda -> {

            if (moneda != null) {
                monedaAEditar = moneda;
                llenarForm();
                Evento evento = new Evento(0, 0, 0, 1, "Get moneda by id para llenar formulario " + _monedaId);
                eventoDbHelper.saveEvento(evento);
            }
        }).execute();
    }

    private void llenarForm() {
        nombreMoneda.setText(monedaAEditar.getMoneda());
        precioMoneda.setText(monedaAEditar.getPrecio());
        paisMoneda.setText(monedaAEditar.getPais());
        fechaMoneda.setText(monedaAEditar.getFecha());
        materialMoneda.setText(monedaAEditar.getMaterial());
    }

    private void addMoneda() {

        boolean result = getFormData();

        if (result) {
            Moneda moneda = new Moneda(
                    this.nombreMonedaString,
                    this.precioMonedaString,
                    this.paisMonedaString,
                    this.fechaMonedaString,
                    this.materialMonedaString,
                    "monedaDefault.png"
            );
            dbHelper.saveMoneda(moneda);
            Evento evento = new Evento(1, 0, 0, 0, "Added moneda " + moneda.getId());
            eventoDbHelper.saveEvento(evento);
            goBack();
        }
    }

    private void goBack() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private boolean getFormData() {
        this.nombreMonedaString = nombreMoneda.getText().toString();
        this.precioMonedaString = precioMoneda.getText().toString();
        this.paisMonedaString = paisMoneda.getText().toString();
        this.fechaMonedaString = fechaMoneda.getText().toString();
        this.materialMonedaString = materialMoneda.getText().toString();

        return checkData();
    }

    private boolean checkData() {
        if (nombreMonedaString.isEmpty()) {
            nombreMoneda.setError("El nombre de la moneda es requerido");
            return false;
        }
        if (precioMonedaString.isEmpty()) {
            precioMoneda.setError("El precio de la moneda es requerido");
            return false;
        }
        if (paisMonedaString.isEmpty()) {
            paisMoneda.setError("El país de la moneda es requerido");
            return false;
        }
        if (fechaMonedaString.isEmpty()) {
            fechaMoneda.setError("La fecha de la moneda es requerida");
            return false;
        }
        if (materialMonedaString.isEmpty()) {
            materialMoneda.setError("El material de la moneda es requerido");
            return false;
        }
        return true;
    }
}