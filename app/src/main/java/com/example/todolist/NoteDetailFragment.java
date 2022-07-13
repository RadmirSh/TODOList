package com.example.todolist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NoteDetailFragment extends Fragment {

    static final String ARG_INDEX = "index";
    private Notes notes;


    public NoteDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            requireActivity().getSupportFragmentManager().popBackStack();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {

            // получаю текущий индекс записи из массива
            int index = arguments.getInt(ARG_INDEX);

            TextView tvName = view.findViewById(R.id.tvName);
            tvName.setText(Notes.getNotes()[index].getName());

            // подписываюсь на ChangeListener
            tvName.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                // изменяю Name объекта
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Notes.getNotes()[index].setName(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            TextView tvContent = view.findViewById(R.id.tvContent);
            tvContent.setText(Notes.getNotes()[index].getContent());
        }
    }

    public static NoteDetailFragment newInstance(int index) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;

    }
}