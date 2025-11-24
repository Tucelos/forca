package com.exemplo.forca;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    // Elementos da tela
    private ImageView imgForca;
    private TextView txtPalavra, txtPontuacao, txtFeedback;
    private EditText edtLetra;
    private Button btnChutar;

    // Lógica do jogo
    // [cite: 78] Conjunto de palavras embutidas
    private String[] palavras = {"XICARA", "CHAVE", "PASSARO", "CASA", "EXCELENTE", "NASCER"};
    private String palavraSecreta;
    private char[] palavraExibida; // O array de underline (_ _ _)
    private int erros = 0;
    private int pontos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Vincular ID do XML
        imgForca = findViewById(R.id.imgForca);
        txtPalavra = findViewById(R.id.txtPalavra);
        txtPontuacao = findViewById(R.id.txtPontuacao);
        txtFeedback = findViewById(R.id.txtFeedback);
        edtLetra = findViewById(R.id.edtLetra);
        btnChutar = findViewById(R.id.btnChutar);

        iniciarJogo();

        btnChutar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String letraStr = edtLetra.getText().toString().toUpperCase();
                if (letraStr.length() > 0) {
                    verificarLetra(letraStr.charAt(0));
                    edtLetra.setText(""); // Limpa o campo
                } else {
                    Toast.makeText(GameActivity.this, "Digite uma letra!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void iniciarJogo() {
        erros = 0;
        // Sortear palavra [cite: 79]
        Random random = new Random();
        palavraSecreta = palavras[random.nextInt(palavras.length)];

        // Criar os traços "_" [cite: 80]
        palavraExibida = new char[palavraSecreta.length()];
        for (int i = 0; i < palavraExibida.length; i++) {
            palavraExibida[i] = '_';
        }

        atualizarTela();
        imgForca.setImageResource(R.drawable.forca_0); // Imagem inicial
        txtFeedback.setText("");
    }

    private void verificarLetra(char letra) {
        boolean acertou = false;

        // Verifica se a letra existe na palavra
        for (int i = 0; i < palavraSecreta.length(); i++) {
            if (palavraSecreta.charAt(i) == letra) {
                palavraExibida[i] = letra;
                acertou = true;
            }
        }

        if (acertou) {
            pontos += 10; // Sistema de pontuação [cite: 83]
            txtFeedback.setText("Acertou: " + letra);
            txtFeedback.setTextColor(getResources().getColor(android.R.color.holo_green_dark));

            // Verifica se ganhou (não tem mais underline)
            if (String.valueOf(palavraExibida).equals(palavraSecreta)) {
                Toast.makeText(this, "VOCÊ GANHOU! +100 Pontos", Toast.LENGTH_LONG).show();
                pontos += 100;
                salvarPontuacao(pontos); //  Salvar dados
                iniciarJogo(); // Reinicia
            }
        } else {
            erros++;
            pontos -= 5;
            txtFeedback.setText("Errou: " + letra);
            txtFeedback.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            atualizarImagemForca(); //
        }

        atualizarTela();
    }

    private void atualizarImagemForca() {
        // Troca a imagem baseada no erro. Certifique-se de ter as imagens forca_0 a forca_6 na pasta drawable
        switch (erros) {
            case 1: imgForca.setImageResource(R.drawable.forca_1); break;
            case 2: imgForca.setImageResource(R.drawable.forca_2); break;
            case 3: imgForca.setImageResource(R.drawable.forca_3); break;
            // ... adicione os outros casos
            case 6:
                imgForca.setImageResource(R.drawable.forca_6);
                Toast.makeText(this, "GAME OVER! A palavra era: " + palavraSecreta, Toast.LENGTH_LONG).show();
                salvarPontuacao(pontos);
                iniciarJogo(); // Reinicia
                break;
        }
    }

    private void atualizarTela() {
        // Adiciona espaço entre as letras para ficar bonito: _ _ A _
        StringBuilder display = new StringBuilder();
        for (char c : palavraExibida) {
            display.append(c).append(" ");
        }
        txtPalavra.setText(display.toString());
        txtPontuacao.setText("Pontos: " + pontos);
    }

    // [cite: 76, 87] SharedPreferences para salvar pontuação
    private void salvarPontuacao(int pontosFinais) {
        SharedPreferences prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE);
        int recordeAtual = prefs.getInt("recorde", 0);

        if (pontosFinais > recordeAtual) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("recorde", pontosFinais);
            editor.apply();
        }
    }
}