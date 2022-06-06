package com.enrique.proyectofinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.enrique.proyectofinal.db.DbContactos;
import com.enrique.proyectofinal.entidades.Contactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VerActivity extends AppCompatActivity {
    TextView viewSMS;
    EditText txtNombre, txtTelefono, txtCorreo, txtSMS;
    Button btnGuardar, btnEnviarSms;
    FloatingActionButton fabEditar, fabEliminar, fabSms;
    Contactos contactos;
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
        viewSMS = findViewById(R.id.viewSMS);

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
        DbContactos dbContactos = new DbContactos(VerActivity.this);
        contactos = dbContactos.verContactos(id);
        if (contactos != null){
            txtNombre.setText(contactos.getNombre());
            txtTelefono.setText(contactos.getTelefono());
            txtCorreo.setText(contactos.getCorreo_electronico());
            txtNombre.setInputType(InputType.TYPE_NULL);
            txtTelefono.setInputType(InputType.TYPE_NULL);
            txtCorreo.setInputType(InputType.TYPE_NULL);
            txtSMS.setVisibility(View.INVISIBLE);
            viewSMS.setVisibility(View.INVISIBLE);
            btnGuardar.setVisibility(View.INVISIBLE);
            btnEnviarSms.setVisibility(View.INVISIBLE);
        }
        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerActivity.this, EditarActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });
        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VerActivity.this);
                builder.setMessage("¿Desea eliminar este contacto?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (dbContactos.eliminarContacto(id)){
                                    lista();
                                }

                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });
        fabSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerActivity.this, EnviarSms.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });
    }
    private void lista(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}