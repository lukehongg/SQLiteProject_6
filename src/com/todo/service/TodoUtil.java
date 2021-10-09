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
		
		String title, desc, category, d_date, current_date;
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
		current_date = sdf.format(new Date());
		
		TodoItem t = new TodoItem(title, desc, category, d_date, current_date,0);
		if(list.addItem(t)>0)
			System.out.println("Item added");
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
		String current_date = sdf.format(new Date());
		
		TodoItem t = new TodoItem(title, desc, category, d_date, current_date, 0);
		t.setId(n);
		if(list.editItem(t)>0)
			System.out.println("Item updated\n");
	}

	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for(TodoItem item: l.getList()) {
			if(item.getIs_completed() == 1) {
				System.out.println(item.toString(1));
			}
			else System.out.println(item.toString());
		}
	}
	public static void listAll(TodoList l, int comp) {
		int count=0;
		for(TodoItem item: l.getList(comp)) {
			if(item.getIs_completed() == 1) {
				System.out.println(item.toString(1));
				count++;
			}
		}
		System.out.printf("\nTotal %d items are completed\n", count);
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for(TodoItem item: l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	public static void listAllCate(TodoList l) {
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
	
	/*
	public static void loadList(TodoList l, String filename) {
		int count=0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String newLine;
			StringTokenizer st;
			while((newLine = br.readLine()) != null) {
				count++;
				st = new StringTokenizer(newLine, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String added_date = st.nextToken();
				l.addItem(new TodoItem(title, desc, category, due_date, added_date));
			}
			br.close();
			//if(count == 0) System.out.println(filename + "파일이 없습니다.");
			if(count != 0) System.out.println(count+"개의 항목을 읽었습니다.");
			
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(filename + "파일이 없습니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	*/
	/*
	public static void saveList(TodoList l, String filename) {
		try {
			FileWriter fw = new FileWriter(filename);
			for(TodoItem x: l.getList()) 
				fw.write(x.toSaveString());
			System.out.println("모든 데이터가 저장되었습니다.");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/

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
	
	
	
}
