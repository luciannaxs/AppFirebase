package br.edu.faculdadedelta.projetofirebase;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private TextView tvUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        tvUser = findViewById(R.id.tvUser);
        tvUser.setText("Bem Vindo(a): ".concat(user.getDisplayName()));

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

}
