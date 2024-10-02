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
                System.out.println("상품관리 코드 : " + rset.getInt("PRODUCT_CODE") + ", 상품 이름 : " + rset.getString("PRODUCT_NAME") + ", 상품가격 : " + rset.getInt("PRODUCT_PRICE") + ", 상품 카테고리 : " + rset.getString("PRODUCT_CATEGORY") + ", 재고 여부 : " + rset.getString("PRODUCT_STATUS"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(stmt);
            close(rset);
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
                    System.out.println();
                    System.out.println("관리자 코드 확인 완료 :  " + checkList);

                    System.out.println("환영합니다 " + managerId + "번 관리자님");
                }


                }else {
                    System.out.println();
                    System.out.println("관리자 코드가 옳지 않습니다 프로그램을 종료합니다");
                    System.out.println();


            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(pstmt);
            close(rset);
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
                System.out.println("직원 ID : " + rset.getInt("STAFF_ID") + ", 직원 이름 : " + rset.getString("STAFF_NAME") + ", 직원 전화 번호 : " +  rset.getString("STAFF_PHONE") + ", 입사일 : " + rset.getString("HIRE_DATE") + ", 출근 여부 : " + rset.getString("WORK_STATUS") + ", 담당 관리자 : " + rset.getString("MANAGER_NAME"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(stmt);
            close(rset);
        }
    }

    public void myStaffList(Connection con){
        Scanner sc = new Scanner(System.in);
        PreparedStatement ptsmt = null;
        ResultSet rset = null;

        System.out.print( "관리자 이름을 입력하세요 : ");
        String managerName = sc.nextLine();
        String query = prop.getProperty("myStaffList");
        try {
            ptsmt = con.prepareStatement(query);
            ptsmt.setString(1,managerName);

            rset = ptsmt.executeQuery();

            System.out.println(managerName + "님이 관리하는 직원은 다음과 같습니다");
            System.out.println();
            while (rset.next()){
                System.out.println("직원 이름은 " + rset.getString("STAFF_NAME")+ "이고 직원 ID는 " + rset.getInt("STAFF_ID") + "입니다");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(ptsmt);
            close(rset);
        }

    }

    public int insertProduct(Connection con) {

        Scanner sc = new Scanner(System.in);

        PreparedStatement pstmt = null;
        int result =0;

        String query = prop.getProperty("insertProduct");

        System.out.print("추가할 상품의 이름을 입력하세요 : ");
        String productName = sc.nextLine();
        System.out.print("추가할 상품의 가격을 입력하세요 : ");
        int productPrice = sc.nextInt();
        System.out.print("추가할 상품의 카테고리를 입력하세요(식사/음료/주류/위생용품/기타) : ");
        sc.nextLine();
        String productCategory = sc.nextLine();
        System.out.print("추가할 상품의 재고 여부를 입력하세요(O/X) : ");
        String productStatus = sc.nextLine();
        try {
            pstmt=con.prepareStatement(query);
            pstmt.setString(1,productName);
            pstmt.setInt(2,productPrice);
            pstmt.setString(3,productCategory);
            pstmt.setString(4,productStatus);

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(pstmt);
        }if (result>0){
            System.out.println();
            System.out.println("상품을 성공적으로 추가했습니다");
        }else {
            System.out.println("상품 등록에 실패했습니다");
        }

        return result;
    }


    public void searchProduct(Connection con){
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        String query = prop.getProperty("searchProduct");

        System.out.print("검색할 상품의 상품코드를 입력하세요 : ");
        int productCode = sc.nextInt();
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1,productCode);
            rset = pstmt.executeQuery();
            if (rset.next()){
                System.out.println("상품관리 코드 : " + rset.getInt("PRODUCT_CODE") + ", 상품 이름 : " + rset.getString("PRODUCT_NAME") + ", 상품가격 : " + rset.getInt("PRODUCT_PRICE") + ", 상품 카테고리 : " + rset.getString("PRODUCT_CATEGORY") + ", 재고 여부 : " + rset.getString("PRODUCT_STATUS"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(pstmt);
            close(rset);
        }
        System.out.println(productCode + "번 상품 검색을 완료했습니다");
    }

    public void changeProduct(Connection con){
        Scanner sc = new Scanner(System.in);
        PreparedStatement ptsmt = null;
        int result = 0;

        String query = prop.getProperty("changeProduct");

        System.out.print("변경하려는 상품의 상품코드를 입력하세요 : ");
        int productCode = sc.nextInt();
        System.out.print("상품의 변경 된 이름을 입력하세요 : ");
        sc.nextLine();
        String productName = sc.nextLine();
        System.out.print("상품의 변경 된 가격을 입력하세요 : ");
        int productPrice = sc.nextInt();
        System.out.print("상품의 변경 된 카테고리를 입력하세요(식사/음료/주류/위생용품/기타) : ");
        sc.nextLine();
        String productCategory = sc.nextLine();
        System.out.print("상품의 변경된 재고 여부를 입력하세요(O/X) : ");
        String productStatus = sc.nextLine();

        try {
            ptsmt = con.prepareStatement(query);
            ptsmt.setString(1,productName);
            ptsmt.setInt(2,productPrice);
            ptsmt.setString(3,productCategory);
            ptsmt.setString(4,productStatus);
            ptsmt.setInt(5,productCode);

            result = ptsmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(ptsmt);
        }if (result>0){
            System.out.println("상품을 성공적으로 변경했습니다");
        }else {
            System.out.println("상품 변경에 실패했습니다");
        }
    }


    public void deleteProduct(Connection con){
        PreparedStatement ptsmt = null;
        int result =0;
        Scanner sc = new Scanner(System.in);
        System.out.print("삭제할 상품의 이름을 입력하세요 : ");
        String productName = sc.nextLine();

        String query = prop.getProperty("deleteProduct");
        try {
            ptsmt = con.prepareStatement(query);
            ptsmt.setString(1,productName);

            result = ptsmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(ptsmt);
        }if (result>0){
            System.out.println("상품을 성공적으로 삭제했습니다");
        }else {
            System.out.println("상품 삭제를 실패했습니다");
        }

    }


}
