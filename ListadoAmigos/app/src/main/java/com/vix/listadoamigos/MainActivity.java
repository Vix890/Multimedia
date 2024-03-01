package com.vix.listadoamigos;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView ListViewContact;
    List<Contacto> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListViewContact = findViewById(R.id.ListViewContacto);

        CustomAdapter adapter = new CustomAdapter(this, getData());
        ListViewContact.setAdapter(adapter);
        ListViewContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contacto contact = list.get(i);
                Toast.makeText(getBaseContext(), contact.getNombre(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<Contacto> getData() {
        list = new ArrayList<>();

        list.add(new Contacto(1, R.mipmap.poopemoji, "Delegao", "El delegao"));
        list.add(new Contacto(2, R.mipmap.download, "Jona", "Cat lover"));
        list.add(new Contacto(3, R.mipmap.amigo, "Hugo", "Llega tarde"));
        list.add(new Contacto(4, R.mipmap.amigo, "Sergio", "Compartir es vivir"));
        list.add(new Contacto(5, R.mipmap.amigo, "Mauri", "Call of dutty"));
        list.add(new Contacto(6, R.mipmap.amigo, "Jc", "Chat GPT"));
        return list;
    }
}