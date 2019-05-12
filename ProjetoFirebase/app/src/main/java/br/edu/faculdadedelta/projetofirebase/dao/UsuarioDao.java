package br.edu.faculdadedelta.projetofirebase.dao;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import br.edu.faculdadedelta.projetofirebase.modelo.Usuario;

public class UsuarioDao {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public UsuarioDao(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

    public void incluir(Usuario usuario){
        myRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if (mutableData.getValue() == null) {
                    mutableData.setValue(1);
                } else {
                    int count = mutableData.getValue(Integer.class);
                    mutableData.setValue(count + 1);
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean success, DataSnapshot dataSnapshot) {
                // Analyse databaseError for any error during increment
            }
        });

        myRef.child("usuarios").setValue(usuario);
    }
}
