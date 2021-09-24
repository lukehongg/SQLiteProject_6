package com.todo.dao;

import java.util.*;
import com.todo.dao.TodoItem;

import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list;

	public TodoList() {
		this.list = new ArrayList<TodoItem>();
	}

	public void addItem(TodoItem t) {
		list.add(t);
	}

	public void deleteItem(TodoItem t) {
		list.remove(t);
	}

	void editItem(TodoItem t, TodoItem updated) {
		int index = list.indexOf(t);
		list.remove(index);
		list.add(updated);
	}
	
	public TodoItem getItemWithIndex(int n) {
		
		TodoItem it = list.get(n-1);
		return it; 
	}

	public ArrayList<TodoItem> getList() {
		return new ArrayList<TodoItem>(list);
	}
	
	public int getListSize() {
		return list.size();
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll() {
		System.out.println("\n"
				+ "inside list_All method\n");
		int count = 1;
		for (TodoItem myitem : list) {
			System.out.println(count + ". "+ myitem.toString());
			count++;
		}
	}
	public void listFind(String s) {
		int count=0;
		TodoItem temp;
		
		for(int i=0; i<list.size(); i++) {
			temp = list.get(i);
			if(temp.getTitle().contains(s)) {
				count++;
				System.out.printf("%d. %s\n", i+1, temp.toString());
			}
			else if(temp.getDesc().contains(s)){
				count++;
				System.out.printf("%d. %s\n", i+1, temp.toString());
			}
			else continue;
		}
		System.out.println("총 "+ count+"개의 항목을 찾았습니다.\n");
	}
	public void listFindCate(String s) {
		int count=0;
		TodoItem temp;
		for(int i=0; i<list.size(); i++) {
			temp = list.get(i);
			if(temp.getCategory().contains(s)) {
				count++;
				System.out.printf("%d. %s\n", i+1, temp.toString());
			}
		}
		System.out.println("총 "+ count+"개의 항목을 찾았습니다.\n");
	}
	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) {
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
}
