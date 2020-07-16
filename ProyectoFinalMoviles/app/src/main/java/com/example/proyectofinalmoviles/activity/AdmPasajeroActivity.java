package com.example.proyectofinalmoviles.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinalmoviles.R;
import com.example.proyectofinalmoviles.adapter.CustomOnItemSelectedListener;
import com.example.proyectofinalmoviles.adapter.EmpleadoAdapter;
import com.example.proyectofinalmoviles.logicaNegocio.Usuario;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdmPasajeroActivity extends AppCompatActivity {
    private Button btnAdd;
    private Button btnUpd;
    private Button btnDel;
    private Button btnFind;
    private boolean editable = true;
    private EditText cedula;
    private EditText nombre;
    private EditText apellido1;
    private EditText apellido2;
    private EditText nacionalidad;
    private EditText perfil;
    private EditText correo;
    private EditText clave;
    private EditText telefono;
    private EditText direccion;
    private EditText estadoCivil;
    private EditText edad;
    private Spinner spinner1;


    private EditText codigoB;
    private EditText origenB;
    private EditText destinoB;


    private EditText codigoE;
    private RecyclerView rv;
    private List<Usuario> empleadosList;


    private static final String TAG = "MainActivity";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private TextView txtdate, txttime;
    private Button btntimepicker, btndatepicker;

    private  java.sql.Time timeValue;
    private SimpleDateFormat format;
    private Calendar c;
    private int year, month, day;

    private String fechaNac;

    private Context context;
    private CardView cardview;
    private TextView textview;
    private LinearLayout.LayoutParams layoutparams;
    private Button btnList;
    private boolean esEmpleado;
    private Spinner isempleado, spinner2;
    private EditText cedulaB;
    private EditText nombreB;
    private EditText apellido1B;
    private EditText apellido2B;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_adm_pasajero);
        context = getApplicationContext();

        LinearLayoutManager llm = new LinearLayoutManager(context);
        empleadosList= new ArrayList<>();


        addListenerOnSpinnerItemSelection();

        cedula=findViewById(R.id.cedula);
        nombre=findViewById(R.id.nombre);
        apellido1=findViewById(R.id.primerApellido);
        apellido2=findViewById(R.id.segundoApellido);
        nacionalidad=findViewById(R.id.nacionalidad);
        correo=findViewById(R.id.correo);
        clave=findViewById(R.id.clave);
        telefono= findViewById(R.id.telefono);
        direccion =findViewById(R.id.direccion);
        estadoCivil=findViewById(R.id.estadoCivil);
        edad=findViewById(R.id.edad);


        spinner1 = (Spinner) findViewById(R.id.spinnerPerfil);


        mDisplayDate = (TextView) findViewById(R.id.fechaNac);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AdmPasajeroActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                fechaNac = month + "/" + day + "/" + year;
                mDisplayDate.setText(fechaNac);
            }
        };


        rv = (RecyclerView)findViewById(R.id.rvPasajero);

        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        btnAdd = findViewById(R.id.btnAddPasajero);
        btnUpd = findViewById(R.id.btnUpPasajero);

        btnDel = findViewById(R.id.btnEliminar);
        btnFind = findViewById(R.id.btnBuscar);
        btnList= findViewById(R.id.btnListadoPasajeros);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addEmpleado();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        btnUpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    updateEmpleado();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEmpleado();
            }
        });


        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEmpleado();
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toListEmpleado();
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




    public void addEmpleado() throws ParseException {

        if( validarFormulario()){

            if(String.valueOf(isempleado.getSelectedItem()).equals("Empleado")){
                esEmpleado=true;}
            else{
                esEmpleado=false;
            }

            String fechaN=fechaNac;
            DateFormat format=new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date=format.parse(fechaN);
            java.sql.Date inputDate = new java.sql.Date(date.getTime());

            Usuario u= new Usuario (correo.getText().toString(),clave.getText().toString(),cedula.getText().toString(),perfil.getText().toString(),nombre.getText().toString(),
                    apellido1.getText().toString(),apellido2.getText().toString(),Integer.parseInt(telefono.getText().toString()),
                    direccion.getText().toString(),nacionalidad.getText().toString(),Integer.parseInt(edad.getText().toString()),inputDate,estadoCivil.getText().toString());


            empleadosList.add(u);

            Toast.makeText(getApplicationContext(), "Empleado agregado correctamente", Toast.LENGTH_LONG).show();

            cedula.setText("");
            nombre.setText("");
            apellido1.setText("");
            apellido2.setText("");
            nacionalidad.setText("");
            perfil.setText("");
            correo.setText("");
            clave.setText("");
            telefono.setText("");
            direccion .setText("");
            estadoCivil.setText("");
            edad.setText("");


        }}


    public void updateEmpleado() throws ParseException {
        boolean encontrado=false;


        if( validarFormulario()){



            if(String.valueOf(isempleado.getSelectedItem()).equals("Empleado")){
                esEmpleado=true;}
            else{
                esEmpleado=false;
            }


            String fechaN=fechaNac;
            DateFormat format=new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date=format.parse(fechaN);
            java.sql.Date inputDate = new java.sql.Date(date.getTime());

            Usuario aux= new Usuario (correo.getText().toString(),clave.getText().toString(),cedula.getText().toString(),perfil.getText().toString(),nombre.getText().toString(),
                    apellido1.getText().toString(),apellido2.getText().toString(),Integer.parseInt(telefono.getText().toString()),
                    direccion.getText().toString(),nacionalidad.getText().toString(),Integer.parseInt(edad.getText().toString()),inputDate,estadoCivil.getText().toString());

            for (Usuario u : empleadosList) {
                if (u.getCedula().equals(aux.getCedula())) {
                    u.setClave(aux.getClave());
                    u.setCorreo(aux.getCorreo());
                    u.setDirección(aux.getDirección());
                    u.setEdad(aux.getEdad());
                    u.setEstadoCivil(aux.getEstadoCivil());
                    u.setFechaNacimiento(aux.getFechaNacimiento());
                    u.setNacionalidad(aux.getNacionalidad());
                    u.setPerfil(aux.getPerfil());
                    u.setPrimerApellido(aux.getPrimerApellido());
                    u.setSegundoApellido(aux.getSegundoApellido());
                    u.setTeléfono(aux.getTeléfono());

                    encontrado=true;
                    break;
                }
            }

            if(encontrado){
                Toast.makeText(getApplicationContext(), "Empleado modificado correctamente", Toast.LENGTH_LONG).show();

            }
            else{
                Toast.makeText(getApplicationContext(), "Error al modificar empleado ", Toast.LENGTH_LONG).show();


            }

            cedula.setText("");
            nombre.setText("");
            apellido1.setText("");
            apellido2.setText("");
            nacionalidad.setText("");
            perfil.setText("");
            correo.setText("");
            clave.setText("");
            telefono.setText("");
            direccion .setText("");
            estadoCivil.setText("");
            edad.setText("");

        }

    }

    public void deleteEmpleado(){
        boolean eliminado=false;
        codigoE = findViewById(R.id.codigoAddUptAeropuerto);

        for (int i = 0; i < empleadosList.size(); i++) {

            if (empleadosList.get(i).getCedula().contentEquals(cedulaB.getText().toString())&&empleadosList.get(i).getPerfil().contentEquals("Empleado"))
            {            empleadosList.remove(empleadosList.get(i));
                Toast.makeText(getApplicationContext(), "Empleado  eliminado correctamente", Toast.LENGTH_LONG).show();
                eliminado=true;
            }
        }

        if(!eliminado)
            Toast.makeText(getApplicationContext(), "Error al eliminar empleado", Toast.LENGTH_LONG).show();

        codigoE.setText("");

    }

    public void searchEmpleado(){
        boolean encontrado=false;
        cedulaB = findViewById(R.id.cedulaBuscarEmpleado);
        nombreB= findViewById(R.id.nombreBuscarEmpleado);
        apellido1B=findViewById(R.id.ape1BuscarEmpleado);
        apellido2B=findViewById(R.id.ape2BuscarEmpleado);


        for (int i = 0; i < empleadosList.size(); i++) {

            if (empleadosList.get(i).getCedula().contentEquals(cedulaB.getText().toString())&&empleadosList.get(i).getNombre().contentEquals(nombreB.getText().toString())&&empleadosList.get(i).getPrimerApellido().contentEquals(apellido1.getText().toString())&&empleadosList.get(i).getSegundoApellido().contentEquals(apellido2.getText().toString())&&empleadosList.get(i).getPerfil().contentEquals("Empleado")){


                textview.setText ((empleadosList.get(i).toString()));
                encontrado=true;
            }
        }
        if(!encontrado)
            Toast.makeText(getApplicationContext(), "Empleado no encontrado", Toast.LENGTH_LONG).show();

        codigoB.setText("");
        origenB.setText("");
        destinoB.setText("");
    }

    public void toListEmpleado(){
        EmpleadoAdapter adapter = new EmpleadoAdapter(empleadosList);
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



    // get the selected dropdown list value
    public void addListenerOnButton() {

        isempleado = (Spinner) findViewById(R.id.spinnerWifi);
        spinner2 = (Spinner) findViewById(R.id.spinner2);


        Toast.makeText(getApplicationContext(), "Spinner 1 "+String.valueOf(isempleado.getSelectedItem()) +
                "Spinner 2 : "+ String.valueOf(spinner2.getSelectedItem()), Toast.LENGTH_LONG).show();


    }

    public boolean validarFormulario() {
        int error = 0;
        if (TextUtils.isEmpty(this.cedula.getText())) {
            cedula.setError("Cédula requerida");
            error++;
        }
        if (TextUtils.isEmpty(this.apellido1.getText())) {
            apellido1.setError("Apellido 1 requerido");

            error++;
        }
        if (TextUtils.isEmpty(this.apellido2.getText())) {
            apellido2.setError("Apellido 2 requerido");

            error++;
        }
        if (TextUtils.isEmpty(this.direccion.getText())) {
            direccion.setError("Dirección requerida");

            error++;
        }

        if (TextUtils.isEmpty(this.telefono.getText())) {
            telefono.setError("Teléfono requerido");

            error++;
        }

        if (TextUtils.isEmpty(this.correo.getText())) {
            correo.setError("Correo requerido");

            error++;
        }
        if (TextUtils.isEmpty(this.clave.getText())) {
            clave.setError("Clave  requerida");
            error++;
        }
        if (TextUtils.isEmpty(this.nacionalidad.getText())) {
            nacionalidad.setError("Nacionalidad requerida");
            error++;
        }
        if (TextUtils.isEmpty(this.edad.getText())) {
            edad.setError("Edad requerida");

            error++;
        }

        if (TextUtils.isEmpty(this.estadoCivil.getText())) {
            estadoCivil.setError("Estado civil requerido");

            error++;
        }  if (TextUtils.isEmpty(this.perfil.getText())) {
            perfil.setError("Perfil requerido");
            error++;
        }



        if (error > 0) {
            Toast.makeText(getApplicationContext(), "Algunos errores", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinnerPerfil);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

}