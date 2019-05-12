package br.edu.faculdadedelta.projetofirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.edu.faculdadedelta.projetofirebase.dao.UsuarioDao;
import br.edu.faculdadedelta.projetofirebase.modelo.Usuario;

public class LoginActivity extends AppCompatActivity {

    private MaterialButton btnEntrar;
    private MaterialButton btnNovo;
    private MaterialButton btnCadastrar;
    private MaterialButton btnCancelar;

    private TextInputEditText etLoginEmail;
    private TextInputEditText etLoginSenha;

    private FrameLayout frameLogin;
    private FrameLayout frameCadastro;

    private LoginHelper helper;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        helper = new LoginHelper(this);

        etLoginEmail = findViewById(R.id.loginEmail);
        etLoginSenha = findViewById(R.id.loginSenha);

        frameLogin = findViewById(R.id.frameLogin);
        frameCadastro = findViewById(R.id.frameCadastro);

        btnNovo = findViewById(R.id.btnNovoCadastro);
        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLogin.setVisibility(View.GONE);
                frameCadastro.setVisibility(View.VISIBLE);
                helper.limparCampos();
            }
        });

        btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameCadastro.setVisibility(View.GONE);
                frameLogin.setVisibility(View.VISIBLE);
                helper.limparCampos();
            }
        });

        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });

        btnEntrar = findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logar();
            }
        });
    }

    private void cadastrar(){
        Usuario usuario = helper.popularModelo();
        UsuarioDao dao = new UsuarioDao();

        mAuth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

        dao.incluir(usuario);
        frameCadastro.setVisibility(View.GONE);
        frameLogin.setVisibility(View.VISIBLE);
        helper.limparCampos();


    }

    private void logar(){
        mAuth.signInWithEmailAndPassword(etLoginEmail.getText().toString(), etLoginSenha.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}
