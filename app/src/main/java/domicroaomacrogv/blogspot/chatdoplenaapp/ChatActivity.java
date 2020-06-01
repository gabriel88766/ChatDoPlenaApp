package domicroaomacrogv.blogspot.chatdoplenaapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {

    // int quantityOfMessages; atributo será usado caso seja mostrado mais mensagens quando o usuario vai para cima
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        TextView room = findViewById(R.id.textView6);
        room.setText(SingletonUsuario.getInstance().getSelectedRoom());
        showMessages();
    }

    public void back(View view){
         finish();
    }
    public void send(View view){
            //Enviar mensagem.
    }
    void showMessages(){
        String[] test = new String[50];
        for(int i = 0;i<50;i++){
            test[i]="mensagem"+i;
        }
        list = findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,test);
        list.setAdapter(adapter);
    }
}
