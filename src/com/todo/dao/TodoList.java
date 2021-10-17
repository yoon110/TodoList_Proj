package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

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

	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, place, category, importance, current_date, due_date, complete)"
				+ " values (?,?,?,?,?,?,?,?);";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getPlace());
			pstmt.setString(4, t.getCategory());
			pstmt.setString(5, t.getImportance());
			pstmt.setString(6, t.getCurrent_date());
			pstmt.setString(7, t.getDue_date());
			pstmt.setString(8, t.getComplete());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int i) {
		String sql = "delete from list where id=?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, i);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public int checkTo(int i) {
		String sql = "Update list set complete=?"
				+ "WHERE id=?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "V");
			pstmt.setInt(2, i);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public int updateItem(TodoItem t, int i) {
		String sql = "Update list set title=?, memo=?, place=?, category=?, importance=?, current_date=?, due_date=?"
				+ "where id=?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getPlace());
			pstmt.setString(4, t.getCategory());
			pstmt.setString(5, t.getImportance());
			pstmt.setString(6, t.getCurrent_date());
			pstmt.setString(7, t.getDue_date());
			pstmt.setInt(8, i);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String place = rs.getString("place");
				String category = rs.getString("category");
				String importance = rs.getString("importance");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				String complete = rs.getString("complete");
				TodoItem t = new TodoItem(title, description, category, due_date, complete, importance, place);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
		stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%" + keyword + "%";
		try {
			String sql = "SELECT * FROM list WHERE title like ? OR memo like ?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String place = rs.getString("place");
				String category = rs.getString("category");
				String importance = rs.getString("importance");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				String complete = rs.getString("complete");
				TodoItem t = new TodoItem(title, description, category, due_date, complete, importance, place);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM category";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String category = rs.getString("category");
				list.add(category);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getListCategory(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		String keyNum = cateNametoNum(keyword);
		String sql = "SELECT * FROM list WHERE category = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyNum);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String place = rs.getString("place");
				String category = rs.getString("category");
				String importance = rs.getString("importance");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				String complete = rs.getString("complete");
				TodoItem t = new TodoItem(title, description, category, due_date, complete, importance, place);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			if (ordering == 0)
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String place = rs.getString("place");
				String category = rs.getString("category");
				String importance = rs.getString("importance");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				String complete = rs.getString("complete");
				TodoItem t = new TodoItem(title, description, category, due_date, complete, importance, place);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll() {
		for (TodoItem myitem : list) {
			System.out.println(myitem.toString());
		}
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

	public int isDuplicate(String title) {
		PreparedStatement pstmt;
		int count = 0;
		String sql = "SELECT * FROM list WHERE title = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				count++;
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, memo, place, category, importance, current_date, due_date, complete)"
					+ " values (?,?,?,?,?,?,?,?);";
			int records = 0;
			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String complete = st.nextToken();
				String category = st.nextToken();
				String title = st.nextToken();
				String importance = st.nextToken();
				String desc = st.nextToken();
				String place = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();

				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, desc);
				pstmt.setString(3, place);
				pstmt.setString(4, category);
				pstmt.setString(5, importance);
				pstmt.setString(6, current_date);
				pstmt.setString(7, due_date);
				pstmt.setString(8, complete);

				int count = pstmt.executeUpdate();
				if (count > 0)
					records++;
				pstmt.close();

			}
			System.out.println(records + " records read!!");
			br.close();
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getCountList() {
		Statement stmt;
		int count = 0;
		try {
			String sql = "SELECT count(id) FROM list;";
			stmt = conn.createStatement();
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
	
	public int getCountCate() {
		Statement stmt;
		int count = 0;
		try {
			String sql = "SELECT count(id) FROM category;";
			stmt = conn.createStatement();
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

	public String cateNumtoName(String n) {
		
		String cate = null;
		int id = Integer.parseInt(n);
		PreparedStatement pstmt;
		String sql = "SELECT * FROM category WHERE id = ?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				cate = rs.getString("category");
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cate;
	}

	public String cateNametoNum(String keyword) {
		PreparedStatement pstmt;
		String cateNum=null;
		String sql = "SELECT * FROM category WHERE category = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int i = rs.getInt("id");
				cateNum= Integer.toString(i);
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cateNum;
	}
	
	public int addCate(String category) {
		String sql = "insert into category (category)"
				+ " values (?);";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public String insertCate(String name) {
		String cateNum;
		if(cateNametoNum(name)==null) {
			int count=addCate(name);
			cateNum = cateNametoNum(name);
		}else {
			cateNum = cateNametoNum(name);
		}
		return cateNum;
	}
}
