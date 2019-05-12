package br.edu.faculdadedelta.projetofirebase;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.edu.faculdadedelta.projetofirebase.modelo.Usuario;

public class MainActivity extends AppCompatActivity {

    private MaterialButton btnTestar;
    private MaterialButton btnUm;
    private MaterialButton btnDois;
    private MaterialButton btnTres;

    private FrameLayout frameUm;
    private FrameLayout frameDois;
    private FrameLayout frameTres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();



        /*Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setNome("Samuca");
        usuario.setEmail("samuca@gmail.com");
        usuario.setSenha(BCrypt.withDefaults().hashToString(12, "viado".toCharArray()));

        myRef.child("usuarios").child(String.valueOf(usuario.getId())).setValue(usuario);

        btnTestar = findViewById(R.id.btnTestar);
        btnTestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testar();
            }
        });*/

        frameUm = findViewById(R.id.frameUm);
        frameDois = findViewById(R.id.frameDois);
        frameTres = findViewById(R.id.frameTres);

        btnUm = findViewById(R.id.btnUm);
        btnUm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameUm.setVisibility(View.GONE);
                frameDois.setVisibility(View.VISIBLE);
            }
        });

        btnDois = findViewById(R.id.btnDois);
        btnDois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameDois.setVisibility(View.GONE);
                frameTres.setVisibility(View.VISIBLE);
            }
        });

        btnTres = findViewById(R.id.btnTres);
        btnTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameTres.setVisibility(View.GONE);
                frameUm.setVisibility(View.VISIBLE);
            }
        });


    }

    private void testar() {
        Intent intent = new Intent(getBaseContext(), TesteActivity.class);
        startActivity(intent);
    }
}
