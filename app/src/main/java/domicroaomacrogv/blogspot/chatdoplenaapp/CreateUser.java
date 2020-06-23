package domicroaomacrogv.blogspot.chatdoplenaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import static domicroaomacrogv.blogspot.chatdoplenaapp.MainActivity.URL_ADD;

public class CreateUser extends AppCompatActivity {

    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);
    }

    public void onClickCreateUser (View view) {
        String url = URL_ADD+"/createUser";
        JSONObject body = new JSONObject();
        try {
            body.put("username", ((EditText)findViewById(R.id.editText)).getText().toString());
            body.put("email", ((EditText)findViewById(R.id.editText2)).getText().toString());
            body.put("password", ((EditText)findViewById(R.id.editText3)).getText().toString());
            body.put("retype", ((EditText)findViewById(R.id.editText4)).getText().toString());
            body.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    if(response.equals("true")){
                        result(true);
                    }else{
                        result(false);
                    }
                },
                error -> result(false)) {
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

    public void result(boolean success)
    {
        if(success)
        {
            finish();
            Toast.makeText(this, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Não foi possível criar o usuário", Toast.LENGTH_SHORT).show();
        }
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_admin:
                if (checked)
                    type = "Admin";
                    break;
            case R.id.radio_regular:
                if (checked)
                    type = "Regular";
                    break;
        }
    }

}