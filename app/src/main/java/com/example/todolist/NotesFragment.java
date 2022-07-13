package com.example.todolist;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class NotesFragment extends Fragment {

    Notes notes;
    View dataContainer;


    static final String SELECTED_INDEX = "index";
    int selectedIndex = 0;

    public NotesFragment() {
        // Required empty public constructor
    }

    // сохраняем сотояние с индексом элемента массива записей
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SELECTED_INDEX, selectedIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // проверяем текущее состояние
        if (savedInstanceState != null) {
            selectedIndex = savedInstanceState.getInt(SELECTED_INDEX, 0);
        }

        // инициализация списка записей
        initNotes(view.findViewById(R.id.data_container));

        if (isLandscape()) {
            showLandNoteDetails(selectedIndex);
        }
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void initNotes(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        for (int i = 0; i < Notes.getNotes().length; i++) {

            TextView tv = new TextView(getContext());
            tv.setText(Notes.getNotes()[i].getName());
            tv.setTextSize(28);
            layoutView.addView(tv);

            final int index = i;
            tv.setOnClickListener(view1 -> showNoteDetails(index));
        }
    }

    private void showNoteDetails(int index){
        selectedIndex = index;
        if (isLandscape()){
            showLandNoteDetails(index);
        } else {
            showPortNoteDetails(index);
        }
    }

    // отрисовка списка заметок
    private void showPortNoteDetails(int index) {
        NoteDetailFragment noteDetailFragment = NoteDetailFragment.newInstance(index);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.notes_container, noteDetailFragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
        fragmentTransaction.commit();

    }

    // отрисовка дополнительного фрагмента
    private void showLandNoteDetails(int index) {
        NoteDetailFragment noteDetailFragment = NoteDetailFragment.newInstance(index);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.notes_container, noteDetailFragment); // замена фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}