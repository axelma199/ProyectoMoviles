package com.example.proyectofinalmoviles.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.example.proyectofinalmoviles.adapter.CustomOnItemSelectedListener;
import com.example.proyectofinalmoviles.adapter.VueloAdapter;
import com.example.proyectofinalmoviles.logicaNegocio.Avion;
import com.example.proyectofinalmoviles.logicaNegocio.Vuelo;

import java.util.ArrayList;
import java.util.List;

public class AdmVueloActivity extends AppCompatActivity {

    private Button btnAdd;
    private Button btnUpd;
    private Button btnDel;
    private Button btnFind;
    private boolean editable = true;
    private EditText codigo;
    private EditText origen;
    private EditText destino;
    private EditText horaSalida;
    private EditText horaLlegada;
    private EditText numPasajeros;
    private EditText numEscalas;
    private EditText duracion;
    private EditText avion;


    private EditText codigoB;
    private EditText origenB;
    private EditText destinoB;


    private EditText codigoE;
    private RecyclerView rv;
    private ArrayList<Vuelo> vueloList;

    private Context context;
    private CardView cardview;
    private TextView textview;
    private LinearLayout.LayoutParams layoutparams;
    private Button btnList;
    private boolean hayComida;
    private Spinner comida, spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_adm_vuelo);
        context = getApplicationContext();

        comida = findViewById(R.id.spinnerComida);

        spinner2 = findViewById(R.id.spinner2);
        LinearLayoutManager llm = new LinearLayoutManager(context);

        vueloList= new ArrayList<>();


        codigo=  findViewById(R.id.codigoAddUptVuelo);
        origen= findViewById(R.id.origenAddUptVuelo);
        destino= findViewById(R.id.destinoAddUptVuelo);
        horaSalida=findViewById(R.id.horaSalidaAddUptVuelo);
        horaLlegada=findViewById(R.id.horaLlegadaAddUptVuelo);
        numPasajeros=findViewById(R.id.numPasajerosAddUptVuelo);
        numEscalas=  findViewById(R.id.numEscalasAddUptVuelo);
         duracion=findViewById(R.id.duracionAddUptVuelo);
        avion=findViewById(R.id.avionAddUptVuelo);


        rv = (RecyclerView)findViewById(R.id.rvVuelo);

        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);


        btnAdd = findViewById(R.id.btnAddVuelo);
        btnUpd = findViewById(R.id.btnUpdVuelo);

        btnDel = findViewById(R.id.btnEliminar);
        btnFind = findViewById(R.id.btnBuscar);
        btnList= findViewById(R.id.btnListadoVuelo);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addVuelo();
            }
        });

        btnUpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               updateVuelo();
            }
        });


        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteVuelo();
            }
        });


        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   searchVuelo();
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toListVuelos();
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

        addListenerOnSpinnerItemSelection();


    }



    public Avion buscarAvion(String cod){
            Avion avion=null;


         return avion;


    }

    public void addVuelo(){

        if( validarFormulario()){

            if(String.valueOf(comida.getSelectedItem()).equals("Si")){
                hayComida=true;}
            else{
                hayComida=false;
            }

            Avion avionObj=buscarAvion(avion.getText().toString());
            Vuelo vuelo= new Vuelo (codigo.getText().toString(),origen.getText().toString(),destino.getText().toString(),
                     (horaSalida.getText().toString()),(horaLlegada.getText().toString()),
                    Integer.parseInt(numPasajeros.getText().toString()),   Integer.parseInt(numEscalas.getText().toString()),
                    hayComida,0,   Integer.parseInt(duracion.getText().toString()), avionObj,
                    "");


            vueloList.add(vuelo);

            Toast.makeText(getApplicationContext(), "Vuelo agregado correctamente", Toast.LENGTH_LONG).show();

            codigo.setText("");
            origen.setText("");
            destino.setText("");
            horaSalida.setText("");
            horaLlegada.setText("");
            numPasajeros.setText("");
            numEscalas.setText("");
            duracion.setText("");
            avion.setText("");


        }}


    public void updateVuelo(){
        boolean encontrado=false;


        if( validarFormulario()){



            if(String.valueOf(comida.getSelectedItem()).equals("Si")){
                hayComida=true;}
            else{
                hayComida=false;
            }


            Avion avionObj=buscarAvion(avion.getText().toString());
            Vuelo aux= new Vuelo (codigo.getText().toString(),origen.getText().toString(),destino.getText().toString(),
                    (horaSalida.getText().toString()),(horaLlegada.getText().toString()),
                    Integer.parseInt(numPasajeros.getText().toString()),   Integer.parseInt(numEscalas.getText().toString()),
                    hayComida,0,   Integer.parseInt(duracion.getText().toString()), avionObj,
                    "");

             for (Vuelo vuelo : vueloList) {
                if (vuelo.getCodigo().equals(aux.getCodigo())) {
                    vuelo.setOrigen(aux.getOrigen());
                    vuelo.setDestino(aux.getDestino());
                    vuelo.setDuración(aux.getDuración());
                    vuelo.setNumeroPasajeros(aux.getNumeroPasajeros());
                    vuelo.setNúmeroEscalas(aux.getNúmeroEscalas());
                    vuelo.setAvión(aux.getAvión());
                    vuelo.setComida(aux.isComida());
                    vuelo.setHoraLlegada(aux.getHoraLlegada());
                    vuelo.setHoraPartida(aux.getHoraPartida());
                     encontrado=true;
                    break;
                }
            }

            if(encontrado){
                Toast.makeText(getApplicationContext(), "Vuelo modificado correctamente", Toast.LENGTH_LONG).show();

            }
            else{
                Toast.makeText(getApplicationContext(), "Error al modificar vuelo ", Toast.LENGTH_LONG).show();


            }

            codigo.setText("");
            origen.setText("");
            destino.setText("");
            horaSalida.setText("");
            horaLlegada.setText("");
            numPasajeros.setText("");
            numEscalas.setText("");
            duracion.setText("");
            avion.setText("");
        }

    }

    public void deleteVuelo(){
        boolean eliminado=false;
        codigoE = findViewById(R.id.codigoAddUpdAvion);

        for (int i = 0; i < vueloList.size(); i++) {

            if (vueloList.get(i).getCodigo().contentEquals(codigoE.getText().toString()))
            {            vueloList.remove(vueloList.get(i));
                Toast.makeText(getApplicationContext(), "Vuelo eliminado correctamente", Toast.LENGTH_LONG).show();
                eliminado=true;
            }
        }

        if(!eliminado)
            Toast.makeText(getApplicationContext(), "Error al eliminar vuelo", Toast.LENGTH_LONG).show();

        codigoE.setText("");

    }

    public void searchVuelo(){
        boolean encontrado=false;
        codigoB = findViewById(R.id.codigoBuscarAvion);
        origenB= findViewById(R.id.origenBuscarVuelo);
        destinoB= findViewById(R.id.destinoBuscarVuelo);

        for (int i = 0; i < vueloList.size(); i++) {

            if (vueloList.get(i).getCodigo().contentEquals(codigoB.getText().toString())&&vueloList.get(i).getOrigen().contentEquals(origenB.getText().toString())&&vueloList.get(i).getDestino().contentEquals(destinoB.getText().toString())) {

                textview.setText ((vueloList.get(i).toString()));
                encontrado=true;
            }
        }
        if(!encontrado)
            Toast.makeText(getApplicationContext(), "Vuelo no encontrado", Toast.LENGTH_LONG).show();

        codigoB.setText("");
        origenB.setText("");
        destinoB.setText("");
    }

    public void toListVuelos(){
        VueloAdapter adapter = new VueloAdapter(vueloList);
        rv.setAdapter(adapter);

    }



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
         comida.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {



        Toast.makeText(getApplicationContext(), "Spinner 1 "+String.valueOf(comida.getSelectedItem()) +
                "Spinner 2 : "+ String.valueOf(spinner2.getSelectedItem()), Toast.LENGTH_LONG).show();


    }

    public boolean validarFormulario() {
        int error = 0;
        if (TextUtils.isEmpty(this.codigo.getText())) {
            codigo.setError("Código requerido");
            error++;
        }
        if (TextUtils.isEmpty(this.origen.getText())) {
            origen.setError("Origen requerido");

            error++;
        }
        if (TextUtils.isEmpty(this.destino.getText())) {
            destino.setError("Destino requerida");

            error++;
        }
        if (TextUtils.isEmpty(this.duracion.getText())) {
            duracion.setError("Duración requerida");

            error++;
        }

        if (TextUtils.isEmpty(this.horaSalida.getText())) {
            horaSalida.setError("Hora de salida requerida");

            error++;
        }

        if (TextUtils.isEmpty(this.horaLlegada.getText())) {
            horaLlegada.setError("Hora de llegada requerida");

            error++;
        }
        if (TextUtils.isEmpty(this.numEscalas.getText())) {
            numEscalas.setError("Número de escalas  requerido");
            error++;
        }
        if (TextUtils.isEmpty(this.numPasajeros.getText())) {
            numPasajeros.setError("Número de pasajeros requerido");
            error++;
        }
        if (TextUtils.isEmpty(this.avion.getText())) {
            avion.setError("Código de avión requerido");

            error++;
        }


        if (error > 0) {
            Toast.makeText(getApplicationContext(), "Algunos errores", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


}