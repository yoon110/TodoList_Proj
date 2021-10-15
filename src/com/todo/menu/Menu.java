package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("-----------MENU-----------");
        System.out.println("add          - 일정 추가");
        System.out.println("del          - 일정 삭제");
        System.out.println("edit         - 일정 편집");
        System.out.println("ls           - 일정 목록");
        System.out.println("ls_name      - 이름 순 정렬 목록");
        System.out.println("ls_name_desc - 이름 역순 정렬 목록");
        System.out.println("ls_date      - 날짜 순 정렬 목록");
        System.out.println("ls_date_desc - 날짜 역순 정렬 목록(최신순)");
        System.out.println("ls_cate      - 카테고리 목록");
        System.out.println("find         - 제목, 내용 내 키워드 검색");
        System.out.println("find_cate    - 카테고리 내 키워드 검색");
        System.out.println("exit         - 종료");
    }
    

	public static void prompt() {
		// TODO Auto-generated method stub
		System.out.printf("\n입력 > ");
	}
}
