package com.ohgiraffers.model.dao;

import com.ohgiraffers.model.dto.ConvenienceStoreDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import static com.ohgiraffers.common.JDBCTemplate.close;

public class ConvenienceStoreDAO {

    private Properties prop = new Properties();

    //쿼리 불러오기
    public ConvenienceStoreDAO(){
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/ConvenienceStore-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //모든 편의점 테이블의 모든 상품 정보 조회
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

    // 관리자 ID 확인
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

    //직원 테이블의 모든 직원정보 조회
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

    //관리자가 관리하는 직원의 정보 조회
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


    public void StaffManagement(Connection con){
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        int result =0;

        System.out.println("1. 직원 추가  2. 직원변경");
        System.out.println("3. 직원 검색  4. 직원삭제");
        System.out.println();
        System.out.print("실행할 메뉴를 입력하세요 : ");
        int num = sc.nextInt();

        switch (num){
            case 1:
                System.out.print("추가할 직원의 이름을 입력 하세요 : ");
                sc.nextLine();
                String staffName = sc.nextLine();
                System.out.print("추가할 직원의 전화번호를 입력하세요 : ");
                String staffPhone = sc.nextLine();
                System.out.print("추가할 직원의 입사일을 입력하세요(예-2023.04.08) : ");
                String staffHireDate = sc.nextLine();
                System.out.print("추가할 직원의 출근 상태를 입력하세요(O/X) : ");
                String staffWorkStatus = sc.nextLine();
                System.out.print("추가할 직원을 관리하는 관리자의 이름을 입력하세요 : ");
                String managerName = sc.nextLine();

                String query = prop.getProperty("AddStaff");
                try {
                    pstmt = con.prepareStatement(query);
                    pstmt.setString(1,staffName);
                    pstmt.setString(2,staffPhone);
                    pstmt.setString(3,staffHireDate);
                    pstmt.setString(4,staffWorkStatus);
                    pstmt.setString(5,managerName);

                    result = pstmt.executeUpdate();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (result>0){
                    System.out.println("직원 정보를 성공적으로 추가했습니다");
                }else {
                    System.out.println("직원 정보 등록에 실패했습니다");
                }break;

            case 2:
                System.out.print("변경할 직원의 직원 ID를 입력 하세요 : ");
                sc.nextLine();
                String staffId = sc.nextLine();
                System.out.print("변경된 직원의 이름을 입력하세요 : ");
                String staffNameCh = sc.nextLine();
                System.out.print("변경된 직원의 전화번호를 입력하세요 : ");
                String staffPhoneCh = sc.nextLine();
                System.out.print("변경된 직원의 입사일을 입력하세요(예-2023.04.08) : ");
                String staffHireDateCh=sc.nextLine();
                System.out.print("변경된 직원의 출근 상태를 입력하세요(O/X) : ");
                String staffWorkStatusCh = sc.nextLine();
                System.out.print("변경된 직원을 관리하는 관리자의 이름을 입력하세요 : ");
                String managerNameCh = sc.nextLine();

                String query1 = prop.getProperty("changeStaff");
                try {
                    pstmt = con.prepareStatement(query1);

                    pstmt.setString(1,staffNameCh);
                    pstmt.setString(2,staffPhoneCh);
                    pstmt.setString(3,staffHireDateCh);
                    pstmt.setString(4,staffWorkStatusCh);
                    pstmt.setString(5,managerNameCh);
                    pstmt.setString(6,staffId);

                    result = pstmt.executeUpdate();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }if (result>0){
                System.out.println("직원 정보를 성공적으로 변경했습니다");
            }else {
                System.out.println("직원 정보 변경에 실패했습니다");
            }break;


            case 3:
                System.out.print("검색할 직원의 직원 ID를 입력하세요 : ");
                int staffIdSh = sc.nextInt();

                String query2 = prop.getProperty("searchStaff");
                try {
                    pstmt = con.prepareStatement(query2);
                    pstmt.setInt(1,staffIdSh);

                    rset = pstmt.executeQuery();
                    if (rset.next()){
                        System.out.println("직원 ID : " + rset.getInt("STAFF_ID") + ", 직원 이름 : " + rset.getString("STAFF_NAME") + ", 직원 전화번호 : " + rset.getString("STAFF_PHONE") + ", 입사일 : " + rset.getString("HIRE_DATE") + ", 출근여부 : " + rset.getString("WORK_STATUS") + ", 관리자 이름 : " + rset.getString("MANAGER_NAME"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }break;

            case 4:
                System.out.print("삭제할 직원의 직원 ID를 입력하세요 : ");
                int staffIdDel = sc.nextInt();
                String query3 = prop.getProperty("deleteStaff");

                try {
                    pstmt =con.prepareStatement(query3);
                    pstmt.setInt(1,staffIdDel);

                    result = pstmt.executeUpdate();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }if (result>0){
                System.out.println("직원 정보를 성공적으로 삭제했습니다");
            }else {
                System.out.println("직원 정보를 삭제하지 못했습니다");
            }break;
            default:
                System.out.println("잘못된 번호를 입력했습니다");
                break;
        }
    }
    //편의점 테이블에 상품 추가
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


    //상품 조회
    public void searchProduct(Connection con){
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstmt = null;
        ResultSet rset = null;


        System.out.println("1. 상품코드로 검색     2. 상품 이름으로 검색    ");
        System.out.println("3. 상품가격으로 검색   4. 상품 카테고리로 검색 ");
        System.out.println("5. 상품 재고여부로 검색");
        System.out.println();
        System.out.print("상품을 검색할 방법을 입력하세요 : ");
        int num = sc.nextInt();
        switch (num){
            case 1 :
                //편의점 테이블에서 상품코드로 상품조회
                String query = prop.getProperty("searchProductCode");
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
                }
                System.out.println("상품코드가 " + productCode + "번인 상품 검색을 완료했습니다");
                break;

            case 2 :
                //편의점 테이블에서 상품이름으로 상품조회
                String query1 = prop.getProperty("searchProductName");
                System.out.print("검색할 상품의 이름을 입력하세요 : ");
                sc.nextLine();
                String productName = sc.nextLine();

                try {
                    pstmt = con.prepareStatement(query1);
                    pstmt.setString(1,productName);
                    rset = pstmt.executeQuery();
                    while (rset.next()){
                        System.out.println("상품관리 코드 : " + rset.getInt("PRODUCT_CODE") + ", 상품 이름 : " + rset.getString("PRODUCT_NAME") + ", 상품가격 : " + rset.getInt("PRODUCT_PRICE") + ", 상품 카테고리 : " + rset.getString("PRODUCT_CATEGORY") + ", 재고 여부 : " + rset.getString("PRODUCT_STATUS"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("상품이름이 " + productName + "인 상품 검색을 완료했습니다");
                break;

            case 3:
                //편의점 테이블에서 상품가격으로 상품조회
                String query2 = prop.getProperty("searchProductPrice");
                System.out.print("검색할 상품의 가격을 입력하세요 : ");
                int productPrice = sc.nextInt();

                try {
                    pstmt =con.prepareStatement(query2);
                    pstmt.setInt(1,productPrice);
                    rset = pstmt.executeQuery();
                    while (rset.next()){
                        System.out.println("상품관리 코드 : " + rset.getInt("PRODUCT_CODE") + ", 상품 이름 : " + rset.getString("PRODUCT_NAME") + ", 상품가격 : " + rset.getInt("PRODUCT_PRICE") + ", 상품 카테고리 : " + rset.getString("PRODUCT_CATEGORY") + ", 재고 여부 : " + rset.getString("PRODUCT_STATUS"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("가격이 " + productPrice + "인 상품 검색을 완료했습니다");
                break;

            case 4 :
                //편의점 테이블에서 상품 카테고리로 상품조회
                String query3 = prop.getProperty("searchProductCategory");
                System.out.print("검색할 상품의 카테고리를 입력하세요(식사/음료/주류/위생용품/기타) : ");
                sc.nextLine();
                String productCategory = sc.nextLine();

                try {
                    pstmt = con.prepareStatement(query3);
                    pstmt.setString(1,productCategory);
                    rset = pstmt.executeQuery();

                    while (rset.next()){
                        System.out.println("상품관리 코드 : " + rset.getInt("PRODUCT_CODE") + ", 상품 이름 : " + rset.getString("PRODUCT_NAME") + ", 상품가격 : " + rset.getInt("PRODUCT_PRICE") + ", 상품 카테고리 : " + rset.getString("PRODUCT_CATEGORY") + ", 재고 여부 : " + rset.getString("PRODUCT_STATUS"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("상품 카테고리가 "+ productCategory + "인 상품 검색을 완료했습니다");
                break;

            case 5 :
                //편의점 테이블에서 상품 재고여부로 상품조회
                String query4 = prop.getProperty("searchProductStatus");
                System.out.print("검색할 상품의 재고 여부를 입력하세요(O/X) : ");
                sc.nextLine();
                String productStatus = sc.nextLine();

                try {
                    pstmt = con.prepareStatement(query4);
                    pstmt.setString(1,productStatus);
                    rset = pstmt.executeQuery();

                    while (rset.next()){
                        System.out.println("상품관리 코드 : " + rset.getInt("PRODUCT_CODE") + ", 상품 이름 : " + rset.getString("PRODUCT_NAME") + ", 상품가격 : " + rset.getInt("PRODUCT_PRICE") + ", 상품 카테고리 : " + rset.getString("PRODUCT_CATEGORY") + ", 재고 여부 : " + rset.getString("PRODUCT_STATUS"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("상품 재고여부가 " + productStatus + "인 상품 검색을 완료했습니다");
                break;

            default:
                System.out.println("번호를 잘못입력하셨습니다");
        }

    }

    //편의점 테이블의 상품 변경
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


    //편의점 테이블의 상품 삭제
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
