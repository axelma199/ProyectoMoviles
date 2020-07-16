package com.example.proyectofinalmoviles.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinalmoviles.R;
import com.example.proyectofinalmoviles.adapter.AvionAdapter;
import com.example.proyectofinalmoviles.adapter.CustomOnItemSelectedListener;
import com.example.proyectofinalmoviles.logicaNegocio.Avion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdmAvionActivity extends AppCompatActivity {

    private Spinner wifi, spinner2;

    private Button btnAdd;
    private Button btnUpd;
    private Button btnDel;
    private Button btnFind;
    private boolean editable = true;
    private EditText codigo;
    private EditText modelo;
    private EditText marca;
    private EditText capPasajeros;
    private EditText capTripulantes;
    private EditText asientos;

    private EditText codigoB;
    private EditText nombreB;

    private EditText codigoE;
    private RecyclerView rv;
    private ArrayList<Avion> AeropuertoList;
    private Context context;
    private CardView cardview;
    private TextView textview;
    private LinearLayout.LayoutParams layoutparams;
    private Button btnList;
    private List<Avion> avionList;
    private boolean hayWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_adm_avion);
        context = getApplicationContext();

        //addItemsOnSpinner2();
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();

        LinearLayoutManager llm = new LinearLayoutManager(context);


        rv = (RecyclerView)findViewById(R.id.rvAvion);

        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        codigo=  findViewById(R.id.codigoAddUptAvion);
        modelo= findViewById(R.id.modeloAddUptAvion);
        marca= findViewById(R.id.marcaAddUptAvion);
        capPasajeros=findViewById(R.id.capacidadPasajerosAddUptAvion);
        capTripulantes=findViewById(R.id.capacidadTripulantesAddUptAvion);
        asientos=findViewById(R.id.numeroAsientosAddUptAvion);

         avionList= new ArrayList<>();

         btnAdd = findViewById(R.id.btnAddAvion);
        btnUpd = findViewById(R.id.btnUpdAvion);

        btnDel = findViewById(R.id.btnEliminar);
        btnFind = findViewById(R.id.btnBuscar);
        btnList= findViewById(R.id.btnListadoAvion);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAvion();
            }
        });

        btnUpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAvion();
            }
        });


        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAvion();
            }
        });


        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchAvion();
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toListAviones();
            }
        });




        editable = true;



        textview = new TextView (getApplicationContext());
        textview.setTextSize (TypedValue.COMPLEX_UNIT_DIP, 25);
        textview.setTextColor (Color.BLUE);
        textview.setPadding (25,25,25,25);
        textview.setGravity (Gravity.CENTER);
        cardview = (CardView) findViewById(R.id.cv);
        cardview.addView (textview);





    }

    public void addAvion(){

        if( validarFormulario()){

            if(String.valueOf(wifi.getSelectedItem()).equals("Si")){
            hayWifi=true;}
        else{
            hayWifi=false;
        }

        Avion avion= new Avion (codigo.getText().toString(),modelo.getText().toString(),marca.getText().toString(),Integer.parseInt(capPasajeros.getText().toString()),Integer.parseInt(capTripulantes.getText().toString()),Integer.parseInt(asientos.getText().toString()),hayWifi);


             new AsyncTask<String, Void, Void> () {

                @Override
                protected Void doInBackground(String... params) {

                    try {

                        URL url = new URL ( "http://192.168.0.100:8081/LabConnection/LabServlet3?"+"key1="+2+"&key2="+3);

                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection ();

                        urlConnection.setRequestMethod ( "GET" );
                        urlConnection.connect ();
                        BufferedReader br = new BufferedReader ( new InputStreamReader ( urlConnection.getInputStream () ) );
                        String result = br.readLine ();
                        Log.d ( "Mariolos", result );

                    } catch (Exception e) {
                        e.printStackTrace ();
                        System.out.println ( "ERROR IN URL CONNECTION---" + e );
                    }


                    //  Looper.loop();
                    return null;
                }
            }.execute ();


        Toast.makeText(getApplicationContext(), "Avión agregado correctamente", Toast.LENGTH_LONG).show();

            codigo.setText("");
            modelo.setText("");
            marca.setText("");
            capPasajeros.setText("");
            capTripulantes.setText("");
            asientos.setText("");

}
        }


    public void updateAvion(){
        boolean encontrado=false;


       if( validarFormulario()){



 if(String.valueOf(wifi.getSelectedItem()).equals("Si")){
        hayWifi=true;}
 else{
    hayWifi=false;
}


        Avion aux= new Avion (codigo.getText().toString(),modelo.getText().toString(),marca.getText().toString(),Integer.parseInt(capPasajeros.getText().toString()),Integer.parseInt(capTripulantes.getText().toString()),Integer.parseInt(asientos.getText().toString()),hayWifi);
           for (Avion avion : avionList) {
               if (avion.getCódigo().equals(aux.getCódigo())) {
                   avion.setModelo(aux.getModelo());
                   avion.setMarca(aux.getMarca());
                   avion.setAsientos(aux.getAsientos());
                   avion.setCapacidadPasajeros(aux.getCapacidadPasajeros());
                   avion.setCapacidadTripulantes(aux.getCapacidadTripulantes());
                   encontrado=true;
                   break;
               }
           }

           if(encontrado){
               Toast.makeText(getApplicationContext(), "Avión modificado correctamente", Toast.LENGTH_LONG).show();

           }
           else{
               Toast.makeText(getApplicationContext(), "Error al modificar avión ", Toast.LENGTH_LONG).show();


           }

           codigo.setText("");
           modelo.setText("");
           marca.setText("");
           capPasajeros.setText("");
           capTripulantes.setText("");
           asientos.setText("");
       }

    }

    public void deleteAvion(){
        boolean eliminado=false;
        codigoE = findViewById(R.id.codigoAddUptAeropuerto);

        for (int i = 0; i < avionList.size(); i++) {

            if (avionList.get(i).getCódigo().contentEquals(codigoE.getText().toString()))
            {            avionList.remove(avionList.get(i));
                Toast.makeText(getApplicationContext(), "Avión eliminado correctamente", Toast.LENGTH_LONG).show();
                eliminado=true;
            }
        }

        if(!eliminado)
            Toast.makeText(getApplicationContext(), "Error al eliminar avión", Toast.LENGTH_LONG).show();

        codigoE.setText("");

    }

    public void searchAvion(){
        boolean encontrado=false;
        codigoB = findViewById(R.id.codigoBuscarAvion);

         for (int i = 0; i < avionList.size(); i++) {

            if (avionList.get(i).getCódigo().contentEquals(codigoB.getText().toString())) {

                textview.setText ((avionList.get(i).toString()));
                encontrado=true;
            }
        }
        if(!encontrado)
            Toast.makeText(getApplicationContext(), "Aeropuerto no encontrado", Toast.LENGTH_LONG).show();

        codigoB.setText("");

    }

    public void toListAviones(){
        AvionAdapter adapter = new AvionAdapter(avionList);
        rv.setAdapter(adapter);

    }

    public void validarDatos(){}



    // add items into spinner dynamically
    public void addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        list.add("list 1");
        list.add("list 2");
        list.add("list 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
        wifi = (Spinner) findViewById(R.id.spinnerWifi);
        wifi.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        wifi = (Spinner) findViewById(R.id.spinnerWifi);
        spinner2 = (Spinner) findViewById(R.id.spinner2);


        Toast.makeText(getApplicationContext(), "Spinner 1 "+String.valueOf(wifi.getSelectedItem()) +
                "Spinner 2 : "+ String.valueOf(spinner2.getSelectedItem()), Toast.LENGTH_LONG).show();


    }

    public boolean validarFormulario() {
        int error = 0;
        if (TextUtils.isEmpty(this.codigo.getText())) {
            codigo.setError("Código requerido");
            error++;
        }
        if (TextUtils.isEmpty(this.modelo.getText())) {
            modelo.setError("Modelo requerido");

            error++;
        }
        if (TextUtils.isEmpty(this.marca.getText())) {
            marca.setError("Marca requerida");

            error++;
        }
        if (TextUtils.isEmpty(this.capTripulantes.getText())) {
            capTripulantes.setError("Capacidad de tripulantes requerida");

            error++;
        }

        if (TextUtils.isEmpty(this.capPasajeros.getText())) {
            capPasajeros.setError("Capacidad de pasajeros requerido");

            error++;
        }

        if (TextUtils.isEmpty(this.asientos.getText())) {
            asientos.setError("Número de asientos  requerido");

            error++;
        }
        if (error > 0) {
            Toast.makeText(getApplicationContext(), "Algunos errores", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
