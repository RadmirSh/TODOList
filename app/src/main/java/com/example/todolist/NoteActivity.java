package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import static com.example.todolist.NoteDetailFragment.SELECTED_NOTE;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

         if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }

        if (savedInstanceState == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.note_fragment_container, NoteDetailFragment.newInstance(getIntent().getExtras().getParcelable(SELECTED_NOTE)))
                    .commit();



    }
}