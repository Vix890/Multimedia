package com.vix.primeraclculadora;

import static java.lang.Float.parseFloat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Calcular extends AppCompatActivity {

    private String num1;

    private String num2;

    private int resultadoSumaInt;
    private int resultadoRestaInt;
    private int resultadoMultiInt;
    private int resultadoDivInt;

    private TextView suma;
    private TextView resta;
    private TextView div;
    private TextView multi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcular);

        suma = (TextView) findViewById(R.id.suma);
        resta = (TextView) findViewById(R.id.resta);
        multi = (TextView) findViewById(R.id.multi);
        div = (TextView) findViewById(R.id.div);

        num1 = getIntent().getStringExtra("num1");
        num2 = getIntent().getStringExtra("num2");

        calcular(num1, num2);
    }

    @SuppressLint("SetTextI18n")
    private void calcular(String num1, String num2) {

        double n1;
        n1 = parseFloat(num1);
        double n2;
        n2 = parseFloat(num2);

        double resultadoSuma = n1 + n2;
        double resultadoResta = n1 - n2;
        double resultadoMulti = n1 * n2;
        double resultadoDiv = 0;

        try {

            if (n2 != 0.0) {
                resultadoDiv = n1 / n2;
            } else {
                div.setText("No se puede dividir entre 0");
            }

            if ((resultadoSuma % 1) == 0)
               resultadoSumaInt = (int) resultadoSuma;

            if ((resultadoResta % 1) == 0)
                resultadoRestaInt = (int) resultadoResta;

            if ((resultadoMulti % 1) == 0)
                resultadoMultiInt = (int) resultadoMulti;

            if ((resultadoDiv % 1) == 0)
                resultadoDivInt = (int) resultadoDiv;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            if (resultadoSumaInt != 0) {
                suma.setText(num1 + " + " + num2 + " = " + resultadoSumaInt);
            } else {
                suma.setText(num1 + " + " + num2 + " = " + resultadoSuma);
            }
            if (resultadoRestaInt != 0) {
                resta.setText(num1 + " - " + num2 + " = " + resultadoRestaInt);
            } else {
                resta.setText(num1 + " - " + num2 + " = " + resultadoResta);
            }
            if (resultadoMultiInt != 0) {
                multi.setText(num1 + " * " + num2 + " = " + resultadoMultiInt);
            } else {
                multi.setText(num1 + " * " + num2 + " = " + resultadoMulti);
            }
            if (resultadoDivInt != 0) {
                div.setText(num1 + " / " + num2 + " = " + resultadoDivInt);
            } else {
                div.setText(num1 + " / " + num2 + " = " + resultadoDiv);
            }
        }
    }

    public void volver(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(intent);
    }
}