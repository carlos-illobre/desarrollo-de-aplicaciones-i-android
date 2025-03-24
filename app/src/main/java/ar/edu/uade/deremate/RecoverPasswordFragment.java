package ar.edu.uade.deremate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecoverPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecoverPasswordFragment extends Fragment {

    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private Button recoverButton;
    private Button cancelButton;
    private TextView messageText;

    public RecoverPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecoverPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecoverPasswordFragment newInstance() {
        RecoverPasswordFragment fragment = new RecoverPasswordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recover_password, container, false);

        //emailInput = view.findViewById(R.id.emailInput);


        return view;
    }
}