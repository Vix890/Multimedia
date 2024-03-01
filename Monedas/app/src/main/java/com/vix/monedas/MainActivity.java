package com.vix.monedas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.vix.monedas.addeditmoneda.AddEditMonedaFragment;
import com.vix.monedas.data.Evento;
import com.vix.monedas.data.EventoDbHelper;
import com.vix.monedas.data.MonedasDbHelper;
import com.vix.monedas.eventos.EventosFragment;
import com.vix.monedas.monedas.MonedasFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        * menu toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        * menu lateral
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.inflateMenu(R.menu.nav_menu);
        navigationView.inflateHeaderView(R.layout.nav_header);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MonedasFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem addItem = menu.findItem(R.id.add_item_moneda);
        MenuItem editItem = menu.findItem(R.id.edit_item_moneda);
        MenuItem deleteItem = menu.findItem(R.id.delete_item_moneda);
        MenuItem homeItem = menu.findItem(R.id.home_item_moneda);

        addItem.setVisible(true);
        editItem.setVisible(false);
        deleteItem.setVisible(false);
        homeItem.setVisible(false);

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.add_item_moneda) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new AddEditMonedaFragment())
                    .addToBackStack(null)
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MonedasFragment()).commit();
        } else if (item.getItemId() == R.id.recuperar) {
            try (MonedasDbHelper dbHelper = new MonedasDbHelper(this);
                 EventoDbHelper eventoDbHelper = new EventoDbHelper(this);
                 ) {
                dbHelper.recuperarMonedasBorradas();

                Evento evento = new Evento(0, 1, 0, 0, "Recuperar monedas borradas");
                eventoDbHelper.saveEvento(evento);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MonedasFragment()).commit();
            }
        } else if (item.getItemId() == R.id.nav_eventos) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EventosFragment()).commit();
        } else if (item.getItemId() == R.id.nav_salir) {
            finishAffinity();
        }
        return false;
    }
}