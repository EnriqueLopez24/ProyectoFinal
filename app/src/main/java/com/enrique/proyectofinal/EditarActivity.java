package com.enrique.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.enrique.proyectofinal.db.DbContactos;
import com.enrique.proyectofinal.entidades.Contactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditarActivity extends AppCompatActivity {
    EditText txtNombre, txtTelefono, txtCorreo, txtSMS;
    Button btnGuardar, btnEnviarSms;
    TextView viewSMS;
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
        viewSMS = findViewById(R.id.viewSMS);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnEnviarSms = findViewById(R.id.btnEnviarSms);
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);
        fabSms = findViewById(R.id.fabSms);
        fabEditar.setVisibility(View.INVISIBLE);
        fabEliminar.setVisibility(View.INVISIBLE);
        fabSms.setVisibility(View.INVISIBLE);
        btnEnviarSms.setVisibility(View.INVISIBLE);
        viewSMS.setVisibility(View.INVISIBLE);
        txtSMS.setVisibility(View.INVISIBLE);
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
        DbContactos dbContactos = new DbContactos(EditarActivity.this);
        contactos = dbContactos.verContactos(id);
        if (contactos != null){
            txtNombre.setText(contactos.getNombre());
            txtTelefono.setText(contactos.getTelefono());
            txtCorreo.setText(contactos.getCorreo_electronico());
        }
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtNombre.getText().toString().equals("") && !txtTelefono.getText().toString().equals("") && !txtCorreo.getText().toString().equals("")){
                   correcto = dbContactos.editarContacto(id, txtNombre.getText().toString(), txtTelefono.getText().toString(), txtCorreo.getText().toString());
                    if (correcto){
                        Toast.makeText(EditarActivity.this, "CONTACTO ACTUALIZADO", Toast.LENGTH_SHORT).show();
                        verRegistro();
                    }else {
                        Toast.makeText(EditarActivity.this, "ERROR AL ACTUALIZAR", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(EditarActivity.this, "ASEGURESE DE LLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void verRegistro(){
        Intent intent = new Intent(this, VerActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
}
