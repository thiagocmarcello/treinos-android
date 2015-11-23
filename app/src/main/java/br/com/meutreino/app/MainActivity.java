package br.com.meutreino.app;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import br.com.meutreino.app.fragment.ConfiguracaoFragment;
import br.com.meutreino.app.fragment.SobreFragment;
import br.com.meutreino.app.dao.USuarioHelper;
import br.com.meutreino.app.entidade.Treino;
import br.com.meutreino.app.entidade.Usuario;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private TextView treinoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buscarTreino();
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (position == 0) {
            buscarTreino();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                    .commit();
        } else if (position == 1) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, ConfiguracaoFragment.newInstance())
                    .commit();
        } else if (position == 2) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, SobreFragment.newInstance())
                    .commit();
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    private static final String ENDERECO = "http://54.94.216.94:8080/MeuTreinoServico/servico/get?email=";

    private void buscarTreino() {
        new HttpAsyncTask().execute(ENDERECO + buscarEmail().toUpperCase());
    }

    private String buscarEmail() {
        USuarioHelper userDAO = new USuarioHelper(getApplication());
        List<Usuario> usuarios = userDAO.buscarTodos();
        if (usuarios != null && usuarios.size() > 0) {
            return userDAO.buscarTodos().get(0).getEmail();
        } else {
            return "teste@gmail.com";
        }
    }

    /**
     * Metodo para receber GET
     *
     * @param url
     * @return
     */
    public static String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
            } else {
                result = "Did not work!";
            }
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (jsonArray != null) {
                    JSONObject jsonObject = null;
                    List<Treino> treinos = new ArrayList<Treino>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        Treino treino = new Treino();
                        treino.setNome(jsonObject.getString("nome"));
                        treino.setRepeticao(jsonObject.getInt("repeticao"));
                        treino.setSequencia(jsonObject.getInt("sequencia"));
                        treino.setDescricao(jsonObject.getString("descricao"));
                        treinos.add(treino);
                    }
                    StringBuilder sb = new StringBuilder();
                    String nomeTreino = "";
                    for (Treino t : treinos) {
                        if (!t.getNome().equals(nomeTreino)) {
                            nomeTreino = t.getNome();
                            sb.append("\n");
                            sb.append(t.getNome().toUpperCase());
                            sb.append("\n");
                        }
                        sb.append("- ");
                        sb.append(t.getDescricao());
                        sb.append(" ");
                        sb.append(t.getSequencia());
                        sb.append("x de ");
                        sb.append(t.getRepeticao());
                        sb.append("\n");
                    }
                    treinoTextView = (TextView) findViewById(R.id.textTreino);
                    if (sb.length() > 0) {
                        treinoTextView.setText(sb.toString());
                    } else {
                        treinoTextView.setText("Sem treino para o momento.");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
