package com.todo.menu;
import java.util.Scanner;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("( add )  Add a new item");
        System.out.println("( comp ) Check as completed");
        System.out.println("( Mcomp ) Check multiple items as completed");
        System.out.println("( del ) Delete an existing item");
        System.out.println("( Mdel ) Delete multiple items");
        System.out.println("( edit ) Update an item");
        System.out.println("( ls ) List all items");
        System.out.println("( ls_cate ) List all categories");
        System.out.println("( ls_comp ) List all completed items");
        System.out.println("( find ) Find related item");
        System.out.println("( find_cate ) Find items in Category");
        System.out.println("( ls_name ) sort the list by name");
        System.out.println("( ls_name_desc ) Descending sort the list by name");
        System.out.println("( ls_date ) sort the list by date");
        System.out.println("( ls_date_desc ) Descending sort the list by date");
        System.out.println("( prio ) Set as first-priority");
        System.out.println("( Mprio ) Set multiple items to first-priority");
        System.out.println("( ls_prio ) List all first-priority items");
        System.out.println("( today ) List all items due today");
        System.out.println("( monthly ) Add monthly item for input duration ");
        System.out.println("( exit or escape key) to exit\n");
        
    }
    public static void prompt() {
        System.out.print("Command > ");
    }
}
