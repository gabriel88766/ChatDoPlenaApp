package domicroaomacrogv.blogspot.chatdoplenaapp;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.CompletableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

import static domicroaomacrogv.blogspot.chatdoplenaapp.MainActivity.URL_ADD;

public class ChatActivity extends AppCompatActivity {

    ListView list;
    ChatArrayAdapter adapter;

    ArrayList<String> messagesNow;
    ArrayList<String> sender;
    private StompClient mStompClient;
    int userId;
    int roomId;
    String username;
    String roomName;
    private CompositeDisposable compositeDisposable;
    String newMessageReceived;
    String newMessageSend;
    String newSender;
    EditText editText;
    JSONObject newJSONMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws"+URL_ADD.substring(4)+"/mywebsockets/websocket");
        getChatInfo();
        TextView room = findViewById(R.id.textView6);
        room.setText(SingletonUsuario.getInstance().getSelectedRoom());
        messagesNow = new ArrayList<>();
        sender = new ArrayList<>();
        makeMessagesListView();
        getMessages();

        resetSubscriptions();

        editText = findViewById(R.id.edit_chat);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStompClient.disconnect();
        if (compositeDisposable != null) compositeDisposable.dispose();
    }

    public void back(View view){
         finish();
    }

    void makeMessagesListView(){

        list = findViewById(R.id.listview);
        adapter = new ChatArrayAdapter(this,R.layout.item_send);
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

    void updateListView() {
        boolean left;
        left = newSender.equals(username);
        ChatMessage newChatMessage = new ChatMessage(newMessageReceived,left);
        adapter.add(newChatMessage);
        adapter.notifyDataSetChanged();
    }


    void getMessages() {
        JsonArrayRequest request = new JsonArrayRequest
                (Request.Method.GET, URL_ADD+"/messages",null, response -> {
                    try {
                        if(response.length()!=0) {
                            messagesNow.clear();
                            sender.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject currentMessage = response.getJSONObject(i);
                                newMessageReceived=currentMessage.getString("content");
                                newSender=currentMessage.getString("username");
                                if(!newSender.equals(username))
                                    newMessageReceived=newSender+":"+newMessageReceived;
                                updateListView();
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    //Nada a fazer
                });
        SingletonRequestQueue.getInstance(this).addToRequestQueue(request);
    }
    void getChatInfo(){
        JsonObjectRequest request =
                new JsonObjectRequest(Request.Method.GET,
                                      URL_ADD+"/chatInfo",null,
                                      response-> {
                    try{
                        userId = response.getInt("userId");
                        roomId = response.getInt("roomId");
                        username = response.getString("username");
                        roomName = response.getString("roomName");
                        subscribe();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                                      },error -> { /*Nothing to do */});
        SingletonRequestQueue.getInstance(this).addToRequestQueue(request);
    }

    public void sendMessage(View view) {
        if (!mStompClient.isConnected()) return;
        newMessageSend = editText.getText().toString();
        if(newMessageSend.isEmpty())
            return;
        editText.setText("");
        JSONObject temp  = new JSONObject();

        try {
            temp.put("userId",userId);
            temp.put("roomId",roomId);
            temp.put("username",username);
            temp.put("content",newMessageSend);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        compositeDisposable.add(mStompClient.send("/app/message",String.valueOf(temp))
                                        .compose(applySchedulers())
                                        .subscribe(() -> Log.d("Chat Activity","STOMP echo send successfully"),
                                                   throwable -> Log.e("Chat Activity","Error send STOMP ",throwable)));
    }

    private void subscribe() {
        mStompClient.withClientHeartbeat(1000).withServerHeartbeat(1000);
        resetSubscriptions();
        Disposable dispLifecycle = mStompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lifecycleEvent -> {
                    switch (lifecycleEvent.getType()) {
                        case OPENED:
                            break;
                        case ERROR:
                            Log.e("ChatActivity","Stomp connection error",lifecycleEvent.getException());
                            break;
                        case CLOSED:
                            resetSubscriptions();
                            break;
                        case FAILED_SERVER_HEARTBEAT:
                            Log.e("ChatActivity","Stomp hearthbeat fail",lifecycleEvent.getException());
                            break;
                    }
                });

        compositeDisposable.add(dispLifecycle);

        Disposable dispTopic = mStompClient.topic("/topic/"+roomId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topicMessage -> {
                    newJSONMessage = new JSONObject(topicMessage.getPayload());
                    try{
                        newMessageReceived = newJSONMessage.getString("content");
                        newSender = newJSONMessage.getString("username");
                        // linha onde podemos saber o remetente newJSONMessage.getInt("username")
                        updateListView();
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                },throwable -> Log.d("fail","Error on subscribe topic",throwable));

        compositeDisposable.add(dispTopic);

        mStompClient.connect(null);
    }


    private void resetSubscriptions() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        compositeDisposable = new CompositeDisposable();
    }

    protected CompletableTransformer applySchedulers() {
        return upstream -> upstream
                .unsubscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
