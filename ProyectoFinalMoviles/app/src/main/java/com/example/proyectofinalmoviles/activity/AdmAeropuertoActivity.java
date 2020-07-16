package com.example.proyectofinalmoviles.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinalmoviles.R;
import com.example.proyectofinalmoviles.adapter.AeropuertoAdapter;
import com.example.proyectofinalmoviles.logicaNegocio.Aeropuerto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class AdmAeropuertoActivity extends AppCompatActivity {


    private Button btnAdd;
    private Button btnUpd;
    private Button btnDel;
    private Button btnFind;
    private boolean editable = true;
    private EditText codigo;
    private EditText nombre;
    private EditText telefono;
    private EditText email;
    private EditText direccion;
    private EditText codigoB;
    private EditText nombreB;
    private EditText codigoE;
    private RecyclerView rv;
    private Context context;
    private CardView cardview;
    private TextView textview;
    private LinearLayout.LayoutParams layoutparams;
    private Button btnList;
    private List<Aeropuerto> aeropuertoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_adm_aeropuerto);
        context = getApplicationContext();

        LinearLayoutManager llm = new LinearLayoutManager(context);


        rv = (RecyclerView) findViewById(R.id.rvAeropuerto);

        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

         rv.addItemDecoration(new DividerItemDecoration (this,
                DividerItemDecoration.HORIZONTAL));
       // rv.addItemDecoration(new DividerItemDecoration(this,
             //   DividerItemDecoration.VERTICAL));

        aeropuertoList = new ArrayList<Aeropuerto>();
        btnAdd = findViewById(R.id.btnAddAeropuerto);
        btnUpd = findViewById(R.id.btnUpdAeropuerto);

        btnDel = findViewById(R.id.btnEliminar);
        btnFind = findViewById(R.id.btnBuscar);
        btnList = findViewById(R.id.btnListadoAeropuerto);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAeropuerto();
            }
        });

        btnUpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAeropuerto();
            }
        });


        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAeropuerto();
            }
        });


        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchAeropuerto();
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toListAeropuertos();
            }
        });


        codigo = findViewById(R.id.codigoAddUptAeropuerto);
        nombre = findViewById(R.id.nombreAddUpdAeropuerto);
        telefono = findViewById(R.id.telefonoAddUptAeropuerto);
        email = findViewById(R.id.correoAddUptAeropuerto);
        direccion = findViewById(R.id.direccionAddUpdAeropuerto);

        textview = new TextView(getApplicationContext());
        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        textview.setTextColor(Color.BLUE);
        textview.setPadding(25, 25, 25, 25);
        textview.setGravity(Gravity.CENTER);
        cardview = (CardView) findViewById(R.id.cv);
        cardview.addView(textview);


    }

    public void addAeropuerto() {



      if(validarFormulario()){


        final Aeropuerto aeropuerto = new Aeropuerto(codigo.getText().toString(), nombre.getText().toString(), telefono.getText().toString(), email.getText().toString(), direccion.getText().toString());

          char[] cadena=aeropuerto.getDirección ().toCharArray ();
          for(int i=0;i<cadena.length;i++){
              if(cadena[i]==' '){
                  cadena[i]='+';
              }
          }

          aeropuerto.setDirección (new String(cadena));

          char[] cadena2=aeropuerto.getNombre ().toCharArray ();
          for(int i=0;i<cadena2.length;i++){
              if(cadena2[i]==' '){
                  cadena2[i]='+';
              }
          }

          aeropuerto.setNombre (new String(cadena2));




          final String msj[]={""};

          new AsyncTask<String, Void, Void> () {

              @Override
              protected Void doInBackground(String... params) {

                  try {

                      URL url = new URL ( "http://10.0.2.2:8081/BackEndProyectoFinalMoviles/ProyectoServlet?"+"nomb="+aeropuerto.getNombre ()+
                              "&codigo="+aeropuerto.getCódigo ()+ "&tel="+aeropuerto.getTeléfono () + "&dir="+aeropuerto.getDirección ()+ "&correo="+aeropuerto.getCorreo ()+"&accion="+"insertarA");

                      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection ();

                      urlConnection.setRequestMethod ( "GET" );
                      urlConnection.connect ();
                      BufferedReader br = new BufferedReader ( new InputStreamReader ( urlConnection.getInputStream () ) );
                      String result =    br.readLine ();
                      msj[0] =result;


                       Log.d ( "Mensaje servlet:", msj[0]+" " );

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




          codigo.setText("");
          nombre.setText("");
          telefono.setText("");
          email.setText("");
          direccion.setText("");
    }}


    public void updateAeropuerto() {



         boolean encontrado=false;
         if(validarFormulario()){



           final Aeropuerto aeropuerto = new Aeropuerto(codigo.getText().toString(), nombre.getText().toString(), telefono.getText().toString(), email.getText().toString(), direccion.getText().toString());


             final String msj[]={""};

             new AsyncTask<String, Void, Void> () {

                 @Override
                 protected Void doInBackground(String... params) {

                     try {

                         URL url = new URL ( "http://10.0.2.2:8081/BackEndProyectoFinalMoviles/ProyectoServlet?"+"nomb="+aeropuerto.getNombre ()+
                                 "&codigo="+aeropuerto.getCódigo ()+ "&tel="+aeropuerto.getTeléfono () + "&dir="+aeropuerto.getDirección ()+ "&correo="+aeropuerto.getCorreo ()+"&accion="+"modificarA");

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



             codigo.setText("");
             nombre.setText("");
             telefono.setText("");
             email.setText("");
             direccion.setText("");
             //codigo.setEnabled(false);


        }}

    public void deleteAeropuerto() {
        boolean eliminado = false;
        codigoE = findViewById(R.id.codigoAddUpdAvion);

        if(validarFormularioEliminar()) {


            final String cod = codigoE.getText ().toString ();
            final String msj[] = {""};
            new AsyncTask<String, Void, Void> () {

                @Override
                protected Void doInBackground(String... params) {

                    try {

                        URL url = new URL ( "http://10.0.2.2:8081/BackEndProyectoFinalMoviles/ProyectoServlet?" + "codigoE=" + cod + "&accion=" + "eliminarA" );

                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection ();

                        urlConnection.setRequestMethod ( "GET" );
                        urlConnection.connect ();
                        BufferedReader br = new BufferedReader ( new InputStreamReader ( urlConnection.getInputStream () ) );
                        String result = br.readLine ();
                        msj[0] = result;
                        Log.d ( "Mensaje servlet:", result + " " );

                    } catch (Exception e) {
                        e.printStackTrace ();
                        System.out.println ( "ERROR IN URL CONNECTION---" + e );
                    }


                    //  Looper.loop();
                    return null;
                }

                @Override
                protected void onPostExecute(Void param) {
                    Toast.makeText ( getApplicationContext (), msj[0], Toast.LENGTH_LONG ).show ();
                }
            }.execute ();


        }

        codigoE .setText("");

    }

    public void searchAeropuerto() {
        boolean encontrado = false;
        codigoB = findViewById(R.id.codigoBuscarAeropuerto);
        nombreB = findViewById(R.id.nombreBuscarAeropuerto);

        if(validarFormularioBuscar()) {

            final String codigo = codigoB.getText ().toString ();
            final String nombre1 = nombreB.getText ().toString ();

            char[] cadena=nombre1.toCharArray ();
            for(int i=0;i<cadena.length;i++){
                 if(cadena[i]==' '){
                     cadena[i]='+';
                 }
            }

           final String nombre= new String(cadena);
            final String msj[] = {""};

            new AsyncTask<String, Void, Void> () {

                @Override
                protected Void doInBackground(String... params) {

                    try {

                        URL url = new URL ( "http://10.0.2.2:8081/BackEndProyectoFinalMoviles/ProyectoServlet?" + "codigoB=" + codigo + "&nombreB=" + nombre +
                                "&accion=" + "buscarA" );

                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection ();

                        urlConnection.setRequestMethod ( "GET" );
                        urlConnection.connect ();
                        BufferedReader br = new BufferedReader ( new InputStreamReader ( urlConnection.getInputStream () ) );
                        String result = br.readLine ();
                        msj[0] = result;
                        Log.d ( "Mensaje servlet:", result + "" );

                    } catch (Exception e) {
                        e.printStackTrace ();
                        System.out.println ( "ERROR IN URL CONNECTION---" + e );
                    }


                    //  Looper.loop();
                    return null;
                }

                @Override
                protected void onPostExecute(Void param) {
                    if (!msj[0].equals ( "NOT" )) {
                        Log.d ( "Mensaje servlet:", "Si encontrado" + "" );

                        Gson gson2 = new Gson ();
                        Aeropuerto persona = (Aeropuerto) gson2.fromJson ( msj[0], Aeropuerto.class );
                        msj[0] = persona.toString ();
                        textview.setText ( msj[0] );
                    } else {
                        Log.d ( "Mensaje servlet:", "No encontrado" + "" );
                        textview.setText ( "Aeropuerto no encontrado" );
                    }


                }
            }.execute ();


            codigoB.setText ( "" );
            nombreB.setText ( "" );
        }
    }

    public void toListAeropuertos() {
        final String msj[]={""};

        new AsyncTask<String, Void, Void> () {

            @Override
            protected Void doInBackground(String... params) {

                try {

                    URL url = new URL ( "http://10.0.2.2:8081/BackEndProyectoFinalMoviles/ProyectoServlet?"+
                            "&accion="+"listarA");

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection ();

                    urlConnection.setRequestMethod ( "GET" );
                    urlConnection.connect ();
                    BufferedReader br = new BufferedReader ( new InputStreamReader ( urlConnection.getInputStream () ) );
                    String result = br.readLine ();
                    Gson gson2 = new Gson();
                    Type typeMyType = new TypeToken<ArrayList<Aeropuerto>> (){}.getType();

                    aeropuertoList = gson2.fromJson(result, typeMyType);
                    msj[0] =aeropuertoList.size ()+"";
                    Log.d ( "Mensaje servlet:", msj[0] );

                } catch (Exception e) {
                    e.printStackTrace ();
                    System.out.println ( "ERROR IN URL CONNECTION---" + e );
                }


                //  Looper.loop();
                return null;
            }

            @Override
            protected void onPostExecute(Void param) {
                Log.d ( "Mensaje lista:", aeropuertoList.size ()+"" );


                AeropuertoAdapter adapter = new AeropuertoAdapter(aeropuertoList);
                rv.setAdapter(adapter);

            }
        }.execute ();




    }

    public boolean validarFormulario() {
        int error = 0;
        if (TextUtils.isEmpty(this.codigo.getText())) {
            codigo.setError("Código requerido");
            error++;
        }
        if (TextUtils.isEmpty(this.nombre.getText())) {
            nombre.setError("nombre requerido");
            error++;
        }
        if (TextUtils.isEmpty(this.telefono.getText())) {
            telefono.setError("Teléfono requerido");
            error++;
        }
        if (TextUtils.isEmpty(this.direccion.getText())) {
            direccion.setError("Dirección requerida");
            error++;
        }

        if (TextUtils.isEmpty(this.email.getText())) {
            email.setError("Correo requerido");
            error++;
        }
        if (this.email.getText().charAt (0)!='@') {
            email.setError("Correo inválido");
            error++;
        }
        if (error > 0) {
            Toast.makeText(getApplicationContext(), "Algunos errores", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean validarFormularioBuscar(){
        int error=0;
        if (TextUtils.isEmpty(this.codigoB.getText())) {
            codigoB.setError("Código requerido");
            error++;
        }

        if (TextUtils.isEmpty(this.nombreB.getText())) {
            nombreB.setError("Nombre requerido");
            error++;
        }
        if (error > 0) {
            Toast.makeText(getApplicationContext(), "Algunos errores", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean validarFormularioEliminar(){
            int error=0;
        if (TextUtils.isEmpty(this.codigoE.getText())) {
            codigoE.setError("Código requerido");
            error++;
        }

        if (error > 0) {
            Toast.makeText(getApplicationContext(), "Algunos errores", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}