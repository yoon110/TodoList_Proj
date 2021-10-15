package com.todo;

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
		TodoUtil.loadList(l, "todoList.txt");
		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			sc.nextLine();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				System.out.println("< 일정 목록 >");
				TodoUtil.listAll(l);
				break;

			case "ls_name":
				System.out.println("< 제목 순으로 목록 정렬 >");
				TodoUtil.listAll(l,"title",1);
				break;

			case "ls_name_desc":
				System.out.println("< 제목 역순으로 목록 정렬 >");
				TodoUtil.listAll(l,"title",0);
				break;
				
			case "ls_date":
				System.out.println("< 날짜 순으로 목록 정렬 >");
				TodoUtil.listAll(l,"due_date",1);
				break;
				
			case "ls_date_desc":
				System.out.println("< 날짜 역순으로 목록 정렬 >");
				TodoUtil.listAll(l,"due_date",0);
				break;
				
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
				
			case "find":
				String keyword = sc.nextLine().trim();
				TodoUtil.findKey(l,keyword);
				break;
				
			case "find_cate":
				String cate = sc.nextLine().trim();
				TodoUtil.findCateList(l,cate);
				break;
				
			case "help":
				Menu.displaymenu();
				break;

			case "exit":
				quit = true;
				break;
				
			default:
				System.out.println("정확한 명령어를 입력해주세요. (명령어 메뉴 - help)");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
		sc.close();
		TodoUtil.saveList(l, "todoList.txt");
	}
}
