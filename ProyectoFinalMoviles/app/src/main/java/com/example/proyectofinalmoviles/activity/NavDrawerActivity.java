package com.example.proyectofinalmoviles.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.proyectofinalmoviles.R;

public class NavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        this.userPrivileges();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer.openDrawer(GravityCompat.START);
    }

    private void userPrivileges() {
        //getting logged user
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_user_key), Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.preference_user_key_default);
        String privilegio = prefs.getString(getString(R.string.preference_user_key), defaultValue);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem holder;
        //using privileges to lock data
        switch (privilegio) {
            case "Administrador":
                holder = menu.findItem(R.id.nav_consultaHistorial);
                holder.setEnabled(true);
                holder = menu.findItem(R.id.nav_vuelo);
                holder.setEnabled(true);
                holder = menu.findItem(R.id.nav_avion);
                holder.setEnabled(true);
                holder = menu.findItem(R.id.nav_aeropuerto);
                holder.setEnabled(true);
                holder = menu.findItem(R.id.nav_pasajero);
                holder.setEnabled(true);
                holder = menu.findItem(R.id.nav_empleado);
                holder.setEnabled(true);
                holder = menu.findItem(R.id.nav_escala);
                holder.setEnabled(true);
                holder = menu.findItem(R.id.nav_generarRuta);
                holder.setEnabled(true);
                break;

            case "Cliente":
                holder = menu.findItem(R.id.nav_compraTiquete);
                holder.setEnabled(true);
                holder = menu.findItem(R.id.nav_reservaTiquete);
                holder.setEnabled(true);
                holder = menu.findItem(R.id.nav_check_In);
                holder.setEnabled(true);
                holder = menu.findItem(R.id.nav_consultaHistorial);
                holder.setEnabled(true);

                break;
            default:    //if is none
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            this.moveTaskToBack(true);
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //getting logged user
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_user_key), Context.MODE_PRIVATE);
        String defaultValue = getResources().getString(R.string.preference_user_key_default);
        String privilegio = prefs.getString(getString(R.string.preference_user_key), defaultValue);

        if (id == R.id.nav_consultaHistorial) {
            Toast.makeText(getApplicationContext(), "Consulta de Historial", Toast.LENGTH_SHORT).show();
            if (privilegio.equals("administrador"))
                abrirAdmHistorial();
            else
                abrirHistorial();
        } else if (id == R.id.nav_aeropuerto) {
            Toast.makeText(getApplicationContext(), "Aeropuerto", Toast.LENGTH_SHORT).show();
            abrirAdmAeropuerto();
        }  else if (id == R.id.nav_avion) {
            Toast.makeText(getApplicationContext(), "Avión", Toast.LENGTH_SHORT).show();
            abrirAdmAvion();
        } else if (id == R.id.nav_empleado) {
            Toast.makeText(getApplicationContext(), "Empleado", Toast.LENGTH_SHORT).show();
            abrirAdmEmpleado();
        }  else if (id == R.id.nav_logout) {
            Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_SHORT).show();
            abrirLogin();
        }
        else if (id == R.id.nav_escala) {
            Toast.makeText(getApplicationContext(), "Escala", Toast.LENGTH_SHORT).show();
            abrirAdmEscala();
        }
        else if (id == R.id.nav_pasajero) {
            Toast.makeText(getApplicationContext(), "Pasajero", Toast.LENGTH_SHORT).show();
            abrirAdmPasajero();
        }
        else if (id == R.id.nav_vuelo) {
            Toast.makeText(getApplicationContext(), "Vuelo", Toast.LENGTH_SHORT).show();
            abrirAdmVuelo();
        }
        else if (id == R.id.nav_check_In) {
            Toast.makeText(getApplicationContext(), "Check-in", Toast.LENGTH_SHORT).show();
            abrirCheckIn();
        }
        else if (id == R.id.nav_reservaTiquete) {
            Toast.makeText(getApplicationContext(), "Reserva de tiquete", Toast.LENGTH_SHORT).show();
            abrirReservaTiquetes();
        }
        else if (id == R.id.nav_compraTiquete) {
            Toast.makeText(getApplicationContext(), "Compra de tiquete", Toast.LENGTH_SHORT).show();
            abrirCompraTiquetes();
        }
        else if (id == R.id.nav_generarRuta) {
            Toast.makeText(getApplicationContext(), "Generar ruta", Toast.LENGTH_SHORT).show();
            abrirGenerarRuta();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void abrirLogin() {
        finish();
        Intent a = new Intent(this, LoginActivity.class);
        startActivity(a);
    }

    public void abrirAdmEscala() {
        Intent intent = new Intent(this, AdmEscalaActivity.class);
        startActivity(intent);
    }

    public void abrirAdmPasajero() {
        Intent intent = new Intent(this, AdmPasajeroActivity.class);
        startActivity(intent);
    }

    public void abrirAdmEmpleado() {
      Intent intent = new Intent(this, AdmEmpleadoActivity.class);
        startActivity(intent);
    }

    public void abrirAdmAvion() {
        Intent intent = new Intent(this, AdmAvionActivity.class);
        startActivity(intent);
    }

    public void abrirAdmVuelo() {
        Intent intent = new Intent(this, AdmVueloActivity.class);
        startActivity(intent);
    }

    public void abrirAdmAeropuerto() {
        Intent intent = new Intent(this, AdmAeropuertoActivity.class);
        startActivity(intent);
    }


    public void abrirCompraTiquetes() {
        Intent intent = new Intent(this, CompraTiqueteActivity.class);
        startActivity(intent) ;
    }
    public void abrirReservaTiquetes() {
        Intent intent = new Intent(this, ReservaTiqueteActivity.class);
        startActivity(intent);
    }

    public void abrirCheckIn() {
          Intent intent = new Intent(this, Check_in_Activity.class);
        startActivity(intent) ;
    }
    public void abrirGenerarRuta() {
         Intent intent = new Intent(this, GenerarRutasActivity.class);
        startActivity(intent);
    }


    public void abrirHistorial() {
        Intent intent = new Intent(this,ShowHistorialComprasActivity.class);
        startActivity(intent);
    }


    public void abrirAdmHistorial() {
        Intent intent = new Intent(this, HistorialComprasActivity.class);
        intent.putExtra("from", "historial");
        startActivity(intent);
        finish();
    }

}
