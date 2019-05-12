package br.edu.faculdadedelta.projetofirebase;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.edu.faculdadedelta.projetofirebase.modelo.Usuario;

public class LoginHelper {

    private TextInputLayout ilLoginEmail;
    private TextInputLayout ilLoginSenha;
    private TextInputLayout ilCadNome;
    private TextInputLayout ilCadEmail;
    private TextInputLayout ilCadSenha;
    private TextInputLayout ilCadConfirma;

    private TextInputEditText etLoginEmail;
    private TextInputEditText etLoginSenha;
    private TextInputEditText etCadNome;
    private TextInputEditText etCadEmail;
    private TextInputEditText etCadSenha;
    private TextInputEditText etCadConfirma;

    private Usuario usuario;

    public LoginHelper(LoginActivity activity) {
        ilLoginEmail = activity.findViewById(R.id.hintLoginEmail);
        ilLoginSenha = activity.findViewById(R.id.hintLoginSenha);
        ilCadNome = activity.findViewById(R.id.hintCadNome);
        ilCadEmail = activity.findViewById(R.id.hintCadEmail);
        ilCadSenha = activity.findViewById(R.id.hintCadSenha);
        ilCadConfirma = activity.findViewById(R.id.hintCadConfirma);
        etLoginEmail = activity.findViewById(R.id.loginEmail);
        etLoginSenha = activity.findViewById(R.id.loginSenha);
        etCadNome = activity.findViewById(R.id.cadNome);
        etCadEmail = activity.findViewById(R.id.cadEmail);
        etCadSenha = activity.findViewById(R.id.cadSenha);
        etCadConfirma = activity.findViewById(R.id.cadConfirma);
        usuario = new Usuario();
    }

    public void limparCampos() {
        etLoginEmail.setText("");
        etLoginSenha.setText("");
        etCadNome.setText("");
        etCadEmail.setText("");
        etCadSenha.setText("");
        etCadConfirma.setText("");
        usuario = new Usuario();
    }

    public Usuario popularModelo(){
        usuario.setNome(etCadNome.getText().toString());
        usuario.setEmail(etCadEmail.getText().toString());
        usuario.setSenha(etCadSenha.getText().toString());
        return usuario;
    }
}
