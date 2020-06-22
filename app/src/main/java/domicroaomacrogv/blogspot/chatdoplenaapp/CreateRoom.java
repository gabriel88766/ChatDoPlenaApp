package domicroaomacrogv.blogspot.chatdoplenaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import static domicroaomacrogv.blogspot.chatdoplenaapp.MainActivity.URL_ADD;

public class CreateRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_room);
    }

    public void onClickCreateRoom (View view) {
        String url = URL_ADD+"/createRoom";
        JSONObject body = new JSONObject();
        try {
            body.put("roomName", ((EditText)findViewById(R.id.editText)).getText().toString());
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
            this.startActivity(new Intent(this, RoomManagement.class));
            Toast.makeText(this, "Sala criada com sucesso", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Não foi possível criar a sala", Toast.LENGTH_SHORT).show();
        }
    }
}