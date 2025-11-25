package com.example.forca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "forca.db";
    private static final int DATABASE_VERSION = 1;

    // Nome da tabela e colunas
    public static final String TABLE_RANKING = "ranking";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_PONTOS = "pontos";

    // SQL de criação da tabela
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_RANKING + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NOME + " TEXT, " +
            COLUMN_PONTOS + " INTEGER);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RANKING);
        onCreate(db);
    }

    // Inserir nova pontuação
    public void inserirPontuacao(String nome, int pontos) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, nome);
        values.put(COLUMN_PONTOS, pontos);
        db.insert(TABLE_RANKING, null, values);
        db.close();
    }

    // Obter Top 5
    public List<String> obterTop5() {
        List<String> ranking = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        // Query para pegar os 5 melhores ordenados por pontos decrescente
        Cursor cursor = db.query(TABLE_RANKING, 
                new String[]{COLUMN_NOME, COLUMN_PONTOS}, 
                null, null, null, null, 
                COLUMN_PONTOS + " DESC", "5");

        if (cursor.moveToFirst()) {
            int rank = 1;
            do {
                String nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME));
                int pontos = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PONTOS));
                ranking.add(rank + ". " + nome + " - " + pontos + " pts");
                rank++;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ranking;
    }
}
