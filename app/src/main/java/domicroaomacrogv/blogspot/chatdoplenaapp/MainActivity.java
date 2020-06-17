package domicroaomacrogv.blogspot.chatdoplenaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;

public class MainActivity extends AppCompatActivity {

    public static final String URL_ADD = "http://10.0.2.2:8081"; //url para localhost:8081. Deve conter http:// para ser bem formada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CookieManager manager = new CookieManager();
        CookieHandler.setDefault( manager  );
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (SingletonUsuario.getInstance().isAutenticado()) {
            setContentView(R.layout.secondary_login);
            TextView textView = findViewById(R.id.textView5);
            textView.setText(SingletonUsuario.getInstance().getUsuario());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new StringRequest(Request.Method.GET, URL_ADD + "/logout", null, null);
    }
    public void startNewActivity(View view) {

        String user = getEditText(R.id.editText);
        String password = getEditText(R.id.editText2);

        //desativar o botão para evitar múltiplos clicks
        Button btn = findViewById(R.id.button);
        btn.setEnabled(false);

        loginUser(user,password);

        SingletonUsuario.getInstance().setUsuario(user);
    }

    public void logout(View view) {
        SingletonUsuario.getInstance().logout();
        finish();
        startActivity(getIntent());
    }

    public void voltar(View view) {
        if (SingletonUsuario.getInstance().isAdmin()) {
            this.startActivity(new Intent(this, AdminSignedIn.class));
        } else {
            this.startActivity(new Intent(this, ListRoom.class));
        }
    }

    private String getEditText(int id) {
        EditText userInput = findViewById(id);
        return userInput.getText().toString();
    }

    public void loginUser(String user, String password) {
        JSONObject body = new JSONObject();
        try {
            body.put("username",user);
            body.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD + "/login",
                response -> {
                    if(response.equals("NOT_LOGGED")){
                        loginFailDialog();
                        Button btn = (Button) findViewById(R.id.button);
                        btn.setEnabled(true);
                    }else{
                        SingletonUsuario.getInstance().setIsAutenticado(true);
                        loginDialog(response);
                    }
                },
                error -> FailToConnectDialog()) {
            @Override
            public byte[] getBody() {
                return body.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        SingletonRequestQueue.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void loginFailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login Falhou");
        builder.setMessage("Login e/ou senha incorretos");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void loginDialog(String response) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bem vindo");
        builder.setMessage(SingletonUsuario.getInstance().getUsuario());
        builder.setPositiveButton("OK", (dialog, id) -> {
            if(response.equals("ADMIN")){
                SingletonUsuario.getInstance().setIsAdmin(true);
                startActivity(new Intent(getBaseContext(),AdminSignedIn.class));
            }else if(response.equals("REGULAR")){
                SingletonUsuario.getInstance().setIsAdmin(false);
                startActivity(new Intent(getBaseContext(),ListRoom.class));
            }else{
                loginFailDialog();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void FailToConnectDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Erro na conexão");
        builder.setPositiveButton("OK",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}


