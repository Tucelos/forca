package com.example.forca;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnJogar, btnRanking, btnConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Liga com o XML acima

        // Encontrar os botões na tela
        btnJogar = findViewById(R.id.btnJogar);
        btnRanking = findViewById(R.id.btnRanking);
        btnConfig = findViewById(R.id.btnConfig);

        // Ação do Botão JOGAR
        btnJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria a intenção de abrir a tela do jogo (GameActivity)
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        // Ação do Botão RANKING (Deixei pronto para não dar erro)
        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se você criou a RankingActivity, descomente a linha abaixo:
                startActivity(new Intent(MainActivity.this, RankingActivity.class));

                // Por enquanto mostra uma mensagem rápida
                //Toast.makeText(MainActivity.this, "Ranking em construção!", Toast.LENGTH_SHORT).show();
            }
        });

        // Ação do Botão CONFIGURAÇÕES
        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se você criou a SettingsActivity, use a Intent aqui
                Toast.makeText(MainActivity.this, "Configurações em construção!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}