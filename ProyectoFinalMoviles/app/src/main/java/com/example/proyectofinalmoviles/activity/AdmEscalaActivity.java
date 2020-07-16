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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinalmoviles.R;
import com.example.proyectofinalmoviles.adapter.EscalaAdapter;
import com.example.proyectofinalmoviles.logicaNegocio.Escala;

import java.util.ArrayList;
import java.util.List;

public class AdmEscalaActivity extends AppCompatActivity {

    private Spinner wifi, spinner2;

    private Button btnAdd;
    private Button btnUpd;
    private Button btnDel;
    private Button btnFind;
    private boolean editable = true;

    private EditText codigo;
    private EditText duracion;
    private EditText aeropuerto;
    private EditText horaLlegada;
    private EditText horaSalida;

    private EditText codigoB;

    private EditText codigoE;
    private RecyclerView rv;

    private Context context;
    private CardView cardview;
    private TextView textview;
    private LinearLayout.LayoutParams layoutparams;
    private Button btnList;
    private List<Escala> escalaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_adm_escala);
        context = getApplicationContext();

        LinearLayoutManager llm = new LinearLayoutManager(context);


        rv = (RecyclerView)findViewById(R.id.rvEscala);

        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);



         escalaList= new ArrayList<>();

         btnAdd = findViewById(R.id.btnAddEscala);
        btnUpd = findViewById(R.id.btnUpdEscala);

        btnDel = findViewById(R.id.btnEliminar);
        btnFind = findViewById(R.id.btnBuscar);
        btnList= findViewById(R.id.btnListadoEscala);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEscala();
            }
        });

        btnUpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEscala();
            }
        });


        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEscala();
            }
        });


        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEscala();
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toListEscalas();
            }
        });

        codigo=  findViewById(R.id.codigoAddUptEscala);
        duracion= findViewById(R.id.duracionAddUptEscala);
        aeropuerto= findViewById(R.id.aeropuertoAddUptEscala);
        horaLlegada=findViewById(R.id.horaLlegadaAddUptEscala);
        horaSalida=findViewById(R.id.horaSalidaAddUptEscala);

        editable = true;



        textview = new TextView (getApplicationContext());
        textview.setTextSize (TypedValue.COMPLEX_UNIT_DIP, 25);
        textview.setTextColor (Color.BLUE);
        textview.setPadding (25,25,25,25);
        textview.setGravity (Gravity.CENTER);
        cardview = (CardView) findViewById(R.id.cv);
        cardview.addView (textview);



    }


    public void addEscala(){

        if( validarFormulario()){


            Escala escala= new Escala (codigo.getText().toString(),Integer.parseInt(duracion.getText().toString()),aeropuerto.getText().toString(),(horaLlegada.getText().toString()),(horaSalida.getText().toString()));
            escalaList.add(escala);

            Toast.makeText(getApplicationContext(), "Avión agregado correctamente", Toast.LENGTH_LONG).show();

            codigo.setText("");
            duracion.setText("");
            aeropuerto.setText("");
            horaSalida.setText("");
            horaLlegada.setText("");


        }}


    public void updateEscala(){
        boolean encontrado=false;


        if( validarFormulario()){



            Escala aux= new Escala (codigo.getText().toString(),Integer.parseInt(duracion.getText().toString()),aeropuerto.getText().toString(),(horaLlegada.getText().toString()),(horaSalida.getText().toString()));
            for (Escala escala : escalaList) {
                if (escala.getCódigo().equals(aux.getCódigo())) {
                    escala.setDuración(aux.getDuración());
                    escala.setAeropuerto(aux.getAeropuerto());
                    escala.setHoraLlegada(aux.getHoraLlegada());
                    escala.setHoraSalida(aux.getHoraSalida());
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
            duracion.setText("");
            aeropuerto.setText("");
            horaSalida.setText("");
            horaLlegada.setText("");
         }

    }

    public void deleteEscala(){
        boolean eliminado=false;
        codigoE = findViewById(R.id.codigoAddUpdAvion);

        for (int i = 0; i < escalaList.size(); i++) {

            if (escalaList.get(i).getCódigo().contentEquals(codigoE.getText().toString()))
            {            escalaList.remove(escalaList.get(i));
                Toast.makeText(getApplicationContext(), "Escala eliminada correctamente", Toast.LENGTH_LONG).show();
                eliminado=true;
            }
        }

        if(!eliminado)
            Toast.makeText(getApplicationContext(), "Error al eliminar escala", Toast.LENGTH_LONG).show();

        codigoE.setText("");

    }

    public void searchEscala(){
        boolean encontrado=false;
        codigoB = findViewById(R.id.codigoBuscarEscala);

        for (int i = 0; i < escalaList.size(); i++) {

            if (escalaList.get(i).getCódigo().contentEquals(codigoB.getText().toString())) {

                textview.setText ((escalaList.get(i).toString()));
                encontrado=true;
            }
        }
        if(!encontrado)
            Toast.makeText(getApplicationContext(), "Escala no encontrada", Toast.LENGTH_LONG).show();

        codigoB.setText("");

    }

    public void toListEscalas(){
        EscalaAdapter adapter = new EscalaAdapter(escalaList);
        rv.setAdapter(adapter);

    }

    public boolean validarFormulario() {
        int error = 0;
        if (TextUtils.isEmpty(this.codigo.getText())) {
            codigo.setError("Código requerido");
            error++;
        }
        if (TextUtils.isEmpty(this.duracion.getText())) {
            duracion.setError("Duración requerida");

            error++;
        }
        if (TextUtils.isEmpty(this.aeropuerto.getText())) {
            aeropuerto.setError("Aeropuerto requerido");

            error++;
        }
        if (TextUtils.isEmpty(this.horaLlegada.getText())) {
            horaLlegada.setError("Capacidad de tripulantes requerida");

            error++;
        }

        if (TextUtils.isEmpty(this.horaSalida.getText())) {
            horaSalida.setError("Capacidad de pasajeros requerido");

            error++;
        }


        if (error > 0) {
            Toast.makeText(getApplicationContext(), "Algunos errores", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}


