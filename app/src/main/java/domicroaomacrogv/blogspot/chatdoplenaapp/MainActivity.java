package domicroaomacrogv.blogspot.chatdoplenaapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

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
            TextView textView = findViewById(R.id.textView5);
            textView.setText(SingletonUsuario.getInstance().getUsuario());
        }

    }

    public void startNewActivity(View view){
        Intent adminUser = new Intent(this, AdminSignedIn.class); //Nova activity caso o user seja admin
        Intent normalUser = new Intent(this, ListRoom.class); //Nova activity caso o user seja normal
        String user  = getEditText(R.id.editText);
        String password = getEditText(R.id.editText2);

        //Aqui se faz a autenticação
        SingletonUsuario.getInstance().setIsAutenticado(true);
        SingletonUsuario.getInstance().setIsAdmin(true);

        //verifica se não está autenticado
        if(!SingletonUsuario.getInstance().isAutenticado()){
            loginFailDialog();
            return;
        }

        SingletonUsuario.getInstance().setUsuario(user);
        if(SingletonUsuario.getInstance().isAdmin()) {
            this.startActivity(adminUser);
        }else{
            this.startActivity(normalUser);
        }
    }

    public void logout(View view){
        SingletonUsuario.getInstance().logout();
        finish();
        startActivity(getIntent());
    }

    public void voltar(View view){
        if(SingletonUsuario.getInstance().isAdmin()) {
            this.startActivity(new Intent(this, AdminSignedIn.class));
        }else{
            this.startActivity(new Intent(this, ListRoom.class));
        }
    }

    //Métodos auxiliares
    private String getEditText(int id){
        EditText userInput = findViewById(id);
        return userInput.getText().toString();
    }
    private void loginFailDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login Falhou");
        builder.setMessage("Login e/ou senha incorretos");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }



}
