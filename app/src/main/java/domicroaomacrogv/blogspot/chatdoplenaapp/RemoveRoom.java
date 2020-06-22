package domicroaomacrogv.blogspot.chatdoplenaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class RemoveRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_room);
    }

    public void onClickRemoveRoom (View view) {
        if ( sucessRemovingRoom() ) {
            finish();
            this.startActivity(new Intent(this, RoomManagement.class));
            Toast.makeText(this, "Sala removida com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Não foi possível remover a sala", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean sucessRemovingRoom() {
        return true;
    }
}