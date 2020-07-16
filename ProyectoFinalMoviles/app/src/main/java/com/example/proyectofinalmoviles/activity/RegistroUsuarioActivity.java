package com.example.proyectofinalmoviles.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinalmoviles.R;
import com.example.proyectofinalmoviles.adapter.CustomOnItemSelectedListener;
import com.example.proyectofinalmoviles.logicaNegocio.Usuario;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegistroUsuarioActivity extends AppCompatActivity {


    private Button btnReg;
    private boolean editable = true;
    private EditText nombre;
    private EditText primerApellido;
    private EditText segundoApellido;
    private EditText cedula;
    private EditText telefono;
    private EditText correo;
    private EditText nacionalidad;
     private EditText direccion;
    private EditText clave;
    private EditText edad;
    private EditText estadoCivil;
    private EditText perfil;

    private String fechaNac;
    private static final String TAG = "MainActivity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView txtdate, txttime;
    private Button btntimepicker, btndatepicker;
    private  java.sql.Time timeValue;
    private SimpleDateFormat format;
    private Calendar c;
    private int year, month, day;

    private Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);


        btnReg = findViewById(R.id.btnRegistarse);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addUsuario();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

         spinner1 = (Spinner) findViewById(R.id.spinnerPerfil);
        addListenerOnSpinnerItemSelection();
        mDisplayDate = (TextView) findViewById(R.id.fechaN);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RegistroUsuarioActivity.this,
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
    }

    public void goToLogin(){

        Intent intent = new Intent(RegistroUsuarioActivity.this, LoginActivity.class);
        RegistroUsuarioActivity.this.startActivity(intent);
    }
    public void addUsuario() throws ParseException {

        nombre = findViewById(R.id.nombre);
        primerApellido = findViewById(R.id.primerApellido);
        segundoApellido = findViewById(R.id.segundoApellido);
        cedula = findViewById(R.id.cedula);
        telefono = findViewById(R.id.telefono);
        correo = findViewById(R.id.correo);
        direccion = findViewById(R.id.direccion);
        nacionalidad = findViewById(R.id.nacionalidad);
        clave = findViewById(R.id.clave);
        estadoCivil = findViewById(R.id.estadoCivil);
        edad = findViewById(R.id.edad);


        if(validarFormulario()) {

            final String[] msj = {""};
            String fechaN = fechaNac;
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = format.parse(fechaN);
            java.sql.Date inputDate = new java.sql.Date(date.getTime());

            final Usuario u = new Usuario(correo.getText().toString(), clave.getText().toString(), cedula.getText().toString(), spinner1.getSelectedItem().toString(), nombre.getText().toString(),
                    primerApellido.getText().toString(), segundoApellido.getText().toString(), Integer.parseInt(telefono.getText().toString()),
                    direccion.getText().toString(), nacionalidad.getText().toString(), Integer.parseInt(edad.getText().toString()), inputDate, estadoCivil.getText().toString());


            char[] cadena2=u.getNombre ().toCharArray ();
            for(int i=0;i<cadena2.length;i++){
                if(cadena2[i]==' '){
                    cadena2[i]='+';
                }
            }

            u.setNombre (new String(cadena2));

            char[] cadena=u.getPrimerApellido ().toCharArray ();
            for(int i=0;i<cadena.length;i++){
                if(cadena[i]==' '){
                    cadena[i]='+';
                }
            }

            u.setDirección (new String(cadena));


            char[] cadena3=u.getSegundoApellido ().toCharArray ();
            for(int i=0;i<cadena3.length;i++){
                if(cadena3[i]==' '){
                    cadena3[i]='+';
                }
            }

            u.setSegundoApellido (new String(cadena3));

            new AsyncTask<String, Void, Void> () {

                @Override
                protected Void doInBackground(String... params) {

                    try {

                        URL url = new URL ( "http://10.0.2.2:8081/BackEndProyectoFinalMoviles/ProyectoServlet?"+"correo="+u.getCorreo ()+"&clave="+u.getClave ()+"&tel="+u.getTeléfono ()+"&dir="+u.getDirección ()+
                                "&nomb="+u.getNombre ()+"&ape1="+u.getPrimerApellido ()+"&ape2="+u.getSegundoApellido ()+"&perfil="+u.getPerfil ()+
                                "&fechaN="+u.getFechaNacimiento ()+"&estadoC="+u.getEstadoCivil ()+"&edad="+u.getEdad ()+"&cedula="+u.getCedula ()+
                                "&nacion="+u.getNacionalidad ()+"&accion="+"registroU");

                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection ();

                        urlConnection.setRequestMethod ( "GET" );
                        urlConnection.connect ();
                        BufferedReader br = new BufferedReader ( new InputStreamReader ( urlConnection.getInputStream () ) );
                        String result = br.readLine ();
                        msj[0] =result;
                        Log.d ( "Mensaje servlet:", result+" " );

                    } catch (Exception e) {
                        e.printStackTrace ();
                        System.out.println ( "ERROR IN URL CONNECTION---" + e );
                    }


                    //  Looper.loop();
                    return null;
                }

                @Override
                protected void onPostExecute(Void param) {
                    Toast.makeText(getApplicationContext(),msj[0], Toast.LENGTH_LONG).show();
                }
            }.execute ();


             goToLogin();
        }
    }




    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinnerPerfil);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }
/*
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

        isAdm = (Spinner) findViewById(R.id.spinnerWifi);
        spinner2 = (Spinner) findViewById(R.id.spinner2);


        Toast.makeText(getApplicationContext(), "Spinner 1 "+String.valueOf(isempleado.getSelectedItem()) +
                "Spinner 2 : "+ String.valueOf(spinner2.getSelectedItem()), Toast.LENGTH_LONG).show();


    }*/

    public boolean validarFormulario(){
        int error = 0;
        if (TextUtils.isEmpty(this.cedula.getText())) {
            cedula.setError("Cédula requerida");
            error++;
        }
        if (TextUtils.isEmpty(this.primerApellido.getText())) {
            primerApellido.setError("Apellido 1 requerido");

            error++;
        }
        if (TextUtils.isEmpty(this.segundoApellido.getText())) {
            segundoApellido.setError("Apellido 2 requerido");

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

        if (this.correo.getText().charAt (0)!='@') {
            clave.setError("Correo inválido");
            error++;
        }
        if (this.clave.getText().length ()<7) {
            clave.setError("Clave debe tener más caracteres");
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
            estadoCivil.setError ( "Estado civil requerido" );

            error++;
        }
        if (error > 0) {
            Toast.makeText(getApplicationContext(), "Algunos errores", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}