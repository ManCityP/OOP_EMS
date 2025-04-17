package com.fhm.oop_ems;

import p1.*;
import p3.Gender;

import java.sql.ResultSet;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Database.Connect();

        Database.CloseConnection();
    }
}