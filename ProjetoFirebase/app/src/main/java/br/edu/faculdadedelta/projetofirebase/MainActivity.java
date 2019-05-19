package br.edu.faculdadedelta.projetofirebase;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import br.edu.faculdadedelta.projetofirebase.modelo.Usuario;

public class MainActivity extends AppCompatActivity {

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
    private TextView tvFinalResult;

    private RadioGroup rgQuestao1;
    private RadioGroup rgQuestao2;
    private RadioGroup rgQuestao3;
    private RadioGroup rgQuestao4;

    private int qtdAcertos = 0;
    private int qtdQuestoes = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fireDatabase = FirebaseDatabase.getInstance();
        fireRef = fireDatabase.getReference();

        fireAuth = FirebaseAuth.getInstance();
        fireUser = fireAuth.getCurrentUser();

        tvUser = findViewById(R.id.tvUser);
        tvFinalResult = findViewById(R.id.tvFinalResult);
        tvUser.setText("Bem Vindo(a): ".concat(fireUser.getDisplayName()));

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
                    tvFinalResult.setText(String.valueOf(qtdAcertos));
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
        double total = (this.qtdAcertos * 100) / this.qtdQuestoes;

        String resultado = validaResultado(total);

        fireRef.child("usuarios").child(fireUser.getUid()).child("qtdQuestoes").setValue(qtdQuestoes);
        fireRef.child("usuarios").child(fireUser.getUid()).child("acertos").setValue(qtdAcertos);
        fireRef.child("usuarios").child(fireUser.getUid()).child("resultado").setValue(resultado);
        fireRef.child("usuarios").child(fireUser.getUid()).child("percAcertos").setValue(total);
    }

    private String validaResultado(double total) {

        if (total < 50)
            return "Ruim";
        else if (total > 50 && total < 80)
            return "Bom";

        return "Excelente";
    }
}
