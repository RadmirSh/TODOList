package com.example.todolist;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Notes implements Parcelable {

    private int id; // идентификатор записи
    private static final Random random = new Random();

    private static ArrayList<Notes> notes;
    private static int counter;

    private String name;
    private String content;
    private LocalDateTime creationDate;


    public int getId() {
        return id;
    }

    public static ArrayList<Notes> getNotes() {
        return notes;
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

    // инициализирую запись внутренним идентификатором
    {
        id = ++counter;
    }
    // инициализатор отрабатывает в первую очередь
    // инициализирую массив из 5 заметок (через фабричный метод getNotes())
    static {
        notes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            notes.add(Notes.getNotes(i));
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    protected Notes(Parcel parcel) {
        id = parcel.readInt();
        name = parcel.readString();
        content = parcel.readString();
        creationDate = (LocalDateTime)parcel.readSerializable();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(getId());
        parcel.writeString(getName());
        parcel.writeString(getContent());
        parcel.writeSerializable(getCreationDate());
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };

}