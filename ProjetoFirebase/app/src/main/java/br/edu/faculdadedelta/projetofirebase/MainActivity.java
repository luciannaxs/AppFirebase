package br.edu.faculdadedelta.projetofirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth fireAuth;
    private FirebaseUser fireUser;
    private FirebaseDatabase fireDatabase;
    private DatabaseReference fireRef;

    private MaterialButton btnFinal;
    private MaterialButton btnQuestao1;
    private MaterialButton btnQuestao2;
    private MaterialButton btnQuestao3;
    private MaterialButton btnQuestao4;

    private FrameLayout frameQuestao1;
    private FrameLayout frameQuestao2;
    private FrameLayout frameQuestao3;
    private FrameLayout frameQuestao4;
    private FrameLayout frameFinal;

    private TextView tvUser;
    private TextView tvUserEmail;


    private TextView tvQtdQuestoes;
    private TextView tvAcertos;
    private TextView tvPercAcertos;
    private TextView tvFinalResult;

    private RadioGroup rgQuestao1;
    private RadioGroup rgQuestao2;
    private RadioGroup rgQuestao3;
    private RadioGroup rgQuestao4;

    private int qtdAcertos = 0;
    private int qtdQuestoes = 4;
    private double percAcertos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fireDatabase = FirebaseDatabase.getInstance();
        fireRef = fireDatabase.getReference();

        fireAuth = FirebaseAuth.getInstance();
        fireUser = fireAuth.getCurrentUser();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        setCamposAposInflate();

        return true;
    }

    private void setCamposAposInflate() {
        tvUser = findViewById(R.id.tvUserMenu);
        tvUser.setText(fireUser.getDisplayName());
        tvUserEmail = findViewById(R.id.tvUserEmail);
        tvUserEmail.setText(fireUser.getEmail());

        tvQtdQuestoes = findViewById(R.id.tvQtdQuestoes);
        tvAcertos = findViewById(R.id.tvAcertos);
        tvPercAcertos = findViewById(R.id.tvPercAcertos);
        tvFinalResult = findViewById(R.id.tvFinalResult);

        frameQuestao1 = findViewById(R.id.frameUm);
        frameQuestao2 = findViewById(R.id.frameDois);
        frameQuestao3 = findViewById(R.id.frameTres);
        frameQuestao4 = findViewById(R.id.frameQuatro);
        frameFinal = findViewById(R.id.frameFinal);

        rgQuestao1 = findViewById(R.id.radioQuestao1);
        rgQuestao2 = findViewById(R.id.radioQuestao2);
        rgQuestao3 = findViewById(R.id.radioQuestao3);
        rgQuestao4 = findViewById(R.id.radioQuestao4);

        btnQuestao1 = findViewById(R.id.btnUm);
        btnQuestao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validaQuestao(rgQuestao1, (RadioButton) findViewById(R.id.resposta1))) {
                    intentFrame(frameQuestao1, frameQuestao2);
                }
            }
        });

        btnQuestao2 = findViewById(R.id.btnDois);
        btnQuestao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validaQuestao(rgQuestao2, (RadioButton) findViewById(R.id.resposta2))) {
                    intentFrame(frameQuestao2, frameQuestao3);
                }
            }
        });

        btnQuestao3 = findViewById(R.id.btnTres);
        btnQuestao3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validaQuestao(rgQuestao3, (RadioButton) findViewById(R.id.resposta3))) {
                    intentFrame(frameQuestao3, frameQuestao4);
                }
            }
        });

        btnQuestao4 = findViewById(R.id.btnQuatro);
        btnQuestao4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validaQuestao(rgQuestao4, (RadioButton) findViewById(R.id.resposta4))) {
                    intentFrame(frameQuestao4, frameFinal);
                    calcPercAcertos();
                    tvAcertos.setText("Acertos: " + String.valueOf(qtdAcertos));
                    tvQtdQuestoes.setText("Qtd. questões: " + String.valueOf(qtdQuestoes));
                    tvPercAcertos.setText("Taxa de acertos: " + String.valueOf(percAcertos));
                    tvFinalResult.setText("Resultado: " + validaResultado());
                    gravarResultado();
                }
            }
        });

        btnFinal = findViewById(R.id.btnFinal);
        btnFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetQuiz();
                frameFinal.setVisibility(View.GONE);
                frameQuestao1.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_exit) {
            fireAuth.signOut();
            Intent intencao = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intencao);
        } else if (id == R.id.nav_resultado) {
            selectFireResultado();
            esconderFramesQuestoes();
            frameFinal.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_configuracoes) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void selectFireResultado() {
        fireRef.child("usuarios").child(fireUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    tvAcertos.setText("Acertos: " + dataSnapshot.child("acertos").getValue().toString());
                    tvQtdQuestoes.setText("Qtd. questoes: " + dataSnapshot.child("qtdQuestoes").getValue().toString());
                    tvPercAcertos.setText("Taxa de acertos: " + dataSnapshot.child("percAcertos").getValue().toString());
                    tvFinalResult.setText("Resultado: " + dataSnapshot.child("resultado").getValue().toString());
                } else
                    tvFinalResult.setText("Você ainda não fez nenhum Quiz!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void esconderFramesQuestoes() {
        frameQuestao1.setVisibility(View.GONE);
        frameQuestao2.setVisibility(View.GONE);
        frameQuestao3.setVisibility(View.GONE);
        frameQuestao4.setVisibility(View.GONE);
    }


    private void resetQuiz() {
        this.qtdAcertos = 0;
        this.rgQuestao1.clearCheck();
        this.rgQuestao2.clearCheck();
        this.rgQuestao3.clearCheck();
        this.rgQuestao4.clearCheck();
    }

    private boolean validaQuestao(RadioGroup rg, RadioButton resposta) {
        int checkedRadioButtonId = rg.getCheckedRadioButtonId();
        if (checkedRadioButtonId != -1) {
            if (checkedRadioButtonId == resposta.getId()) {
                this.qtdAcertos += 1;
            }
            return true;
        }

        Toast.makeText(getBaseContext(), "Selecione uma resposta!", Toast.LENGTH_LONG).show();

        return false;
    }

    private void intentFrame(FrameLayout gone, FrameLayout visible) {
        gone.setVisibility(View.GONE);
        visible.setVisibility(View.VISIBLE);
    }

    private void gravarResultado() {
        String resultado = validaResultado();

        fireRef.child("usuarios").child(fireUser.getUid()).child("qtdQuestoes").setValue(qtdQuestoes);
        fireRef.child("usuarios").child(fireUser.getUid()).child("acertos").setValue(qtdAcertos);
        fireRef.child("usuarios").child(fireUser.getUid()).child("resultado").setValue(resultado);
        fireRef.child("usuarios").child(fireUser.getUid()).child("percAcertos").setValue(this.percAcertos);
    }

    private void calcPercAcertos() {
        this.setPercAcertos((this.qtdAcertos * 100) / this.qtdQuestoes);
    }

    private void setPercAcertos(int i) {
        this.percAcertos = i;
    }

    private String validaResultado() {

        if (this.percAcertos < 50)
            return "Ruim";
        else if (this.percAcertos > 50 && this.percAcertos < 80)
            return "Bom";

        return "Excelente";
    }
}
