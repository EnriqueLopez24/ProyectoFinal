package com.enrique.proyectofinal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.enrique.proyectofinal.db.DbContactos;
import com.enrique.proyectofinal.entidades.Contactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EnviarSms extends AppCompatActivity {
    EditText txtNombre, txtTelefono, txtCorreo, txtSMS;
    Button btnGuardar, btnEnviarSms;
    FloatingActionButton fabEditar, fabEliminar, fabSms;
    Contactos contactos;
    boolean correcto = false;
    int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);
        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreo = findViewById(R.id.txtCorreoElectronico);
        txtSMS = findViewById(R.id.txtSMS);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnEnviarSms = findViewById(R.id.btnEnviarSms);
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);
        fabSms = findViewById(R.id.fabSms);
        fabEditar.setVisibility(View.INVISIBLE);
        fabEliminar.setVisibility(View.INVISIBLE);
        btnGuardar.setVisibility(View.INVISIBLE);
        if (ActivityCompat.checkSelfPermission(EnviarSms.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(EnviarSms.this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                id=Integer.parseInt(null);
            }else {
                id=extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }
        DbContactos dbContactos = new DbContactos(EnviarSms.this);
        contactos = dbContactos.verContactos(id);
        if (contactos != null){
            txtNombre.setText(contactos.getNombre());
            txtTelefono.setText(contactos.getTelefono());
            txtCorreo.setText(contactos.getCorreo_electronico());
            txtNombre.setInputType(InputType.TYPE_NULL);
            txtTelefono.setInputType(InputType.TYPE_NULL);
            txtCorreo.setInputType(InputType.TYPE_NULL);
        }
        btnEnviarSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(txtTelefono.getText().toString(), null, txtSMS.getText().toString(), null, null);
                Toast.makeText(EnviarSms.this, "MENSAJE ENVIADO", Toast.LENGTH_SHORT).show(); }
        });

    }
    private void verRegistro(){
        Intent intent = new Intent(this, VerActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
}
