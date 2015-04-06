package meutreino.com.br.meutreino.br.com.meutreino.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import meutreino.com.br.meutreino.MainActivity;
import meutreino.com.br.meutreino.R;
import meutreino.com.br.meutreino.dao.USuarioHelper;
import meutreino.com.br.meutreino.entidade.Usuario;

/**
 * Created by thiago on 05/04/15.
 */
public class ConfiguracaoFragment extends Fragment implements View.OnClickListener {

    Button button;
    EditText nome;
    EditText email;

    public static ConfiguracaoFragment newInstance() {
        ConfiguracaoFragment fragment = new ConfiguracaoFragment();
        return fragment;
    }

    public ConfiguracaoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_configuracao, container, false);
        button = (Button) rootView.findViewById(R.id.btnSalvar);
        nome = (EditText) rootView.findViewById(R.id.nome);
        email = (EditText) rootView.findViewById(R.id.email);
        button.setOnClickListener(this);
        buscarUsuario();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(2);
    }

    @Override
    public void onClick(View v) {
        Usuario user = new Usuario();
        if (nome.getText().toString() != null && !"".equals(nome.getText().toString().trim())) {
            user.setNome(nome.getText().toString());
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Inserir um nome.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.getText().toString() != null && !"".equals(email.getText().toString().trim())) {
            user.setEmail(email.getText().toString());
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Inserir um e-mail.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        USuarioHelper userDAO = new USuarioHelper(getActivity().getApplicationContext());
        Long salvo = userDAO.inserir(user);
        if (salvo > 0) {
            Toast.makeText(getActivity().getApplicationContext(), "Aluno " + user.getNome() +
                            " cadastrado com sucesso!",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Aluno não cadastrado.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void buscarUsuario() {
        USuarioHelper userDAO = new USuarioHelper(getActivity().getApplicationContext());
        List<Usuario> users = userDAO.buscarTodos();
        if (users != null && !users.isEmpty()) {
            nome.setText(users.get(0).getNome());
            email.setText(users.get(0).getEmail());
        }
    }
}
