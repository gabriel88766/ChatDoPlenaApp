package domicroaomacrogv.blogspot.chatdoplenaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminSignedIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signed_in);
    }

    public void onClickUser (View view) {
        Intent userIntent = new Intent(this, UserManagement.class);
        this.startActivity(userIntent);
    }

    public void onClickRoom (View view) {
        Intent roomIntent = new Intent(this, RoomManagement.class);
        this.startActivity(roomIntent);
    }

    public void onClickChat (View view) {
        Intent chat = new Intent(this, ListRoom.class);
        this.startActivity(chat);
    }

    public void logout(View view){
        SingletonUsuario.getInstance().logout();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
