package meutreino.com.br.meutreino.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import meutreino.com.br.meutreino.entidade.Usuario;

/**
 * Created by thiago on 05/04/15.
 */
public class USuarioHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "meutreino";

    private static final String NOME_TABLEA = "usuario";
    private static final String COLUNA_ID = "id";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_EMAIL = "email";

    public USuarioHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + NOME_TABLEA +
                " (" + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                " " + COLUNA_NOME + " TEXT," +
                " " + COLUNA_EMAIL + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + NOME_TABLEA;
        db.execSQL(sql);
        this.onCreate(db);
    }

    public long inserir(Usuario user) {
        SQLiteDatabase db = this.getWritableDatabase();
        long envio = -1L;
        try {
            ContentValues values = new ContentValues();
            values.put(COLUNA_NOME, user.getNome());
            values.put(COLUNA_EMAIL, user.getEmail());
            db.delete(NOME_TABLEA, null, null);
            envio = db.insert(NOME_TABLEA, null, values);
        } catch (Exception e) {
            Log.i("ERRO", "inserir()" + e.getMessage());
        } finally {
            db.close();
        }
        return envio;
    }

    public List<Usuario> buscarTodos() {
        List<Usuario> users = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String sql = "SELECT * FROM " + NOME_TABLEA;
            Cursor cursor = db.rawQuery(sql, null);
            Usuario user = null;
            if (cursor.moveToFirst()) {
                do {
                    user = new Usuario(null, cursor.getString(1), cursor.getString(2));
                    users.add(user);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.i("ERRO", "buscarTodos()" + e.getMessage());
        } finally {
            db.close();
        }
        return users;
    }
}
