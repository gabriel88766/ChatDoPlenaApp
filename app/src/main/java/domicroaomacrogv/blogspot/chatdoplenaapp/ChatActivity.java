package domicroaomacrogv.blogspot.chatdoplenaapp;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import static domicroaomacrogv.blogspot.chatdoplenaapp.MainActivity.URL_ADD;

public class ChatActivity extends AppCompatActivity {

    ListView list;
    ArrayAdapter<String> adapter;
    Handler handler = new Handler();
    Runnable runnable;
    String myUsername;
    ArrayList<String> messagesNow;
    ArrayList<String> sender;
    int qnt;
    int oldQnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        TextView room = findViewById(R.id.textView6);
        room.setText(SingletonUsuario.getInstance().getSelectedRoom());
        messagesNow = new ArrayList<>();
        sender = new ArrayList<>();
        makeMessagesListView();
        myUsername = SingletonUsuario.getInstance().getUsuario();
    }

    // COMEÇO: o código a seguir funciona atualizando a lista de mensagens a cada 2 segundos
    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed( runnable = () -> {
            updateMessages();
            handler.postDelayed(runnable, 2000);
        }, 2000);
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }
//fim: o código acima funciona atualizando a lista de mensagens a cada 2 segundos

    public void back(View view){
         finish();
    }
    public void send(View view){
            //Enviar mensagem.
    }
    void makeMessagesListView(){

        ArrayList<String> createLv = new ArrayList<>();
        createLv.add("Wait...");
        list = findViewById(R.id.listview);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,createLv);
        list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        list.setAdapter(adapter);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                list.setSelection(adapter.getCount() - 1);
            }
        });

    }

    void updateListView(){

        if(verifyQuantity()) {
            oldQnt=qnt;
            adapter.clear();
            adapter.addAll(messagesNow);
            adapter.notifyDataSetChanged();
        }
    }


    void updateMessages(){
        JsonArrayRequest request = new JsonArrayRequest
                (Request.Method.GET, URL_ADD+"/messages",null, response -> {
                    try {
                        if(response.length()!=0) {
                            messagesNow.clear();
                            sender.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject currentRoom = response.getJSONObject(i);
                                messagesNow.add(currentRoom.getString("content"));
                                sender.add(currentRoom.getString("username"));
                            }
                            qnt=response.length();

                        }else{
                            messagesNow = new ArrayList<>(Collections.singletonList("Nenhuma mensagem encontrada"));
                        }
                        updateListView();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    //Nada a fazer
                });
        SingletonRequestQueue.getInstance(this).addToRequestQueue(request);
    }

    boolean verifyQuantity(){ //retorna verdadeiro se a quantidade de mensagens aumentar
        return qnt!=oldQnt;
    }

}
