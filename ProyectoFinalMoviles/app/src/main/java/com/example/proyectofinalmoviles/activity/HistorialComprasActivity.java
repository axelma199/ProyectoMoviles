package com.example.proyectofinalmoviles.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.proyectofinalmoviles.R;

public class HistorialComprasActivity extends AppCompatActivity {

    private EditText cedulaH;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_compras);



      //  btn = findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToShowHistorialAcad();
            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent a = new Intent(this, NavDrawerActivity.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(a);
        super.onBackPressed();
    }


    private void goToShowHistorialAcad() {
       // cedulaH = findViewById(R.id.editText2);

        Intent intent = new Intent(this, ShowHistorialComprasActivity.class);
        intent.putExtra("editable", false);
        String ced= cedulaH.getText().toString();
        intent.putExtra("cedula",ced);
        startActivity(intent);
    }

}
