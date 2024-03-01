package com.vix.actividadesconvariables;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText)findViewById(R.id.et1);
    }

    public void Salir(View view) {
        Toast toast = Toast.makeText(getApplicationContext(), "Saliendo", Toast.LENGTH_SHORT);
        toast.show();

        // this.finish();
        finishAffinity();
        System.exit(0);
    }

    public void Enviar(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("data", et1.getText().toString());
        startActivity(intent);

        Toast toast = Toast.makeText(getApplicationContext(), "Enviando datos", Toast.LENGTH_SHORT);
        toast.show();
    }
}