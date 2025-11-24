package com.exemplo.forca; //

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RankingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        // 1. Vincular componentes da tela
        TextView txtValor = findViewById(R.id.txtValorRecorde);
        Button btnVoltar = findViewById(R.id.btnVoltar);

        // 2. Recuperar dados do SharedPreferences (Requisito do trabalho)
        SharedPreferences prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE);
        int recorde = prefs.getInt("recorde", 0); // 0 é o padrão se não tiver nada salvo

        // 3. Exibir na tela
        txtValor.setText(String.valueOf(recorde));

        // 4. Botão de voltar
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Fecha essa tela e volta para a anterior (Menu)
            }
        });
    }
}