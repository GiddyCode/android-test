package ng.riby.androidtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
//        implements View.OnClickListener {

    NavController navController;

//    @BindView(R.id.startBtn)
//    Button startBtn;
//    @BindView(R.id.stopBtn)
//    Button stopBtn;
//    @BindView(R.id.showMap)
//    Button showMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);




    }

}
