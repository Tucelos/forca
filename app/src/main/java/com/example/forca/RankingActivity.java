package com.example.forca;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        // 1. Vincular componentes da tela
        TextView txtValor = findViewById(R.id.txtValorRecorde);
        Button btnVoltar = findViewById(R.id.btnVoltar);

        // 2. Recuperar dados do Banco de Dados
        DatabaseHelper db = new DatabaseHelper(this);
        List<String> top5 = db.obterTop5();

        // 3. Exibir na tela
        if (top5.isEmpty()) {
            txtValor.setText("Sem pontuações ainda.");
            txtValor.setTextSize(20); // Ajustar tamanho se necessário
        } else {
            StringBuilder sb = new StringBuilder();
            for (String s : top5) {
                sb.append(s).append("\n");
            }
            txtValor.setText(sb.toString());
            txtValor.setTextSize(20); // Ajustar tamanho para caber a lista
        }

        // 4. Botão de voltar
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Fecha essa tela e volta para a anterior (Menu)
            }
        });
    }
}