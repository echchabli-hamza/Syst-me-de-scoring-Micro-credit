package util;

import entity.Person;

public class Session {



    private static Person user;


    public static Person getUser(){
        return user;
    }

    public static void setUser(Person p){
        user  = p ;
    }


}
