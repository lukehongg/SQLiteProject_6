package com.todo.service;

import java.io.*;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== Create item Section\n"
				+ "enter the title\n");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("title can't be duplicate");
			return;
		}
		sc.nextLine(); // title 후 남아있는 \n제거
		System.out.println("enter the description");
		desc = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String title = sc.next();
		
		System.out.println("\n"
				+ "========== Delete Item Section\n"
				+ "enter the title of item to remove\n"
				+ "\n");
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== Edit Item Section\n"
				+ "enter the title of the item you want to update\n"
				+ "\n");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("title doesn't exist");
			return;
		}

		System.out.println("enter the new title of the item");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("title can't be duplicate");
			return;
		}
		
		System.out.println("enter the new description ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("item updated");
			}
		}

	}

	public static void listAll(TodoList l) {
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		int count=0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String newLine;
			StringTokenizer st;
			while((newLine = br.readLine()) != null) {
				count++;
				st = new StringTokenizer(newLine, "##");
				String title = st.nextToken();
				String desc = st.nextToken();
				String date = st.nextToken();
				l.addItem(new TodoItem(title, desc, date));
			}
			br.close();
			if(count == 0) System.out.println(filename + "파일이 없습니다.");
			if(count != 0) System.out.println(count+"개의 항목을 읽었습니다.");
			
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
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
	
	
	
}
