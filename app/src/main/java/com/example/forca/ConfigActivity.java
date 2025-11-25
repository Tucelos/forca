package com.example.forca;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class ConfigActivity extends AppCompatActivity {

    private Switch switchSom, switchCronometro, switchModoEscuro;
    private SeekBar seekTamanhoFonte;
    private RadioGroup radioGroupIdioma;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Aplicar tema antes de setContentView
        prefs = getSharedPreferences("ForcaPrefs", MODE_PRIVATE);
        boolean modoEscuro = prefs.getBoolean("modoEscuro", false);
        if (modoEscuro) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_config);

        // Vincular componentes
        switchSom = findViewById(R.id.switchSom);
        switchCronometro = findViewById(R.id.switchCronometro);
        switchModoEscuro = findViewById(R.id.switchModoEscuro);
        seekTamanhoFonte = findViewById(R.id.seekTamanhoFonte);
        radioGroupIdioma = findViewById(R.id.radioGroupIdioma);
        Button btnSalvar = findViewById(R.id.btnSalvarConfig);
        Button btnJogar = findViewById(R.id.btnJogarConfig);

        // Carregar valores salvos
        carregarPreferencias();

        // Listeners
        btnSalvar.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View v) {
                salvarPreferencias();
                Toast.makeText(ConfigActivity.this, "Configurações salvas!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnJogar.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View v) {
                salvarPreferencias(); // Salva antes de jogar para garantir
                AlertDialog.Builder builder = new AlertDialog.Builder(ConfigActivity.this);
                builder.setTitle("Digite seu nome");

                final EditText input = new EditText(ConfigActivity.this);
                input.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        String nome = input.getText().toString();
                        if (!nome.trim().isEmpty()) {
                            Intent intent = new Intent(ConfigActivity.this, GameActivity.class);
                            intent.putExtra("NOME_JOGADOR", nome);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(ConfigActivity.this, "Por favor, digite um nome!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());
                builder.show();
            }
        });
    }

    private void carregarPreferencias() {
        switchSom.setChecked(prefs.getBoolean("som", true));
        switchCronometro.setChecked(prefs.getBoolean("cronometro", false));
        switchModoEscuro.setChecked(prefs.getBoolean("modoEscuro", false));
        seekTamanhoFonte.setProgress(prefs.getInt("tamanhoFonte", 1)); // 0=pequeno, 1=medio, 2=grande
        
        String idioma = prefs.getString("idioma", "pt");
        if (idioma.equals("en")) {
            radioGroupIdioma.check(R.id.radioEn);
        } else {
            radioGroupIdioma.check(R.id.radioPt);
        }
    }

    private void salvarPreferencias() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("som", switchSom.isChecked());
        editor.putBoolean("cronometro", switchCronometro.isChecked());
        
        boolean modoEscuroAntigo = prefs.getBoolean("modoEscuro", false);
        boolean modoEscuroNovo = switchModoEscuro.isChecked();
        editor.putBoolean("modoEscuro", modoEscuroNovo);
        
        editor.putInt("tamanhoFonte", seekTamanhoFonte.getProgress());
        
        int selectedId = radioGroupIdioma.getCheckedRadioButtonId();
        if (selectedId == R.id.radioEn) {
            editor.putString("idioma", "en");
        } else {
            editor.putString("idioma", "pt");
        }
        
        editor.apply();

        // Se mudou o modo escuro, recria a activity para aplicar (ou aplica globalmente)
        if (modoEscuroAntigo != modoEscuroNovo) {
            if (modoEscuroNovo) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            recreate(); // Opcional aqui, mas bom para ver efeito imediato se não fechar a tela
        }
    }
}