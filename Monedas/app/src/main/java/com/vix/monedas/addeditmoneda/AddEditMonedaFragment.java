package com.vix.monedas.addeditmoneda;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.vix.monedas.R;
import com.vix.monedas.data.Evento;
import com.vix.monedas.data.EventoDbHelper;
import com.vix.monedas.data.Moneda;
import com.vix.monedas.data.MonedasDbHelper;
import com.vix.monedas.monedas.MonedasFragment;
import com.vix.monedas.monedasdetail.GetMonedaByIdTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
    private ImageView imageViewMoneda;
    private Moneda monedaAEditar;
    private MonedasDbHelper dbHelper;
    private EventoDbHelper eventoDbHelper;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private String imageUri;


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
        setHasOptionsMenu(true);
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
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        imageViewMoneda = view.findViewById(R.id.imageViewMoneda);

        Button btnSelectImage = view.findViewById(R.id.buttonSelectImage);
        btnSelectImage.setOnClickListener(v -> {
            openFileChooser();
        });

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

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            if (mImageUri != null && copiarImagenLocal(mImageUri) != null) {
                imageUri = copiarImagenLocal(mImageUri);
            }
        }
    }

    private String copiarImagenLocal(Uri selectedImageUri) {
        Context context = getContext();

        if (context != null) {
            File internalDir = context.getDir("imagenes", Context.MODE_PRIVATE);
            String imageName = getFileNameFromUri(selectedImageUri);

            try (InputStream in = context.getContentResolver().openInputStream(selectedImageUri);
                 OutputStream out = new FileOutputStream(new File(internalDir, imageName))) {

                byte[] buffer = new byte[1024];
                int read;

                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }

                imageViewMoneda.setImageURI(selectedImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return imageName;
        } else {
            Log.e("FragmentContextError", "El contexto es nulo");
        }

        return null;
    }

    private String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = requireContext().getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (columnIndex != -1) {
                        result = cursor.getString(columnIndex);
                    }                }
            }
        }
        if (result == null) {
            // Si DISPLAY_NAME no está disponible, extraer el nombre del archivo de la ruta
            result = new File(uri.getPath()).getName();
        }
        return result;
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

                // If a new image is selected, update the imageUri
                if (mImageUri != null) {
                    imageUri = copiarImagenLocal(mImageUri);
                }
                // If no new image is selected, retain the previous image
                else {
                    imageUri = monedaAEditar.getImagen();
                }

                monedaAEditar.setImagen(imageUri);

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
        imageViewMoneda.setImageURI(Uri.fromFile(new File(requireContext().getDir("imagenes", Context.MODE_PRIVATE), monedaAEditar.getImagen())));
    }

    private void addMoneda() {

        boolean result = getFormData();

        if (result) {
            String imageUri = mImageUri != null ? copiarImagenLocal(mImageUri) : "monedaDefault.png";
            Moneda moneda = new Moneda(
                    this.nombreMonedaString,
                    this.precioMonedaString,
                    this.paisMonedaString,
                    this.fechaMonedaString,
                    this.materialMonedaString,
                    imageUri
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