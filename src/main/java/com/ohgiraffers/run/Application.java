package com.ohgiraffers.run;

import com.ohgiraffers.model.dao.ConvenienceStoreDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application {
    public static void main(String[] args) {
        Connection con =getConnection();
        ConvenienceStoreDAO dao = new ConvenienceStoreDAO();
        PreparedStatement pstmt = null;
        int result = 0;

        Scanner sc = new Scanner(System.in);


        while (true){
            System.out.println();
        System.out.println("==== 편의점 관리 프로그램 ====");
        dao.checkManagerId(con);
        System.out.println("==== 프로그램 메뉴 ====");
        System.out.println();
        System.out.println("==1. 상품정보 전체 조회");
        System.out.println("==2. 직원정보 전체 조회");
        System.out.println("==3. 내가 관리하는 직원 조회");
        System.out.println("==4. 상품 추가");
        System.out.println("==5. 상품 변경");
        System.out.println("==6. 상품 검색");
        System.out.println("==7. 상품 삭제");
        System.out.println("==8. 프로그램 종료");
            System.out.println();
        System.out.print("원하는 메뉴를 선택 하세요 : ");
        int num = sc.nextInt();
            System.out.println();

        switch (num) {
            case 1:
                dao.selectAllProductlist(con);
                System.out.println();
                System.out.println("상품 전체 정보를 출력 완료했습니다");
                break;
            case 2:
                dao.staffAllInformation(con);
                System.out.println();
                System.out.println("직원 전체 정보를 출력 완료했습니다");
                break;
            case 3:
                dao.myStaffList(con);
        }
            if (num==8){
                System.out.println("프로그램을 종료합니다");
                break;
            }
    }

    }
}
