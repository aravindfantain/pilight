package in.abhinav.pilight.pilight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Controller.controllerResponse{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String addr = String.format(Locale.ENGLISH, "%s.%s.%s.%s",
                ((EditText) findViewById(R.id.ip_1)).getText(),
                ((EditText) findViewById(R.id.ip_2)).getText(),
                ((EditText) findViewById(R.id.ip_3)).getText(),
                ((EditText) findViewById(R.id.ip_4)).getText());
        final int port = Integer.parseInt(((EditText) findViewById(R.id.port)).getText().toString());


        final Controller.controllerResponse delegate = this;
        findViewById(R.id.button_on).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Controller controller = new Controller(addr, port, "On", delegate);
                controller.execute();
            }
        });

        findViewById(R.id.button_off).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Controller controller = new Controller(addr, port, "Off", delegate);
                controller.execute();
            }
        });

    }

    @Override
    public void sendResponse(String response) {
        Toast.makeText(this, "Received from Pi: " + response, Toast.LENGTH_SHORT).show();
    }
}
