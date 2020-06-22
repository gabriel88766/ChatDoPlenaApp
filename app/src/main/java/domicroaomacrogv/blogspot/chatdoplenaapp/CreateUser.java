package domicroaomacrogv.blogspot.chatdoplenaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class CreateUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);
    }

    public void onClickCreateUser (View view) {
        if ( sucessCreatingUser() ) {
            finish();
            this.startActivity(new Intent(this, UserManagement.class));
            Toast.makeText(this, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Não foi possível criar o usuário", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean sucessCreatingUser() {
        return true;
    }
}