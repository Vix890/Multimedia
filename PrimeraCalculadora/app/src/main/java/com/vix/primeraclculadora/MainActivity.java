package com.vix.primeraclculadora;

import static java.lang.System.exit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText num1;
    private EditText num2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = (EditText) findViewById(R.id.num1);
        num2 = (EditText) findViewById(R.id.num2);
    }

    public void cambiarCalular(View view) {
        Intent intent = new Intent(this, Calcular.class);
        intent.putExtra("num1", num1.getText().toString());
        intent.putExtra("num2", num2.getText().toString());

        Toast toast = Toast.makeText(getApplicationContext(), "Enviando num1: " + num1.getText().toString(), Toast.LENGTH_LONG);
        toast.show();
        Toast toast1 = Toast.makeText(getApplicationContext(), "Enviando num2: " + num2.getText().toString(), Toast.LENGTH_LONG);
        toast1.show();

        startActivity(intent);
    }

    public void cambiarCreditos(View view) {
        Intent intent = new Intent(this, Creditos.class);

        Toast toast = Toast.makeText(getApplicationContext(), "Cargando créditos ", Toast.LENGTH_LONG);
        toast.show();

        startActivity(intent);
    }

    public void salir(View view) {
        finishAffinity();
        System.exit(1);
    }
}