package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {

	public static void createItem(TodoList list) {

		String title, desc, cate, due, place, imp;
		Scanner sc = new Scanner(System.in);
		System.out.printf("< 일정 추가 >\n" + "제목 입력 > ");
		title = sc.nextLine().trim();
		while (list.isDuplicate(title) > 0) {
			System.out.printf("이미 존재하는 일정입니다.");
			return;
		}
		sc.nextLine();
		System.out.printf("내용 입력 > ");
		desc = sc.nextLine().trim();

		while (desc.isEmpty()) {
			System.out.printf("다시 입력해주세요.");
			desc = sc.nextLine().trim();
		}

		System.out.printf("장소 입력 (없을 시 스페이스바 입력바랍니다.)> ");
		place = sc.nextLine();

		System.out.printf("카테고리 입력 > ");
		String cateName = sc.next();
		cate = list.insertCate(cateName);

		System.out.printf("중요도 입력 (상,중,하)> ");
		imp = sc.next();

		System.out.printf("마감일자 입력(YYYY/MM/DD) >");
		due = sc.next();

		TodoItem t = new TodoItem(title, desc, cate, due, imp, place);
		if (list.addItem(t) > 0)
			System.out.printf("일정이 추가 되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.println("< 일정 삭제 >");
		while(true) {
			System.out.printf("삭제할 일정의 번호 입력 (종료=0)> ");
			int i = sc.nextInt();
			if(i==0)
				return;
			if (l.deleteItem(i) > 0)
				System.out.printf("입력하신 일정이 삭제 되었습니다.\n");
		}
		
	}

	public static void updateItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.printf("< 일정 편집 >\n" + "편집할 일정의 번호 입력 > ");
		int i = sc.nextInt();
		System.out.printf("새로운 제목 입력 > ");
		String new_title = sc.next();
		while (new_title.isEmpty() || l.isDuplicate(new_title) > 0) {
			if (l.isDuplicate(new_title) > 0) {
				System.out.println("이미 존재하는 일정의 제목입니다.");
			}
			System.out.println("다시 입력해주세요.");
			new_title = sc.next();
		}

		sc.nextLine();
		System.out.printf("새로운 내용 입력 > ");
		String new_description = sc.nextLine().trim();
		while (new_description.isEmpty()) {
			System.out.println("다시 입력해주세요.");
			new_description = sc.nextLine().trim();
		}

		System.out.printf("새로운 장소 입력 > ");
		String new_place = sc.nextLine().trim();

		System.out.printf("새로운 카테고리 입력 > ");
		String cateName = sc.next();
		String new_category = l.insertCate(cateName);

		System.out.printf("새로운 중요도 입력 (상,중,하) > ");
		String new_importance = sc.next();

		System.out.printf("새로운 마감일자 입력 > ");
		String new_dueDate = sc.next();

		TodoItem t = new TodoItem(new_title, new_description, new_category, new_dueDate, new_importance, new_place);
		t.setId(i);
		if (l.updateItem(t,i) > 0)
			System.out.println("일정이 편집되었습니다.");
	}
	

	public static void checkComplete(TodoList l) {
		Scanner sc = new Scanner(System.in);
		int i=0;
		while(true) {
			System.out.printf("완료 체크할 항목의 id를 입력하세요 (종료=0)> ");
			i = sc.nextInt();
			if(i==0)
				return;
			if(l.checkTo(i)>0)
				System.out.println("완료체크 되었습니다.");
		}
	}
	
	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d 개]\n", l.getCountList());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}

	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록, 총 %d 개]\n", l.getCountList());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}

	public static void findKey(TodoList l, String keyword) {
		int count = 0;
		for (TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}

	public static void findCateList(TodoList l, String cate) {
		int count = 0;
		for (TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}

	public static void listCate(TodoList l) {
		HashSet<String> cate = new HashSet<String>();
		for (TodoItem item : l.getList()) {
			cate.add(item.getCategory());
		}
		Iterator<String> it = cate.iterator();
		while (it.hasNext()) {
			System.out.print(it.next());
			it.next();
			if (it.hasNext())
				System.out.print(" / ");
		}
		System.out.println("\n총 " + cate.size() + "개의 항목을 찾았습니다.");
	}

	public static void listCateAll(TodoList l) {
		for (String item : l.getCategories()) {
			System.out.print(item + " ");
		}
		System.out.println("\n총 " + l.getCountCate() + "개의 항목을 찾았습니다.");
	}
	
	public static void saveList(TodoList l, String filename) {
		Writer writer;
		try {
			writer = new FileWriter(filename);
			for (TodoItem item : l.getList()) {
				writer.write(item.toSaveString());
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			Reader r = new FileReader(filename);
			BufferedReader b = new BufferedReader(r);
			String line = "";
			while ((line = b.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String complete = st.nextToken();
				String category = st.nextToken();
				String title = st.nextToken();
				String importance = st.nextToken();
				String desc = st.nextToken();
				String place = st.nextToken();
				String due_date = st.nextToken();
				String date = st.nextToken();
				TodoItem item = new TodoItem(title, desc, category, due_date, complete, importance, place);
				item.setCurrent_date(date);
				System.out.println(item.toSaveString());
			}
			b.close();
		} catch (FileNotFoundException e) {
			System.out.println(filename + " 파일이 없습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
