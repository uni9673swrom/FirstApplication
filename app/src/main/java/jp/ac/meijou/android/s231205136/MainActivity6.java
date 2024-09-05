package jp.ac.meijou.android.s231205136;

import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.stream.Collectors;

import jp.ac.meijou.android.s231205136.databinding.ActivityMain6Binding;
import jp.ac.meijou.android.s231205136.databinding.ActivityMainBinding;
import okhttp3.OkHttpClient;

public class MainActivity6 extends AppCompatActivity {
    private ConnectivityManager connectivityManager;
    private ActivityMain6Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMain6Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        connectivityManager = getSystemService(ConnectivityManager.class);

        var currentNetwork = connectivityManager.getActiveNetwork();

        updateTransport(currentNetwork);
        updateIpAddress(currentNetwork);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void updateTransport(Network network){
        var caps = connectivityManager.getNetworkCapabilities(network);

        if(caps!=null){
            String transport;
            if(caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                transport = "モバイル通信";
            }else if(caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                transport ="WiFi";
            }else{
                transport = "その他";
            }
            binding.summer.setText(transport);
        }
    }

    private void updateIpAddress(Network network){
        var linkProperties = connectivityManager.getLinkProperties(network);
        if(linkProperties!=null){
            var addresses = linkProperties.getLinkAddresses().stream()
                    .map(LinkAddress::toString)
                    .collect(Collectors.joining("\n"));
            binding.winter.setText(addresses);
        }
    }
}