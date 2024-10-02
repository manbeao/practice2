package com.ohgiraffers.model.dao;

import com.ohgiraffers.model.dto.ConvenienceStoreDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import static com.ohgiraffers.common.JDBCTemplate.close;

public class ConvenienceStoreDAO {

    private Properties prop = new Properties();

    public ConvenienceStoreDAO(){
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/ConvenienceStore-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void selectAllProductlist (Connection con){
        Statement stmt = null;
        ResultSet rset = null;


        String query = prop.getProperty("selectAllProductList");
        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            while (rset.next()){
                System.out.println("상품관리 코드 : " + rset.getInt("PRODUCT_CODE") + ", 상품 이름 ; " + rset.getString("PRODUCT_NAME") + ", 상품가격 : " + rset.getInt("PRODUCT_PRICE") + ", 상품 카테고리 : " + rset.getString("PRODUCT_CATEGORY") + ", 재고 여부 : " + rset.getString("PRODUCT_STATUS"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(stmt);
            close(rset);
            close(con);
        }
    }

    public List<Map<Integer, String>> checkManagerId(Connection con){
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        List<Map<Integer, String>> checkList = null;

        try {
            System.out.print("== 관리자 코드를 입력해주세요 : ");
            int managerId = sc.nextInt();

            checkList = new ArrayList<>();

            String query = prop.getProperty("checkManagerId");
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1,managerId);

            rset= pstmt.executeQuery();

            if (rset.next()){
                if (managerId == 1 || managerId ==2 || managerId ==3 || managerId ==4||managerId==5){
                    Map<Integer, String> check = new HashMap<>();
                    check.put(rset.getInt("MANAGER_ID"), rset.getString("MANAGER_NAME"));
                    checkList.add(check);
                    System.out.println("관리자 코드 확인 완료 :  " + checkList);

                    System.out.println("환영합니다 " + managerId + "번 관리자님");
                    System.out.println();
                }else {
                    System.out.println("프로그램을 종료합니다");

                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(pstmt);
            close(rset);
            close(con);
        }
        return checkList;
    }

    public void staffAllInformation(Connection con){
        Statement stmt = null;
        ResultSet rset = null;

        String query = prop.getProperty("staffAllInformation");

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);
            while (rset.next()){
                System.out.println("직원 ID : " + rset.getInt("STAFF_ID") + ", 직원 이름 : " + rset.getString("STAFF_NAME") + ", 직원 전화 번호 : " +  rset.getString("STAFF_PHONE") + ", 입사일 : " + rset.getString("HORE_DATE") + ", 출근 여부 : " + rset.getString("WORK_STATUS") + ", 담당 관리자 : ");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
