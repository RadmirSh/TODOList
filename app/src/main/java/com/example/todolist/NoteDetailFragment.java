package com.example.todolist;

import android.content.res.Configuration;
import android.media.VolumeShaper;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static com.example.todolist.NoteDetailFragment.SELECTED_NOTE;

import java.util.Arrays;
import java.util.Optional;

public class NoteDetailFragment extends Fragment {

    static final String SELECTED_NOTE = "note";
    private Notes note;


    public NoteDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (savedInstanceState != null)
            requireActivity().getSupportFragmentManager().popBackStack();*/
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.note_menu, menu);

        MenuItem menuItemExit = menu.findItem(R.id.action_exit);
        if (menuItemExit != null)
            menuItemExit.setVisible(false);        //видимость для action_exit

        MenuItem menuItemAbout = menu.findItem(R.id.action_about);
        if (menuItemAbout != null)
            menuItemAbout.setVisible(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_delete) {         //получаю ссылку на обект item

            Notes.getNotes().remove(note); //удаляю элемент из коллекции
            updateData();
            if (!isLandscape()) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    //метод для определения текущей ориентации
    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState == null)
            setHasOptionsMenu(true); //для управления меню, определенной в рамках главной активити
        return inflater.inflate(R.layout.fragment_note_detail, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();

        Button buttonBack = view.findViewById(R.id.btnBack);
        if (buttonBack != null)

            buttonBack.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().popBackStack());

        if (arguments != null) {
            Notes paramNote = (Notes) arguments.getParcelable(SELECTED_NOTE);

            if (paramNote != null) {
                Optional<Notes> selectedNote = Notes.getNotes().stream().filter(n -> n.getId() == paramNote.getId()).findFirst();

               /* if (selectedNote.isPresent()) {
                    note = selectedNote.get(); //возвращаю и сохраняю конкретный объект из коллекции
                } else {
                    note = Notes.getNotes().get(0);
                }*/

                //возвращаю и сохраняю конкретный объект из коллекции
                note = selectedNote.orElseGet(() -> Notes.getNotes().get(0));

                //note = selectedNote.orElseGet(() -> Notes.getNotes().get(0)); //в случае удаления(сохранения) элемента выделяю первый элемент из списка
            }
            // возвращаю ссылку на объект с помощью предиката
            //сохраняю ссылку в переменную note
            //note = Notes.getNotes().stream().filter(n -> n.getId() == paramNote.getId()).findFirst().get();

            TextView tvName = view.findViewById(R.id.tvName);
            tvName.setText(note.getName());

            // подписываюсь на ChangeListener
            tvName.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                // изменяю Name объекта
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // запись значения Name
                    note.setName(tvName.getText().toString());
                    updateData();
                    // Notes.getNotes()[index].setName(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            TextView tvContent = view.findViewById(R.id.tvContent);
            tvContent.setText(note.getContent());
        }
    }

    //возвращаю фрагменты NotesFragment
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateData() {
        NotesFragment notesFragment = (NotesFragment) requireActivity().getSupportFragmentManager().getFragments().stream().filter(fragment -> fragment instanceof NotesFragment)
                .findFirst().get();
        notesFragment.initNotes();

    }

    public static NoteDetailFragment newInstance(int index) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putInt(SELECTED_NOTE, index);
        fragment.setArguments(args);
        return fragment;

    }

    public static NoteDetailFragment newInstance(Notes note) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(SELECTED_NOTE, note);
        fragment.setArguments(args);
        return fragment;

    }
}