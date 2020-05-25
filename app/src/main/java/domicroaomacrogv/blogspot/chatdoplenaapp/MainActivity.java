package domicroaomacrogv.blogspot.chatdoplenaapp;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.IDN;

public class MainActivity extends AppCompatActivity {

    boolean admin,autenticado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onRestart(){
        super.onRestart();
        if(SingletonUsuario.getInstance().getUsuario()!=null){
            setContentView(R.layout.secondary_login);
            TextView textView = (TextView) findViewById(R.id.textView5);
            textView.setText(SingletonUsuario.getInstance().getUsuario());
        }


    }

    public void startNewActivity(View view){
        Intent adminUser = new Intent(this, AdminSignedIn.class); //Nova activity caso o user seja admin
        Intent normalUser = new Intent(this, NormalUser.class); //Nova activity caso o user seja normal
        String user  = getEditText(R.id.editText);
        String password = getEditText(R.id.editText2);

        //Aqui se faz a autenticação
        autenticado=true;
        admin=true;

        if(!autenticado){
            setDialog();
            return;
        }

        SingletonUsuario.getInstance().setUsuario(user);
        if(admin) {
            this.startActivity(adminUser);
        }else{
            this.startActivity(normalUser);
        }
    }
    public void logout(View view){
        SingletonUsuario.getInstance().setUsuario(null);
        finish();
        startActivity(getIntent());
    }

    //Métodos auxiliares
    private String getEditText(int id){
        EditText userInput = (EditText)findViewById(id);
        String user = userInput.getText().toString();
        return user;
    }
    private void setDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login Falhou");
        builder.setMessage("Login e/ou senha incorretos");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }



}
