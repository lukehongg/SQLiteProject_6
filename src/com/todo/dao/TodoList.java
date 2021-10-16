package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import com.todo.dao.TodoItem;
import com.todo.service.DbConnect;

import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	
	private List<TodoItem> list;
	Connection conn;
	
	public TodoList() {
		this.list = new ArrayList<TodoItem>();
		this.conn = DbConnect.getConnection();
	}
	
	public ArrayList<TodoItem> getList(){ // list all
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("desc");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				int is_comp = rs.getInt("is_complete");
				int first_priority = rs.getInt("first_priority");
				String place = rs.getString("place");
				TodoItem t = new TodoItem(title, description, category, due_date, current_date, is_comp, first_priority, place);
				t.setId(id);
				list.add(t);
				
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(int comp) { // is_comp get list
		// TODO Auto-generated method stub
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list WHERE is_complete = 1;";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("desc");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				int is_comp = rs.getInt("is_complete");
				int first_priority = rs.getInt("first_priority");
				String place = rs.getString("place");
				TodoItem t = new TodoItem(title, description, category, due_date, current_date, is_comp, first_priority, place);
				t.setId(id);
				list.add(t);
				
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(String keyword) { // find get list
		// TODO Auto-generated method stub
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%" + keyword + "%";
		String sql = "SELECT * FROM list WHERE title like ? or desc like ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("desc");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				int is_comp = rs.getInt("is_complete");
				int first_priority = rs.getInt("first_priority");
				String place = rs.getString("place");
				TodoItem t = new TodoItem(title, description, category, due_date, current_date, is_comp, first_priority, place);
				t.setId(id);
				list.add(t);
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	
	public ArrayList<TodoItem> getListToday(String today) {
		// TODO Auto-generated method stub
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		String sql = "SELECT * FROM list WHERE due_date like ?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, today);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("desc");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				int is_comp = rs.getInt("is_complete");
				int first_priority = rs.getInt("first_priority");
				String place = rs.getString("place");
				TodoItem t = new TodoItem(title, description, category, due_date, current_date, is_comp, first_priority, place);
				t.setId(id);
				list.add(t);
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public int getCount() {
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public int addItem(TodoItem t) {
		String sql = "insert into list (title, desc, category, current_date, due_date, place)"
					+ " values (?,?,?,?,?,?);";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setString(6, t.getPlace());
			pstmt.setInt(7, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;	
	}
	
	public int addItemMontly(TodoItem t, int duration) {
		// TODO Auto-generated method stub
		
		String sql = "insert into list (title, desc, category, current_date, due_date, place)"
				+ " values (?,?,?,?,?,?);";
		
		int count = 0;
		
		try {
			for(int i=0; i<duration; i++) {
				String s = t.getDue_date();
				StringBuilder sb = new StringBuilder(s);
				String[] date = s.split("/");
				Integer temp = Integer.parseInt(date[1]);
				temp = temp + i;
				if(temp>12) {
					temp = temp-12;
					Integer year = Integer.parseInt(date[0]);
					year += 1;
					sb.insert(0, year.toString());
					sb.delete(4, 8);
				}
				sb.insert(5,temp.toString());
				if(temp<10) sb.insert(5,"0");
				sb.delete(7,9);
				String due_date = sb.toString();
				
				PreparedStatement pstmt;
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, t.getTitle());
				pstmt.setString(2, t.getDesc());
				pstmt.setString(3, t.getCategory());
				pstmt.setString(4, t.getCurrent_date());
				pstmt.setString(5, due_date.trim());
				pstmt.setString(6, t.getPlace());
				count += pstmt.executeUpdate();
				pstmt.close();
			}
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(count == duration) return 1;
		else return 0;
	}

	public int deleteItem(int index) {
		PreparedStatement pstmt;
		int count = 0;
		
		try {

			String sql = "delete from list where id=?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return count;
	}

	public int editItem(TodoItem t) {
		String sql = "update list set tItle=?, desc=?, category=?, current_date=?, due_date=?, place=?"
					+ " where id=?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setString(6, t.getPlace());
			pstmt.setInt(7, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
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
			Statement stmt;
			int dup = 0;
			try {
				stmt = conn.createStatement();
				String sql = "SELECT count(id) FROM list WHERE title = '"+title+"';";
				ResultSet rs = stmt.executeQuery(sql);
				rs.next();
				dup = rs.getInt("count(id)");
				stmt.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(dup>0) return true;
			return false;
	}
	
	/*
	public void importData(String filename) {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, desc, category, current_date, due_date, place)"
						+ " values (?,?,?,?,?,?);";
			int records = 0;
			
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				String place = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, description);
				pstmt.setString(3, category);
				pstmt.setString(4, current_date);
				pstmt.setString(5, due_date);
				pstmt.setString(6, place);
				int count = pstmt.executeUpdate();
				if(count>0) records++;
				pstmt.close();
				
			}
			System.out.println(records + " records read!");
			br.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
*/
	

	public ArrayList<String> getCategories() {
		// TODO Auto-generated method stub
		ArrayList<String> list= new ArrayList<String>();
		Statement stmt;
		String sql = "SELECT DISTINCT category FROM list;";
		
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list.add(rs.getString("category"));
			}

			stmt.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return list;
	}

	public ArrayList<TodoItem> getListCategory(String keyword) {
		// TODO Auto-generated method stub
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		String sql = "SELECT * FROM list where category = ?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("desc");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				int is_comp = rs.getInt("is_complete");
				int first_priority = rs.getInt("first_priority");
				String place = rs.getString("place");
				TodoItem t = new TodoItem(title, description, category, due_date, current_date, is_comp, first_priority, place);
				t.setId(id);
				list.add(t);	
			}
			pstmt.close();
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		String sql = "Select * FROM list ORDER By " + orderby;
		try {
			stmt = conn.createStatement();
			if(ordering == 0) {
				sql +=" desc";
			}
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("desc");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				int is_comp = rs.getInt("is_complete");
				int first_priority = rs.getInt("first_priority");
				String place = rs.getString("place");
				TodoItem t = new TodoItem(title, description, category, due_date, current_date, is_comp, first_priority, place);
				t.setId(id);
				list.add(t);	
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public int completeItem(int compIdx) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		int result=0;
		try {
			String sql = "update list set is_complete = 1 where id = ?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, compIdx);
			result = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public int setPriority(int idx) {
		PreparedStatement pstmt;
		int result=0;
		try {
			String sql = "update list set first_priority = 1 where id = ?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			result = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	

	

	
	
	
}
