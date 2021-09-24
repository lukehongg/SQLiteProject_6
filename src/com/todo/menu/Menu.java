package com.todo.menu;
import java.util.Scanner;
public class Menu {

    public static void displaymenu()
    {

        
        System.out.println();
        System.out.println("( add )  Add a new item");
        System.out.println("( del ) Delete an existing item");
        System.out.println("( edit ) Update an item");
        System.out.println("( ls ) List all items");
        System.out.println("( ls_cate ) List all categories");
        System.out.println("( find ) Find related item");
        System.out.println("( find_cate ) Find items in Category");
        System.out.println("( ls_name_asc ) sort the list by name");
        System.out.println("( ls_name_desc ) Descending sort the list by name");
        System.out.println("( ls_date ) sort the list by date");
        System.out.println("( ls_date_desc ) Descending sort the list by date");
        System.out.println("( exit or escape key) to exit\n");
        
    }
    public static void prompt() {
        System.out.print("Command > ");
    }
}
