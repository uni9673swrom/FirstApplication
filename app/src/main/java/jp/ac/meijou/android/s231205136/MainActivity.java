package jp.ac.meijou.android.s231205136;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import jp.ac.meijou.android.s231205136.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PrefDataStore prefDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
       binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefDataStore = PrefDataStore.getInstance(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.saveButton.setOnClickListener(view ->{
            var gap =binding.editTextText.getText().toString();
            prefDataStore.setString("name", gap);
        });

        binding.adc.setOnClickListener((view ->{
            var sup = binding.editTextText.getText().toString();
            binding.textView3.setText(sup);
        }));

        binding.button3.setOnClickListener(view ->{
            var top = "";
            prefDataStore.setString("name", top);
        });

        binding.button4.setOnClickListener(view ->{
            prefDataStore.getString("name")
                    .ifPresent(name -> binding.editTextText.setText(name));
        });

        binding.editTextText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.textView3.setText(editable.toString());
            }
        });

    }
    protected void onStart(){
        super.onStart();
        prefDataStore.getString("name")
                .ifPresent(name -> {
                    binding.textView3.setText(name);
                    binding.editTextText.setText(name);
                });
    }

    protected void onStop(){
        super.onStop();
        var gap =binding.editTextText.getText().toString();
        prefDataStore.setString("name", gap);
    }

}