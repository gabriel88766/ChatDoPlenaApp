package domicroaomacrogv.blogspot.chatdoplenaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import static domicroaomacrogv.blogspot.chatdoplenaapp.MainActivity.URL_ADD;

public class ListRoom extends AppCompatActivity {

    ArrayList<String> salas;
    ArrayList<Integer> ids;
    ListView list;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_room);
        makeListView(); //faz lista vazia a ser atualizada após a request
        getListView();
    }


    void getListView(){
        String url = URL_ADD+"/chats";
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.POST, url,null, response -> {
                    try {
                            salas = new ArrayList<>();
                            ids = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject currentRoom = response.getJSONObject(i);
                                salas.add(currentRoom.getString("name"));
                                ids.add(currentRoom.getInt("id"));
                            }
                        }else{
                            salas = new ArrayList<>(Collections.singletonList("Nenhuma sala encontrada"));
                        }
                       updateListView();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    //Nothing to do
                });
        SingletonRequestQueue.getInstance(this).addToRequestQueue(request);
    }
    //inicializa listview antes da request
    private void makeListView(){
        ArrayList<String> emptyString = new ArrayList<>(Collections.singletonList("Loading rooms..."));
        list = findViewById(R.id.listview);
        adapter = new ArrayAdapter<>(this,R.layout.room_view,emptyString);
        list.setAdapter(adapter);
        list.setClickable(true);

        list.setOnItemClickListener((parent, view, position, id) -> {
            String selectedSala = list.getItemAtPosition(position).toString();
            alertDialogShow(selectedSala);
        });
    }
    private void updateListView(){
        adapter.clear();
        adapter.addAll(salas);
        // fire the event
        adapter.notifyDataSetChanged();
    }
    public void logout(View view){
        SingletonUsuario.getInstance().logout();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    int getRoomId(String sala){
        int selectedIndex=0;
        for(int i=0;i<salas.size();i++){
            if(salas.get(i).equals(sala)){
                selectedIndex=i;
            }
        }
        return ids.get(selectedIndex);
    }

    void alertDialogShow(String sala){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmação");
        builder.setMessage("Você deseja entrar na sala:\n\t"+sala+" ?");
        builder.setPositiveButton("OK", (dialog, args) -> {
            SingletonUsuario.getInstance().selectRoom(sala);
            selectRoom(getRoomId(sala));
        });
        builder.setNegativeButton("Cancelar",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void selectRoom(int id){
        JSONObject body = new JSONObject();
        try {
            body.put("chatId",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD + "/selectChat",
                response -> {
                    if(response.equals("true")){
                        Intent intent = new Intent(getBaseContext(), ChatActivity.class);
                        startActivity(intent);
                    }
                },
                error -> { //nothing to do
                }) {
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

}
