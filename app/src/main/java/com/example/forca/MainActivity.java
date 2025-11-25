package com.example.forca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button btnJogar, btnRanking, btnConfig, btnTutorial;

    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences prefs = newBase.getSharedPreferences("ForcaPrefs", MODE_PRIVATE);
        String idioma = prefs.getString("idioma", "pt");
        
        Locale locale = new Locale(idioma);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        
        Context context = newBase.createConfigurationContext(config);
        super.attachBaseContext(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Encontrar os botões na tela
        btnJogar = findViewById(R.id.btnJogar);
        btnRanking = findViewById(R.id.btnRanking);
        btnConfig = findViewById(R.id.btnConfig);
        btnTutorial = findViewById(R.id.btnTutorial);

        // Ação do Botão JOGAR
        btnJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.msg_digite_nome));

                final android.widget.EditText input = new android.widget.EditText(MainActivity.this);
                input.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton(getString(R.string.msg_ok), new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        String nome = input.getText().toString();
                        if (!nome.trim().isEmpty()) {
                            Intent intent = new Intent(MainActivity.this, GameActivity.class);
                            intent.putExtra("NOME_JOGADOR", nome);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, getString(R.string.msg_digite_valido), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton(getString(R.string.msg_cancelar), new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        // Ação do Botão RANKING
        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RankingActivity.class));
            }
        });

        // Ação do Botão CONFIGURAÇÕES
        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ConfigActivity.class));
            }
        });

        // Ação do Botão TUTORIAL
        btnTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TutorialActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Checar se idioma mudou e recriar se necessário
        // Mas como o ConfigActivity já recria a pilha ou volta, talvez precise recarregar
        // Se voltarmos da ConfigActivity e mudou idioma, precisamos atualizar a UI
        // Uma forma simples é recriar a activity se necessário ou atualizar textos
        // Mas o attachBaseContext roda na criação.
        // Vamos deixar assim e ver se o fluxo Config -> Main atualiza. 
        // Geralmente Config deve reiniciar o app ou voltar pro Main com refresh.
        // No ConfigActivity atual, ele salva e faz finish(). O Main já está criado na stack.
        // O Main não vai chamar attachBaseContext de novo só no onResume.
        // Precisamos recriar o Main se o idioma mudou.
        
        SharedPreferences prefs = getSharedPreferences("ForcaPrefs", MODE_PRIVATE);
        String idiomaPref = prefs.getString("idioma", "pt");
        
        Locale current = getResources().getConfiguration().locale;
        if (!current.getLanguage().equals(idiomaPref)) {
            recreate();
        }
    }
}