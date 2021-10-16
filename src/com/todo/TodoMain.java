package com.todo;

import java.sql.*;
import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
		
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		//l.importData("todoList.txt");
		boolean isList = false;
		boolean quit = false;
		Menu.displaymenu();
		
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
				
			case "comp":
				int compIdx = sc.nextInt();
				TodoUtil.completeItem(l, compIdx);
				break;
				
			case "Mcomp":
				TodoUtil.completeItemP(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
			
			case "Mdel":
				TodoUtil.multiDelete(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;	
				
			case "ls_cate":
				TodoUtil.listAllCate(l);
				break;
				
			case "ls_comp":
				TodoUtil.listAll(l, 1);
				break;
				
			case "find":
				String keyword = sc.nextLine().trim();
				TodoUtil.findList(l, keyword);
				break;
				
			case "find_cate":
				String cate = sc.nextLine().trim();
				TodoUtil.findCateList(l, cate);
				break;
				
			case "ls_name":
				System.out.println("Ordered by title in ascending.");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("Ordered by title in descending.");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("Ordered by due_date in ascending.");
				TodoUtil.listAll(l, "due_date", 1);
				break;

			case "ls_date_desc":
				System.out.println("Ordered by due_date in descending.");
				TodoUtil.listAll(l, "due_date", 0);
				break;
				
			case "prio":
				int Idx = sc.nextInt();
				TodoUtil.setPriority(l, Idx);
				break;
				
			case "Mprio":
				TodoUtil.setPriorityP(l);
				break;
			
			case "ls_prio":
				TodoUtil.listAllP(l);
				break;
				
			case "today":
				TodoUtil.todaylists(l);
				break;
				
			case "monthly":
				TodoUtil.addMonthly(l);
				break;
	
			case "help":	
				Menu.displaymenu();
				break;
				
			case "exit":
				quit = true;
				break;

			default:
				System.out.println("정확한 명령어를 입력하세요. (도움말 - help)");
				break;
			}
			
			
		} while (!quit);
		
	System.out.println("Program ENDED");
	}
}
