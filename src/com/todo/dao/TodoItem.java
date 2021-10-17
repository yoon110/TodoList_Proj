package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
    private String title;
    private String desc;
    private String current_date;
    private String category;
    private String due_date;
    private int id;
    private String complete = " ";
    private String importance;
    private String place;

	public TodoItem(String title, String desc, String category, String due_date, String check) {
		super();
		this.title = title;
		this.desc = desc;
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date= f.format(new Date());
		this.category = category;
		this.due_date = due_date;
		this.complete = check;
	}

	public TodoItem(String title, String desc, String category, String due_date, String check, String importance,
			String place) {
		super();
		this.title = title;
		this.desc = desc;
		this.category = category;
		this.due_date = due_date;
		this.complete = check;
		this.importance = importance;
		this.place = place;
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date= f.format(new Date());
	}
	
	public TodoItem(String title, String desc, String category, String due_date, String importance,
			String place) {
		super();
		this.title = title;
		this.desc = desc;
		this.category = category;
		this.due_date = due_date;
		this.importance = importance;
		this.place = place;
		this.complete= " ";
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date= f.format(new Date());
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getComplete() {
		return complete;
	}

	public void setComplete(String complete) {
		this.complete = complete;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
    
    public boolean equalWord(String t) {
    	return(this.title.contains(t) || this.desc.contains(t));
    }
    
    public boolean equalCate(String t) {
    	return(this.category.contains(t));
    }
    
    public String toSaveString() {
    	return complete +"##"+ category +"##"+ title + "##" + importance +"##"+ desc +"##"+ place +"##"+ due_date +"##"+ current_date + "\n";
    }
    
    @Override
  	public String toString() {
    	TodoList l = new TodoList();
    	String cateName = l.cateNumtoName(category);
  		return "["+complete+"] "+ id + " [" +cateName+ "] / " +title + " (" + importance  +") / " + desc + " / "+place+ " / " + due_date +" / "+ current_date;
  	}
}
