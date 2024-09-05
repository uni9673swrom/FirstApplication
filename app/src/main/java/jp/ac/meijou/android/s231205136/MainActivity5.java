package jp.ac.meijou.android.s231205136;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.PixelCopy;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import jp.ac.meijou.android.s231205136.databinding.ActivityMain5Binding;
import jp.ac.meijou.android.s231205136.databinding.ActivityMainBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity5 extends AppCompatActivity {
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final Moshi moshi = new Moshi.Builder().build();
    private final JsonAdapter<Gist> gistJsonAdapter = moshi.adapter(Gist.class);
    public ActivityMain5Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding =ActivityMain5Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.button.setOnClickListener(view -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            var jet = binding.edit1.getText().toString();
            var snake = binding.edit2.getText().toString();

            var url = Uri.parse("https://placehold.jp/500x500.png")
                    .buildUpon()
                    .appendQueryParameter("text", jet)
                    .appendQueryParameter("backcolor",snake)
                    .build()
                    .toString();
            getImage(url);
        });
    }
    private void getImage(String url){
        var request =new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                var bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                runOnUiThread(()->{
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.image.setImageBitmap(bitmap);
                });
            }
        });
    }
}