package com.example.todolist;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.todolist.NoteDetailFragment.SELECTED_NOTE;


public class NotesFragment extends Fragment {

    Notes note;
    View dataContainer;



    public NotesFragment() {
        // Required empty public constructor
    }

    // сохраняем сотояние с индексом элемента массива записей
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(SELECTED_NOTE, note);
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
            //selectedIndex = savedInstanceState.getInt(SELECTED_NOTE, 0);
            // note = (Notes) savedInstanceState.getSerializable(SELECTED_NOTE);
            note = (Notes) savedInstanceState.getParcelable(SELECTED_NOTE);

        }

        dataContainer = view.findViewById(R.id.data_container);
        initNotes(dataContainer);

        // инициализация списка записей
        initNotes(view.findViewById(R.id.data_container));

        if (isLandscape()) {
            showLandNoteDetails(note);
        }
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    public void initNotes() {
        initNotes(dataContainer);
    }

    private void initNotes(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        layoutView.removeAllViews();
        for (int i = 0; i < Notes.getNotes().size(); i++) {

            TextView tv = new TextView(getContext());
            tv.setText(Notes.getNotes().get(i).getName());
            tv.setTextSize(28);
            layoutView.addView(tv);

            final int index = i;
            initPopupMenu(layoutView, tv, index); // вызываю раскрывающееся меню
            tv.setOnClickListener(view1 -> showNoteDetails(Notes.getNotes().get(index)));
        }
    }


    private void initPopupMenu(LinearLayout rootView, TextView view, int index){
        // продолжительное нажатие
        view.setOnLongClickListener(v -> {
            Activity activity = requireActivity();
            PopupMenu popupMenu = new PopupMenu(activity, view);
            activity.getMenuInflater().inflate(R.menu.notes_popup, popupMenu.getMenu());
            //подписываюсь на нажатие по кнопкам popupMenu
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.action_popup_delete:
                            Notes.getNotes().remove(index);
                            rootView.removeView(view);
                            break;
                    }
                    return true;
                }
            });
            popupMenu.show();
            return true;
        });
    }

    private void showNoteDetails(Notes note) {
        this.note = note;
        if (isLandscape()) {
            showLandNoteDetails(note);
        } else {
            showPortNoteDetails(note);
        }
    }

    /*private void showNoteDetails(int index) {
        selectedIndex = index;
        if (isLandscape()) {
            showLandNoteDetails(index);
        } else {
            showPortNoteDetails(index);
        }
    }*/

    // отрисовка списка заметок
    private void showPortNoteDetails(Notes note) {

        /*NoteDetailFragment noteDetailFragment = NoteDetailFragment.newInstance(index);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.notes_container, noteDetailFragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
        fragmentTransaction.commit();*/


        /*Activity activity = requireActivity();
        final Intent intent = new Intent(activity, NoteActivity.class);
        intent.putExtra(SELECTED_NOTE, note);
        activity.startActivity(intent);*/

        NoteDetailFragment noteDetailFragment = NoteDetailFragment.newInstance(note);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.notes_container, noteDetailFragment); // замена фрагмента
        fragmentTransaction.addToBackStack(" ");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }

    private void showLandNoteDetails(Notes note) {
        NoteDetailFragment noteDetailFragment = NoteDetailFragment.newInstance(note);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.notes_container, noteDetailFragment); // замена фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
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