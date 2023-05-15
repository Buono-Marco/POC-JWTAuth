package com.example.pocjwtauth.dto.view;

public interface View {

    class User {}

    class Editor extends User {}

    class Admin extends Editor {}
}
