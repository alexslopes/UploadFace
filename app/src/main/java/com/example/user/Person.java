package com.example.user;

public class Person {

    private static Person person = null;
    private static int id;
    private static String name;
    private static String login;
    private static String password;

    private Person(int id, String name, String login, String password) {
        setId(id);
        setLogin(login);
        setName(name);
        setPassword(password);
    }

    public static Person createPerson(int id, String name, String login, String password){
        if(person==null){
            new Person(id, name,login, password);
        }
        return person;
    }

    public static void deleteUser(){
        person = null;
    }

    public static int getId() {
        return id;
    }

    private static void setId(int id) {
        Person.id = id;
    }

    public static String getName() {
        return name;
    }

    private static void setName(String name) {
        Person.name = name;
    }

    public static String getLogin() {
        return login;
    }

    private static void setLogin(String login) {
        Person.login = login;
    }

    public static String getPassword() {
        return password;
    }

    private static void setPassword(String password) {
        Person.password = password;
    }
}
