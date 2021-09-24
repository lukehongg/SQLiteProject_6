package com.todo.service;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, cate, d_date, current_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== Create item Section\n"
				+ "enter the title");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("title can't be duplicate");
			return;
		}
		sc.nextLine(); // title 후 남아있는 \n제거
		System.out.print("enter the category ");
		
		cate = sc.next();
		sc.nextLine();
		
		System.out.print("enter the description");
		desc = sc.nextLine().trim();
		
		System.out.print("enter the due date in (yyyy/mm/dd) format ");
		d_date = sc.nextLine().trim();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
		current_date = sdf.format(new Date());
		
		TodoItem t = new TodoItem(title, desc, cate, d_date, current_date);
		list.addItem(t);
		System.out.println("Item added");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "========== Delete Item Section\n"
				+ "enter the index of item to remove ");
		int n = sc.nextInt();
		TodoItem it = l.getItemWithIndex(n);
		System.out.println(n+". "+ it.toString());
		System.out.print("위 항목을 삭제하시겠습니까? (y/n) ");
		String ans = sc.next().trim();
		if(ans.equals("y")) {
			l.deleteItem(it);
			System.out.println("Item removed\n");
		}
		else System.out.println("Removal canceled\n");
	}
	
	public static void updateItem(TodoList list) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("========== Edit Item Section\n"
							+"Enter index of item to edit ");
		int n = sc.nextInt();
		TodoItem it = list.getItemWithIndex(n);
		System.out.println(n + ". " + it.toString());
		System.out.print("enter the NEW title ");
		String title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("title can't be duplicate");
			return;
		}
		sc.nextLine(); // title 후 남아있는 \n제거
		System.out.print("enter the NEW category ");
		String cate = sc.next();
		sc.nextLine();
		System.out.print("enter the NEW description");
		String desc = sc.nextLine().trim();
		System.out.print("enter the NEW due date in (yyyy/mm/dd) format ");
		String d_date = sc.nextLine().trim();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
		String current_date = sdf.format(new Date());
		
		list.deleteItem(it);
		TodoItem t = new TodoItem(title, desc, cate, d_date, current_date);
		list.addItem(t);
		System.out.println("Item updated\n");
	}

	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getListSize());
		int count=1;
		for (TodoItem item : l.getList()) {
			System.out.println(count + ". "+ item.toString());
			count++;
		}
	}
	public static void listAllCate(TodoList l) {
		Set<String> hs = new HashSet<String>();
		for (TodoItem item : l.getList()) {
			hs.add(item.getCategory());
		}
		int count=0;
		if(hs.size()>0) {
			Iterator it = hs.iterator();
			while(it.hasNext()) {
				count++;
				if(count != hs.size())
					System.out.print(it.next()+" / ");
				else System.out.print(it.next());

			}
			System.out.print("\n");
		}
	}
	
	public static void findItem(TodoList l, String s) {
		l.listFind(s);
	}
	public static void findItemCate(TodoList l, String s) {
		l.listFindCate(s);
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
