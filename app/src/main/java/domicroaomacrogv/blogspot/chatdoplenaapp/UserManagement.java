package domicroaomacrogv.blogspot.chatdoplenaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class UserManagement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_management);
    }

    public void onClickCreateUser (View view) {
        Intent myIntent = new Intent(this, RoomManagement.class);
        this.startActivity(myIntent);
    }

    public void onClickRemoveUser (View view) {
        Intent myIntent = new Intent(this, RemoveUser.class);
        this.startActivity(myIntent);
    }

    public void onClickChangeUser (View view) {
        Intent myIntent = new Intent(this, RoomManagement.class);
        this.startActivity(myIntent);
    }
}
