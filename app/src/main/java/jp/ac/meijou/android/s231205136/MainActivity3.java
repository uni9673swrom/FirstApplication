package jp.ac.meijou.android.s231205136;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultLauncherKt;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Optional;

import jp.ac.meijou.android.s231205136.databinding.ActivityMain3Binding;
import jp.ac.meijou.android.s231205136.databinding.ActivityMainBinding;

public class MainActivity3 extends AppCompatActivity {
    private ActivityMain3Binding binding;
    private final ActivityResultLauncher<Intent> getActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result ->{
                switch (result.getResultCode()) {
                    case RESULT_OK:
                        Optional.ofNullable(result.getData())
                                .map(data -> data.getStringExtra("ret"))
                                .map(text -> "Result" + text)
                                .ifPresent(text -> binding.textView4.setText(text));
                        break;
                    case RESULT_CANCELED:
                        binding.textView4.setText("Result: Canceled");
                        break;
                    default:
                        binding.textView4.setText("Result: Unknown(" + result.getResultCode() + ")");
                        break;
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding= ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.button1.setOnClickListener(view ->{
            var intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        binding.button2.setOnClickListener(view ->{
            var intent2 = new Intent();
            intent2.setAction(Intent.ACTION_VIEW);
            intent2.setData(Uri.parse("https://maplestory.nexon.co.jp"));
            startActivity(intent2);
        });

        binding.button.setOnClickListener(view ->{
            var IE = binding.editTextText2.getText().toString();
            var intent3 = new Intent(this, MainActivity.class);
            intent3.putExtra("text",IE);
            startActivity(intent3);
        });
        binding.button5.setOnClickListener(view ->{
            var intent4 = new Intent(this, MainActivity.class);
            getActivityResult.launch(intent4);
        });
    }
}