package com.example.agregandooperacionesanuestrabd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    //Creamos una instancia de nuerstra BD aacediendo al nodo mensaje
    DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
    DatabaseReference mensajeRef=ref.child("mensaje");
    private TextView tvMensaje;
    private EditText txtUserId,txtCorreo,txtNombre, txtPassword;
    String UserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMensaje=(TextView) findViewById(R.id.tvMensaje);
        txtUserId=(EditText)findViewById(R.id.txtUserId);
        txtNombre=(EditText)findViewById(R.id.txtNombre);
        txtCorreo=(EditText)findViewById(R.id.txtCorreo);
        txtPassword=(EditText)findViewById(R.id.txtPassword);
//Enviamos un valor al nodo solo para realizar pruebas
        mensajeRef.setValue("Bienvenido a Firebase!!!");
    }
    @Override
    protected void onStart(){
        super.onStart();
//Agregamos un escuchador de eventos al nodo mensaje para cuando haya cambios mandarlos aun TextView, este mensaje lo traemos de Firebase
        mensajeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//De esta manera tomamos el valor de Firebase
                String value=dataSnapshot.getValue(String.class);
                tvMensaje.setText(value);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void modificar(View view){
        try{
            UserId=txtUserId.getText().toString();
            String Nombre=txtNombre.getText().toString();
            String Correo=txtCorreo.getText().toString();
            String Password=txtPassword.getText().toString();
// Creamos el objeto mandando la información que va a contener, en este caso el usuario ladefine.
                    User user = new User(UserId,Nombre, Correo,Password);
// Creamos una instancia de DatabaseReference para crear nodos dentro del child usuarios quedebe existir en Firebase
            DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
// vamos a agregar el nodo dentro del nodo UserId
            mDatabase.child("usuarios").child(UserId).setValue(user);
            tvMensaje.setText("");
            txtUserId.setText("");
            txtNombre.setText("");
            txtCorreo.setText("");
            txtPassword.setText("");
            Toast.makeText(this, "Nodo insertado con exito", Toast.LENGTH_SHORT).show();
        }
        catch (Exception x){
            Toast.makeText(this, "Hubo problema en la inserción", Toast.LENGTH_SHORT).show();
        }
    }
    public void eliminar(View view){
        UserId=txtUserId.getText().toString();
        try {

            DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
            DatabaseReference mensajeRef=ref.child("usuarios/"+UserId);
            mensajeRef.removeValue();
            Toast.makeText(this, "Nodo eliminado con exito", Toast.LENGTH_SHORT).show();
            txtUserId.setText("");
        }
        catch (Exception x){
            Toast.makeText(this, "Hubo problema en la eliminación",
                    Toast.LENGTH_SHORT).show();
        }}

        public void mostrarUsuario (View view){
// Obtenemos la referencia a ciero nodo en particular dentro del padre usuarios
            DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
            DatabaseReference mensajeRef=ref.child("usuarios/"+UserId);
// Attach a listener to read the data at our posts reference
            mensajeRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//para traer un objeto completo creamos un objeto de la clase User y atraves de elaccedemos a lo que queramos
                    User post = dataSnapshot.getValue(User.class);
                    System.out.println(post);
//tvMensaje.setText("Nombre"+post.username+"\n Email: "+post.email);
                    tvMensaje.setText("Nombre: "+post.getUsername()+"\n Correo: "+post.getEmail());
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }
    }