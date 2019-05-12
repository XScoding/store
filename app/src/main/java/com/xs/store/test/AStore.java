package com.xs.store.test;

import com.xs.store.Store;

/**
 * Created by wangzhengtao767 on 2019/5/8.
 */

public class AStore implements Store {


    private String name = "";

    private int age;

    private Idea idea = new Idea();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }

    public static class Idea{

        private String message = "";


        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
