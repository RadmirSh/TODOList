package com.example.todolist;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Random;

public class Notes {

    private static final Random random = new Random();
    private static Notes[] notes;
    private String name;
    private String content;
    private LocalDateTime creationDate;


    public Notes() {

    }

    public static Notes[] getNotes() {
        return notes;
    }

    // инициализатор отрабатывает в первую очередь
    // инициализирую массив из 5 заметок (через фабричный метод getNotes())
    static {
        notes = new Notes[5];
        for (int i = 0; i < notes.length; i++) {
            notes[i] = Notes.getNotes(i);
        }
    }

    public Notes(String name, String content, LocalDateTime creationDate) {
        this.name = name;
        this.content = content;
        this.creationDate = creationDate;
    }

    // фабричный метод возвращает заметку(через этот метод можно генерировать заметки)
    @SuppressLint("DefaultLocale")
    public static Notes getNotes(int index) {
        String name = String.format("Заметка %d", index);
        String content = String.format("Описание заметки %d", index);
        LocalDateTime creationDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            creationDate = LocalDateTime.now().plusDays(-random.nextInt(5));
        }
        return new Notes(name, content, creationDate);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime createDate) {
        this.creationDate = creationDate;
    }
}