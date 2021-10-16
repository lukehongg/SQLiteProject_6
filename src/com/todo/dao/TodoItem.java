package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private int id;
    private String title;
    private String desc;
    private String current_date;
    private String category;
    private String due_date;
    private int is_completed;
    private int first_priority;
    private String place;


    public int getIs_completed() {
		return is_completed;
	}
	public void setIs_completed(int is_completed) {
		this.is_completed = is_completed;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDue_date() {
		return due_date;
	}
	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}
	public TodoItem() {}
    public TodoItem(String title, String desc, String cate, String due_date, String current_date, int is_comp, int first_priority, String place) {
    	this.title=title;
        this.desc=desc;
        this.category = cate;
        this.due_date = due_date;
        this.current_date = current_date;
        this.is_completed = is_comp;
        this.first_priority = first_priority;
        this.place = place;
    }
    
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    
    @Override
    public String toString() {
    	String temp = title;
    	if(first_priority == 1) {
    		temp = "*"+temp;
    	}
    	else temp = " "+temp;
    	if(is_completed == 1) 
    		return "[V]"+ id+ ". "+"[" + category + "] " + "["+temp+"] "+"["+place+"] "+"["+desc+"]"+"["+due_date+"]"
        			+"["+current_date+"]";
    	else
    		return "[ ]"+ id+ ". "+"[" + category + "] " + "["+temp+"] "+"["+place+"] "+"["+desc+"]"+"["+due_date+"]"
    			+"["+current_date+"]";
    }
    public String toStringToday() {		
		// TODO Auto-generated method stub
    	String temp = category;
    	if(first_priority == 1) {
    		temp = "*"+temp;
    	}
    	else temp = " " + temp;
    	if(is_completed == 1) 
    		return "[V]"+id+ ". [" + temp + "] " + place + "에서 " + desc;
    	else
    		return "[ ]"+id+ ". [" + temp + "] " + place + "에서 " + desc;
	}
	public void setId(int id) {
		// TODO Auto-generated method stub
		this.id = id;
	}
	public int getId() {
		return this.id;
	}
	public int getFirst_priority() {
		return first_priority;
	}
	public void setFirst_priority(int first_priority) {
		this.first_priority = first_priority;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	
}
