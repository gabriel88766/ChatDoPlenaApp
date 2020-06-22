package domicroaomacrogv.blogspot.chatdoplenaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RoomManagement extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_management);
    }

    public void onClickCreateRoom (View view) {
        Intent myIntent = new Intent(this, CreateRoom.class);
        this.startActivity(myIntent);
    }

    public void onClickRemoveRoom (View view) {
        Intent myIntent = new Intent(this, RemoveRoom.class);
        this.startActivity(myIntent);
    }

    public void onClickAddUserToRoom (View view) {
        Intent myIntent = new Intent(this, AddUserToRoom.class);
        this.startActivity(myIntent);
    }

    public void onClickRemoveUserFromRoom (View view) {
        Intent myIntent = new Intent(this, RemoveUserFromRoom.class);
        this.startActivity(myIntent);
    }
}
