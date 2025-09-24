package com.example.examen;

import java.io.Serializable;

public class UserInfo implements Serializable {
    public String gender;
    public Name name;
    public Location location;
    public String email;
    public Login login;
    public Dob dob;
    public Registered registered;
    public String phone;
    public String cell;
    public Id id;
    public Picture picture;
    public String nat;

    public static class Name implements Serializable {
        public String title;
        public String first;
        public String last;
    }

    public static class Location implements Serializable {
        public Street street;
        public String city;
        public String state;
        public String country;
        public String postcode;
        public Coordinates coordinates;
        public Timezone timezone;

        public static class Street implements Serializable {
            public int number;
            public String name;
        }

        public static class Coordinates implements Serializable {
            public String latitude;
            public String longitude;
        }

        public static class Timezone implements Serializable {
            public String offset;
            public String description;
        }
    }

    public static class Login implements Serializable {
        public String uuid;
        public String username;
        public String password;
        public String salt;
        public String md5;
        public String sha1;
        public String sha256;
    }

    public static class Dob implements Serializable {
        public String date;
        public int age;
    }

    public static class Registered implements Serializable {
        public String date;
        public int age;
    }

    public static class Id implements Serializable {
        public String name;
        public String value;
    }

    public static class Picture implements Serializable {
        public String large;
        public String medium;
        public String thumbnail;
    }
}