package domicroaomacrogv.blogspot.chatdoplenaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChangeUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_user);
    }

    public void onClickChangeEmail (View view) {
        Intent myIntent = new Intent(this, ChangeEMail.class);
        this.startActivity(myIntent);
    }

    public void onClickChangePassword (View view) {
        Intent myIntent = new Intent(this, ChangePassword.class);
        this.startActivity(myIntent);
    }
}