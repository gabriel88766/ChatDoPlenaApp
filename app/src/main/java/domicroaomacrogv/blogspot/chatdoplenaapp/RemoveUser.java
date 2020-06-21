package domicroaomacrogv.blogspot.chatdoplenaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RemoveUser extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_user);
    }

    public void onClickRemoveUser (View view) {
        if ( sucessRemovingUser() ) {
            finish();
            this.startActivity(new Intent(this, UserManagement.class));
            Toast.makeText(this, "Usuário removido com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Não foi possível remover o usuário", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean sucessRemovingUser() {
        return true;
    }
}
