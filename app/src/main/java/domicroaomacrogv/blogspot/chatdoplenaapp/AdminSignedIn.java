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

    public void onClickChat (View view) {
        Intent chat = new Intent(this, ChatActivity.class);
        this.startActivity(chat);
    }
}
