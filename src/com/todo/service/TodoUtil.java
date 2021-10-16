package com.todo.service;

import java.io.*;
import java.sql.Connection;
import java.util.*;
import java.text.SimpleDateFormat;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	Connection conn;
	public TodoUtil() {
		//this.list = new ArrayList<TodoItem>();
		this.conn = DbConnect.getConnection();
	}
	public static void createItem(TodoList list) {
		
		String title, desc, category, d_date, current_date, place;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== Create item Section\n"
				+ "enter the title");
		
		title = sc.next();
		
		if (list.isDuplicate(title)) {
			System.out.printf("title can't be duplicate\n");
			return;
		}
		
		System.out.print("enter the category ");
		
		category = sc.next();
		sc.nextLine();
		
		System.out.print("enter the description");
		desc = sc.nextLine().trim();
		
		System.out.print("enter the due date in (yyyy/mm/dd) format ");
		d_date = sc.nextLine().trim();
		
		System.out.print("enter the location ");
		place = sc.nextLine().trim();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
		current_date = sdf.format(new Date());
		
		TodoItem t = new TodoItem(title, desc, category, d_date, current_date, 0, 0, place);
		if(list.addItem(t)>0)
			System.out.println("Item added");
	}
	
	public static void addMonthly(TodoList list) {
		// TODO Auto-generated method stub
		String title, desc, category, d_date, current_date, place;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== Create item Section\n"
				+ "enter the title");
		
		title = sc.next();
		
		if (list.isDuplicate(title)) {
			System.out.printf("title can't be duplicate\n");
			return;
		}
		
		System.out.print("enter the category ");
		
		category = sc.next();
		sc.nextLine();
		
		System.out.print("enter the description");
		desc = sc.nextLine().trim();
		
		System.out.print("enter the initial date in (yyyy/mm/dd) format ");
		d_date = sc.nextLine().trim();
		
		System.out.print("enter the length of duration in months (Max: 12 months) ");
		int duration = sc.nextInt();
		sc.nextLine();
		
		if(duration > 12) {
			System.out.println("\n%%Duration OVERFLOW%%");
			return;
		}
		
		System.out.print("enter the location ");
		place = sc.nextLine().trim();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
		current_date = sdf.format(new Date());
		
		TodoItem t = new TodoItem(title, desc, category, d_date, current_date, 0, 0, place);
		if(list.addItemMontly(t, duration)>0)
			System.out.println("Items added");
	}
	

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "========== Delete Item Section\n"
				+ "enter the index of item to remove ");
		int n = sc.nextInt();
		if(l.deleteItem(n) > 0)
			System.out.println("Item deleted.");
	}
	public static void multiDelete(TodoList l) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "========== Delete Item Section\n"
				+ "enter the indexes to remove ");
		ArrayList<Integer> il = new ArrayList<Integer>();
		String line = sc.nextLine();
		String num[] = line.split(" ");
		int temp;
		for(int i=0; i<num.length; i++) {
			temp = Integer.parseInt(num[i]);
			il.add(temp);
		}
		int check=0;
		for(int idx: il){
			check = l.deleteItem(idx);
			if(check != 1) break;
		}
		if(check != 1) System.out.println("\nDeletion failed");
		else System.out.println("\n" + il.size()+" items are deleted");
	}
	
	public static void updateItem(TodoList list) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("========== Edit Item Section\n"
							+"Enter index of item to edit ");
		int n = sc.nextInt();
		
		System.out.print("enter the NEW title ");
		String title = sc.next().trim();	
		System.out.print("enter the NEW category ");
		String category = sc.next();
		sc.nextLine();
		System.out.print("enter the NEW description");
		String desc = sc.nextLine().trim();
		System.out.print("enter the NEW due date in (yyyy/mm/dd) format ");
		String d_date = sc.nextLine().trim();
		System.out.print("enter the location ");
		String place = sc.nextLine().trim();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
		String current_date = sdf.format(new Date());
		
		TodoItem t = new TodoItem(title, desc, category, d_date, current_date, 0, 0, place);
		t.setId(n);
		if(list.editItem(t)>0)
			System.out.println("Item updated\n");
	}

	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for(TodoItem item: l.getList()) {
			System.out.println(item.toString());
		}
	}
	public static void listAll(TodoList l, int comp) { // ls_comp
		int count=0;
		for(TodoItem item: l.getList(comp)) {
			if(item.getIs_completed() == 1) {
				System.out.println(item.toString());
				count++;
			}
		}
		System.out.printf("\nTotal %d items are completed\n", count);
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) { // ls_name or date
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for(TodoItem item: l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	public static void listAllP(TodoList l) {
		// TODO Auto-generated method stub
		int count=0;
		for(TodoItem item: l.getList()) {
			if(item.getFirst_priority() == 1) {
				System.out.println(item.toString());
				count++;
			}
		}
		System.out.printf("\nTotal %d items are first-priority\n", count);
	}
	
	public static void listAllCate(TodoList l) { // ls_cate
		int count = 0;
		for(String item: l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("\nTotal %d categories found!\n ", count);
	}
	
	public static void findItem(TodoList l, String s) {
		l.listFind(s);
	}
	
	public static void findCateList(TodoList l, String cate) {
		int count = 0;
		for(TodoItem item: l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\nTotal %d categories found.\n", count);
	}
	

	public static void findList(TodoList l, String keyword) {
		// TODO Auto-generated method stub
		int count = 0;
		for(TodoItem item: l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("TOTAL %d items found.\n", count);
		
	}
	public static void completeItem(TodoList l, int compIdx) {
		// TODO Auto-generated method stub
		if(l.completeItem(compIdx) != 0)
			System.out.println("Item completeness checked!");
		else System.out.println("Complete check failed!");
		
	}
	
	public static void completeItemP(TodoList l) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "Enter completed item indexes ");
		ArrayList<Integer> il = new ArrayList<Integer>();
		String line = sc.nextLine();
		String num[] = line.split(" ");
		int temp;
		for(int i=0; i<num.length; i++) {
			temp = Integer.parseInt(num[i]);
			il.add(temp);
		}
		int check=0;
		for(int idx: il){
			check = l.completeItem(idx);
			if(check != 1) break;
		}
		if(check != 1) System.out.println("\nCompleteness checking failed");
		else System.out.println("\n" + il.size()+" items are completed");
		
	}
	
	public static void setPriority(TodoList l, int idx) {
		if(l.setPriority(idx) != 0)
			System.out.println("Priority set completed!");
		else System.out.println("Priority set failed!");
	}
	public static void setPriorityP(TodoList l) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "Enter first-priority item indexes ");
		ArrayList<Integer> il = new ArrayList<Integer>();
		String line = sc.nextLine();
		String num[] = line.split(" ");
		int temp;
		for(int i=0; i<num.length; i++) {
			temp = Integer.parseInt(num[i]);
			il.add(temp);
		}
		int check=0;
		for(int idx: il){
			check = l.setPriority(idx);
			if(check != 1) break;
		}
		if(check != 1) System.out.println("\nPriority setting failed");
		else System.out.println("\n" + il.size()+" items are added as first-priority");
		
	}
	public static void todaylists(TodoList l) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String today = sdf.format(new Date());
        ArrayList<TodoItem> dl = l.getListToday(today);
        
        ArrayList<TodoItem> Comp = extractCompFromAL(dl, 1);
        ArrayList<TodoItem> NotComp = extractCompFromAL(dl, 0);
        
        ArrayList<TodoItem> firstPrio = extractPrioFromAL(NotComp, 1);
        ArrayList<TodoItem> notPrio = extractPrioFromAL(NotComp, 0);
        
        int total = dl.size();
        int left = NotComp.size();
        System.out.println("<DUE-LIST>\nToday: " + today + "\n << ("+left+"/"+total+") >>");
        
        
        if(firstPrio.size()>0) {
        	System.out.println("# First-Priority: ");
        	for(TodoItem item: firstPrio) 
            	System.out.println(item.toStringToday());
        }
        
        if(notPrio.size()>0) {
        	System.out.println("# Not-Priority: ");
        	for(TodoItem item: notPrio) 
            	System.out.println(item.toStringToday());
        }
        if(Comp.size()>0) {
        	System.out.println("# Completed: ");
        	for(TodoItem item: Comp) 
             	System.out.println(item.toStringToday());
        }
       
	}
	private static ArrayList<TodoItem> extractCompFromAL(ArrayList<TodoItem> dl, int i) {
		// TODO Auto-generated method stub
		ArrayList<TodoItem> t = new ArrayList<TodoItem>();
		for(TodoItem item: dl) {
			if(item.getIs_completed() == i)
				t.add(item);
		}
		return t;
	}

	private static ArrayList<TodoItem> extractPrioFromAL(ArrayList<TodoItem> comp, int i) {
		// TODO Auto-generated method stub
		ArrayList<TodoItem> t = new ArrayList<TodoItem>();
		for(TodoItem item: comp) {
			if(item.getFirst_priority() == i)
				t.add(item);
		}
		return t;
	}
}
