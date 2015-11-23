package br.com.meutreino.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.meutreino.app.MainActivity;
import br.com.meutreino.app.R;

/**
 * Created by thiago on 04/04/15.
 */
public class SobreFragment extends Fragment {

    public static SobreFragment newInstance() {
        SobreFragment fragment = new SobreFragment();
        return fragment;
    }

    public SobreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmetn_sobre, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(3);
    }
}
