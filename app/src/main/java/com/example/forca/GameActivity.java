package com.example.forca;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    // Elementos da tela
    private ImageView imgForca;
    private TextView txtPalavra, txtPontuacao, txtFeedback, txtTimer;
    private EditText edtLetra;
    private Button btnChutar, btnVoltar, btnReiniciar;

    // Lógica do jogo
    private String[] palavrasPt = {"XICARA", "CHAVE", "PASSARO", "CASA", "EXCELENTE", "NASCER"};
    private String[] palavrasEn = {"CUP", "KEY", "BIRD", "HOUSE", "EXCELLENT", "BIRTH"};
    private String[] palavrasAtuais;
    
    private String palavraSecreta;
    private char[] palavraExibida; // O array de underline (_ _ _)
    private int erros = 0;
    private int pontos = 0;
    private String nomeJogador;
    
    // Configurações
    private boolean modoCronometro = false;
    private CountDownTimer timer;
    private static final long TEMPO_LIMITE = 60000; // 60 segundos por palavra (exemplo)
    private int tamanhoFontePref = 1; // 0=pequeno, 1=medio, 2=grande

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
        
        // Aplicar Modo Escuro se configurado
        SharedPreferences prefs = getSharedPreferences("ForcaPrefs", MODE_PRIVATE);
        boolean modoEscuro = prefs.getBoolean("modoEscuro", false);
        if (modoEscuro) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_game);
        
        // Carregar outras configurações
        modoCronometro = prefs.getBoolean("cronometro", false);
        tamanhoFontePref = prefs.getInt("tamanhoFonte", 1);
        
        // Configurar idioma das palavras
        String idioma = prefs.getString("idioma", "pt");
        if (idioma.equals("en")) {
            palavrasAtuais = palavrasEn;
        } else {
            palavrasAtuais = palavrasPt;
        }
        
        // Recuperar o nome do jogador
        nomeJogador = getIntent().getStringExtra("NOME_JOGADOR");
        if (nomeJogador == null) {
            nomeJogador = "Anônimo";
        }

        // Vincular ID do XML
        imgForca = findViewById(R.id.imgForca);
        txtPalavra = findViewById(R.id.txtPalavra);
        txtPontuacao = findViewById(R.id.txtPontuacao);
        txtFeedback = findViewById(R.id.txtFeedback);
        txtTimer = findViewById(R.id.txtTimer); 
        edtLetra = findViewById(R.id.edtLetra);
        btnChutar = findViewById(R.id.btnChutar);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnReiniciar = findViewById(R.id.btnReiniciar);
        
        // Aplicar textos localizados manualmente (opcional, já que attachBaseContext deve cuidar da maioria)
        btnVoltar.setText(getString(R.string.btn_voltar));
        btnReiniciar.setText(getString(R.string.btn_reiniciar));
        btnChutar.setText(getString(R.string.btn_chutar));
        
        // Aplicar tamanho da fonte
        aplicarTamanhoFonte(tamanhoFontePref);
        
        // Configuração do Timer visual
        if (modoCronometro) {
            txtTimer.setVisibility(View.VISIBLE);
        } else {
            txtTimer.setVisibility(View.GONE);
        }

        iniciarJogo();

        btnChutar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String letraStr = edtLetra.getText().toString().toUpperCase();
                if (letraStr.length() > 0) {
                    verificarLetra(letraStr.charAt(0));
                    edtLetra.setText(""); // Limpa o campo
                } else {
                    Toast.makeText(GameActivity.this, getString(R.string.msg_digite_valido), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pontos = 0; 
                iniciarJogo();
                Toast.makeText(GameActivity.this, getString(R.string.msg_reiniciado), Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void aplicarTamanhoFonte(int pref) {
        float size = 32; // Padrão
        switch (pref) {
            case 0: size = 24; break;
            case 1: size = 32; break;
            case 2: size = 48; break; // Aumentado para ficar mais visível
        }
        // Utiliza TypedValue para garantir que a unidade seja SP
        txtPalavra.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    private void iniciarJogo() {
        erros = 0;
        Random random = new Random();
        palavraSecreta = palavrasAtuais[random.nextInt(palavrasAtuais.length)];

        palavraExibida = new char[palavraSecreta.length()];
        for (int i = 0; i < palavraExibida.length; i++) {
            palavraExibida[i] = '_';
        }

        atualizarTela();
        imgForca.setImageResource(R.drawable.forca_0); 
        txtFeedback.setText("");
        
        if (modoCronometro) {
            iniciarCronometro();
        }
    }
    
    private void iniciarCronometro() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new CountDownTimer(TEMPO_LIMITE, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtTimer.setText((millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                txtTimer.setText("0s");
                // Game Over por tempo
                erros = 6; // Força game over
                atualizarImagemForca();
            }
        }.start();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    private void verificarLetra(char letra) {
        boolean acertou = false;

        for (int i = 0; i < palavraSecreta.length(); i++) {
            if (palavraSecreta.charAt(i) == letra) {
                palavraExibida[i] = letra;
                acertou = true;
            }
        }

        if (acertou) {
            pontos += 10; 
            // Feedback poderia ser traduzido também, mas ok por enquanto
            txtFeedback.setText(letra + " ✓");
            txtFeedback.setTextColor(getResources().getColor(android.R.color.holo_green_dark));

            if (String.valueOf(palavraExibida).equals(palavraSecreta)) {
                if (timer != null) timer.cancel(); // Para o timer se ganhar
                Toast.makeText(this, getString(R.string.msg_venceu), Toast.LENGTH_LONG).show();
                pontos += 100;
                salvarPontuacao(pontos); 
                iniciarJogo(); 
            }
        } else {
            erros++;
            pontos -= 5;
            txtFeedback.setText(letra + " ✗");
            txtFeedback.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            atualizarImagemForca(); 
        }

        atualizarTela();
    }

    private void atualizarImagemForca() {
        switch (erros) {
            case 1: imgForca.setImageResource(R.drawable.forca_1); break;
            case 2: imgForca.setImageResource(R.drawable.forca_2); break;
            case 3: imgForca.setImageResource(R.drawable.forca_3); break;
            case 4: imgForca.setImageResource(R.drawable.forca_4); break;
            case 5: imgForca.setImageResource(R.drawable.forca_5); break;
            case 6:
                if (timer != null) timer.cancel();
                imgForca.setImageResource(R.drawable.forca_6);
                salvarPontuacao(pontos);
                mostrarDialogoGameOver();
                break;
        }
    }

    private void mostrarDialogoGameOver() {
        if (isFinishing()) return; 
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.msg_game_over));
        builder.setMessage(String.format(getString(R.string.msg_palavra_era), palavraSecreta, pontos));
        builder.setCancelable(false);

        builder.setPositiveButton(getString(R.string.app_name), new DialogInterface.OnClickListener() { // Usando app name como "Menu" provisorio ou criando string especifica
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish(); 
            }
        });
        // Ajuste: "Menu Principal" string
        
        builder.setNegativeButton(getString(R.string.btn_ranking), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(GameActivity.this, RankingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        builder.show();
    }

    private void atualizarTela() {
        StringBuilder display = new StringBuilder();
        for (char c : palavraExibida) {
            display.append(c).append(" ");
        }
        txtPalavra.setText(display.toString());
        txtPontuacao.setText(String.format(getString(R.string.label_pontos), pontos));
    }

    // Salvar pontuação no SQLite
    private void salvarPontuacao(int pontosFinais) {
        DatabaseHelper db = new DatabaseHelper(this);
        db.inserirPontuacao(nomeJogador, pontosFinais);
    }
}