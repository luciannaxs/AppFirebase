package br.edu.faculdadedelta.projetofirebase;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;

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

    private String erro = "Campo Obrigatório";

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
        ilCadSenha.setError(null);
        ilCadConfirma.setError(null);
        ilCadEmail.setError(null);
        ilCadNome.setError(null);
        ilLoginEmail.setError(null);
        ilLoginSenha.setError(null);
        usuario = new Usuario();
    }

    public Usuario popularModelo(){
        usuario.setNome(etCadNome.getText().toString());
        usuario.setEmail(etCadEmail.getText().toString());
        usuario.setSenha(etCadSenha.getText().toString());
        return usuario;
    }


    public boolean isCadastroValid(){
        boolean retorno = true;
        if(TextUtils.isEmpty(etCadNome.getText().toString())){
            ilCadNome.setError(" ");
            retorno = false;
        }
        if(TextUtils.isEmpty(etCadEmail.getText().toString())){
            ilCadEmail.setError(" ");
            retorno = false;
        }
        if(TextUtils.isEmpty(etCadSenha.getText().toString())){
            ilCadSenha.setError(" ");
            retorno = false;
        }
        if(TextUtils.isEmpty(etCadConfirma.getText().toString())){
            ilCadConfirma.setError(" ");
            retorno = false;
        }
        if(!isSenhaValid()){
            ilCadSenha.setError(" ");
            ilCadConfirma.setError("Senhas não conferem");
            retorno = false;
        }
        return retorno;
    }

    private boolean isSenhaValid(){
        return etCadSenha.getText().toString().equals(etCadConfirma.getText().toString());
    }

    public boolean isLoginValid(){
        boolean retorno = true;
        if(TextUtils.isEmpty(etLoginEmail.getText().toString())){
            ilLoginEmail.setError(" ");
            retorno = false;
        }
        if(TextUtils.isEmpty(etLoginSenha.getText().toString())){
            ilLoginSenha.setError(" ");
            retorno = false;
        }
        return retorno;
    }
}
