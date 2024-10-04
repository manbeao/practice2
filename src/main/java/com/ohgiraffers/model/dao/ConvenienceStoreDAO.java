package com.ohgiraffers.model.dao;

import com.ohgiraffers.model.dto.ConvenienceStoreDTO;
import com.ohgiraffers.model.dto.ManagerDTO;
import com.ohgiraffers.model.dto.StaffDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import static com.ohgiraffers.common.JDBCTemplate.close;

public class ConvenienceStoreDAO {


    private Properties prop = new Properties();

    ConvenienceStoreDTO store = null;
    ManagerDTO manager = null;
    StaffDTO staff = null;

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

        ConvenienceStoreDTO store = null;
        List<ConvenienceStoreDTO> storeList = null;

        String query = prop.getProperty("selectAllProductList");
        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            storeList = new ArrayList<>();
            while (rset.next()){
                store = new ConvenienceStoreDTO();
                store.setCode(rset.getInt("PRODUCT_CODE"));
                store.setName(rset.getString("PRODUCT_NAME"));
                store.setPrice(rset.getInt("PRODUCT_PRICE"));
                store.setCategory(rset.getString("PRODUCT_CATEGORY"));
                store.setProductStatus(rset.getString("PRODUCT_STATUS"));

                storeList.add(store);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(stmt);
            close(rset);
        }for (ConvenienceStoreDTO storeDTO : storeList){
            System.out.println(storeDTO);
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

                    System.out.println("환영합니다 '" + managerId + "번' 관리자님");
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

        StaffDTO staff = null;
        List<StaffDTO> staffList = null;
        String query = prop.getProperty("staffAllInformation");

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);
            staffList = new ArrayList<>();

            while (rset.next()){
                staff = new StaffDTO();
                staff.setStaffId(rset.getInt("STAFF_ID"));
                staff.setStaffName(rset.getString("STAFF_NAME"));
                staff.setStaffPhone(rset.getString("STAFF_PHONE"));
                staff.setHireDate(rset.getString("HIRE_DATE"));
                staff.setWorkStatus(rset.getString("WORK_STATUS"));
                staff.setManagerName(rset.getString("MANAGER_NAME"));

                staffList.add(staff);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(stmt);
            close(rset);
        }for (StaffDTO staffDTO : staffList){
            System.out.println(staffDTO);
        }
    }

    //관리자가 관리하는 직원의 정보 조회
    public void myStaffList(Connection con){
        Scanner sc = new Scanner(System.in);
        PreparedStatement ptsmt = null;
        ResultSet rset = null;

        List<StaffDTO> staffList = null;

        StaffDTO staffDTO = null;
        System.out.print( "관리자 이름을 입력하세요 : ");
        String managerName = sc.nextLine();
        String query = prop.getProperty("myStaffList");

        try {
            ptsmt = con.prepareStatement(query);
            ptsmt.setString(1,managerName);
            rset = ptsmt.executeQuery();

            System.out.println(managerName + "님이 관리하는 직원은 다음과 같습니다");
            System.out.println();

            staffList = new ArrayList<>();
            while (rset.next()){
                staffDTO = new StaffDTO();
                staffDTO.setStaffName(rset.getString("STAFF_NAME"));
                staffDTO.setStaffId(rset.getInt("STAFF_ID"));
                staffDTO.setStaffPhone(rset.getString("STAFF_PHONE"));
                staffDTO.setHireDate(rset.getString("HIRE_DATE"));
                staffDTO.setWorkStatus(rset.getString("WORK_STATUS"));
                staffDTO.setManagerName(rset.getString("MANAGER_NAME"));

                staffList.add(staffDTO);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(ptsmt);
            close(rset);
        }for (StaffDTO staffLi : staffList){
            System.out.println(staffLi);
        }

    }


    public void staffManagement(Connection con){
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        int result =0;

        List<StaffDTO> staffList = null;

        System.out.println("1. 직원 추가  2. 직원변경");
        System.out.println("3. 직원 검색  4. 직원삭제");
        System.out.println();
        System.out.print("실행할 메뉴를 입력하세요 : ");
        int num = sc.nextInt();


        switch (num){
            case 1:
                //직원 추가
                StaffDTO staffDTO = new StaffDTO();
                System.out.print("추가할 직원의 이름을 입력 하세요 : ");
                sc.nextLine();
                String staffName = sc.nextLine();
                staffDTO.setStaffName(staffName);
                System.out.print("추가할 직원의 전화번호를 입력하세요 : ");
                String staffPhone = sc.nextLine();
                staffDTO.setStaffPhone(staffPhone);
                System.out.print("추가할 직원의 입사일을 입력하세요(예-2023.04.08) : ");
                String staffHireDate = sc.nextLine();
                staffDTO.setHireDate(staffHireDate);
                System.out.print("추가할 직원의 출근 상태를 입력하세요(O/X) : ");
                String staffWorkStatus = sc.nextLine();
                staffDTO.setWorkStatus(staffWorkStatus);
                System.out.print("추가할 직원을 관리하는 관리자의 이름을 입력하세요 : ");
                String managerName = sc.nextLine();
                staffDTO.setManagerName(managerName);


                String query = prop.getProperty("addStaff");
                try {
                    pstmt = con.prepareStatement(query);
                    pstmt.setString(1,staffDTO.getStaffName());
                    pstmt.setString(2,staffDTO.getStaffPhone());
                    pstmt.setString(3,staffDTO.getHireDate());
                    pstmt.setString(4,staffDTO.getWorkStatus());
                    pstmt.setString(5,staffDTO.getManagerName());

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
                //직원 변경
                System.out.print("변경할 직원의 직원 ID를 입력 하세요 : ");
                StaffDTO changeStaff = new StaffDTO();
                int staffId = sc.nextInt();
                changeStaff.setStaffId(staffId);
                System.out.print("변경된 직원의 이름을 입력하세요 : ");
                sc.nextLine();
                String staffNameCh = sc.nextLine();
                changeStaff.setStaffName(staffNameCh);
                System.out.print("변경된 직원의 전화번호를 입력하세요 : ");
                String staffPhoneCh = sc.nextLine();
                changeStaff.setStaffPhone(staffPhoneCh);
                System.out.print("변경된 직원의 입사일을 입력하세요(예-2023.04.08) : ");
                String staffHireDateCh=sc.nextLine();
                changeStaff.setHireDate(staffHireDateCh);
                System.out.print("변경된 직원의 출근 상태를 입력하세요(O/X) : ");
                String staffWorkStatusCh = sc.nextLine();
                changeStaff.setWorkStatus(staffWorkStatusCh);
                System.out.print("변경된 직원을 관리하는 관리자의 이름을 입력하세요 : ");
                String managerNameCh = sc.nextLine();
                changeStaff.setManagerName(managerNameCh);

                String query1 = prop.getProperty("changeStaff");
                try {
                    pstmt = con.prepareStatement(query1);
                    pstmt.setString(1,changeStaff.getStaffName());
                    pstmt.setString(2,changeStaff.getStaffPhone());
                    pstmt.setString(3,changeStaff.getHireDate());
                    pstmt.setString(4,changeStaff.getWorkStatus());
                    pstmt.setString(5,changeStaff.getManagerName());
                    pstmt.setInt(6,changeStaff.getStaffId());

                    result = pstmt.executeUpdate();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }if (result>0){
                System.out.println("직원 정보를 성공적으로 변경했습니다");
            }else {
                System.out.println("직원 정보 변경에 실패했습니다");
            }break;


            case 3:
                //직원 검색
                System.out.print("검색할 직원의 직원 ID를 입력하세요 : ");
                int staffIdSh = sc.nextInt();

                List<StaffDTO> searchList = null;
                String query2 = prop.getProperty("searchStaff");
                StaffDTO searchStaff = null;
                System.out.println("직원 ID가 '" + staffIdSh + "' 인 직원 정보는 다음과 같습니다");
                System.out.println();
                try {

                    pstmt = con.prepareStatement(query2);
                    pstmt.setInt(1,staffIdSh);
                    rset = pstmt.executeQuery();
                    searchList = new ArrayList<>();
                    if (rset.next()){
                        searchStaff = new StaffDTO();
                        searchStaff.setStaffId(rset.getInt("STAFF_ID"));
                        searchStaff.setStaffName(rset.getString("STAFF_NAME"));
                        searchStaff.setStaffPhone(rset.getString("STAFF_PHONE"));
                        searchStaff.setHireDate(rset.getString("HIRE_DATE"));
                        searchStaff.setWorkStatus(rset.getString("WORK_STATUS"));
                        searchStaff.setManagerName(rset.getString("MANAGER_NAME"));

                        searchList.add(searchStaff);

                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }for (StaffDTO searchLi : searchList){
                System.out.println(searchLi);
            }break;


            case 4:
                //직원 삭제
                System.out.print("삭제할 직원의 직원 ID를 입력하세요 : ");
                int staffIdDel = sc.nextInt();
                String query3 = prop.getProperty("deleteStaff");


                try {
                    StaffDTO deleteStaff = new StaffDTO();
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
    public void insertProduct(Connection con) {

        Scanner sc = new Scanner(System.in);

        PreparedStatement pstmt = null;
        int result =0;
        ConvenienceStoreDTO product = new ConvenienceStoreDTO();
        String query = prop.getProperty("insertProduct");

        System.out.print("추가할 상품의 이름을 입력하세요 : ");
        String productName = sc.nextLine();
        product.setName(productName);
        System.out.print("추가할 상품의 가격을 입력하세요 : ");
        int productPrice = sc.nextInt();
        product.setPrice(productPrice);
        System.out.print("추가할 상품의 카테고리를 입력하세요(식사/음료/주류/위생용품/기타) : ");
        sc.nextLine();
        String productCategory = sc.nextLine();
        product.setCategory(productCategory);
        System.out.print("추가할 상품의 재고 여부를 입력하세요(O/X) : ");
        String productStatus = sc.nextLine();
        product.setProductStatus(productStatus);


        try {
            pstmt=con.prepareStatement(query);
            pstmt.setString(1,product.getName());
            pstmt.setInt(2,product.getPrice());
            pstmt.setString(3,product.getCategory());
            pstmt.setString(4,product.getProductStatus());

            result= pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(pstmt);
        }if (result>0){
        System.out.println("상품을 성공적으로 추가했습니다");
        }else {
            System.out.println("상품 추가에 실패했습니다");
        }
    }


    //상품 조회
    public void searchProduct(Connection con){
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<ConvenienceStoreDTO> storeList = null;
        List<ConvenienceStoreDTO> storeList1 = null;
        List<ConvenienceStoreDTO> storeList2 = null;
        List<ConvenienceStoreDTO> storeList3 = null;
        List<ConvenienceStoreDTO> storeList4 = null;
        ConvenienceStoreDTO product = new ConvenienceStoreDTO();
        int result = 0;

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
                    product = new ConvenienceStoreDTO();
                    pstmt = con.prepareStatement(query);
                    pstmt.setInt(1,productCode);
                    storeList = new ArrayList<>();
                    rset = pstmt.executeQuery();

                    if (rset.next()){
                        product.setCode(rset.getInt("PRODUCT_CODE"));
                        product.setName(rset.getString("PRODUCT_NAME"));
                        product.setPrice(rset.getInt("PRODUCT_PRICE"));
                        product.setCategory(rset.getString("PRODUCT_CATEGORY"));
                        product.setProductStatus(rset.getString("PRODUCT_STATUS"));

                        storeList.add(product);
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);

            }finally {
                    close(pstmt);
                    close(rset);
                }for (ConvenienceStoreDTO storeDTO : storeList){
                            System.out.println(storeDTO);
                        }
                System.out.println("상품코드가 '" + productCode + " 번'인 상품 검색을 완료했습니다");
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
                    storeList1 = new ArrayList<>();

                    if (rset.next()){
                        ConvenienceStoreDTO product1 = new ConvenienceStoreDTO();
                        product1.setCode(rset.getInt("PRODUCT_CODE"));
                        product1.setName(rset.getString("PRODUCT_NAME"));
                        product1.setPrice(rset.getInt("PRODUCT_PRICE"));
                        product1.setCategory(rset.getString("PRODUCT_CATEGORY"));
                        product1.setProductStatus(rset.getString("PRODUCT_STATUS"));

                        storeList1.add(product1);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }finally {
                    close(pstmt);
                    close(rset);
                } for (ConvenienceStoreDTO storeDTO : storeList1){
                System.out.println(storeDTO);
            }
                System.out.println("상품이름이 '" + productName+ "' 인 상품 검색을 완료했습니다");
                break;

            case 3:
                //편의점 테이블에서 상품가격으로 상품조회
                String query2 = prop.getProperty("searchProductPrice");
                System.out.print("검색할 상품의 가격을 입력하세요 : ");
                sc.nextLine();
                int productPrice = sc.nextInt();

                try {
                    pstmt =con.prepareStatement(query2);
                    pstmt.setInt(1,productPrice);
                    rset = pstmt.executeQuery();

                    storeList2 = new ArrayList<>();
                    while (rset.next()){
                        ConvenienceStoreDTO product2 = new ConvenienceStoreDTO();

                        product2.setCode(rset.getInt("PRODUCT_CODE"));
                        product2.setName(rset.getString("PRODUCT_NAME"));
                        product2.setPrice(rset.getInt("PRODUCT_PRICE"));
                        product2.setCategory(rset.getString("PRODUCT_CATEGORY"));
                        product2.setProductStatus(rset.getString("PRODUCT_STATUS"));

                        storeList2.add(product2);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }finally {
                    close(pstmt);
                    close(rset);
                }
                for (ConvenienceStoreDTO storeDTO : storeList2){
                    System.out.println(storeDTO);
                }
//                System.out.println(storeList2);

                System.out.println("가격이 '" + productPrice+ " 원'인 상품 검색을 완료했습니다");
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

                    storeList3 = new ArrayList<>();
                    while (rset.next()){
                        ConvenienceStoreDTO product2 = new ConvenienceStoreDTO();

                        product2.setCode(rset.getInt("PRODUCT_CODE"));
                        product2.setName(rset.getString("PRODUCT_NAME"));
                        product2.setPrice(rset.getInt("PRODUCT_PRICE"));
                        product2.setCategory(rset.getString("PRODUCT_CATEGORY"));
                        product2.setProductStatus(rset.getString("PRODUCT_STATUS"));

                        storeList3.add(product2);

                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }finally {
                    close(pstmt);
                    close(con);}
                for (ConvenienceStoreDTO storeDTO : storeList3){
                    System.out.println(storeDTO);
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

                    storeList4 = new ArrayList<>();
                    while (rset.next()){
                        ConvenienceStoreDTO product3 = new ConvenienceStoreDTO();

                        product3.setCode(rset.getInt("PRODUCT_CODE"));
                        product3.setName(rset.getString("PRODUCT_NAME"));
                        product3.setPrice(rset.getInt("PRODUCT_PRICE"));
                        product3.setCategory(rset.getString("PRODUCT_CATEGORY"));
                        product3.setProductStatus(rset.getString("PRODUCT_STATUS"));

                        storeList4.add(product3);

                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }for (ConvenienceStoreDTO storeDTO : storeList4){
                System.out.println(storeDTO);
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
        ConvenienceStoreDTO convenienceStoreDTO = new ConvenienceStoreDTO();
        String query = prop.getProperty("changeProduct");

        System.out.print("변경하려는 상품의 상품코드를 입력하세요 : ");
        int productCode = sc.nextInt();
        convenienceStoreDTO.setCode(productCode);
        System.out.print("상품의 변경 된 이름을 입력하세요 : ");
        sc.nextLine();
        String productName = sc.nextLine();
        convenienceStoreDTO.setName(productName);
        System.out.print("상품의 변경 된 가격을 입력하세요 : ");
        int productPrice = sc.nextInt();
        convenienceStoreDTO.setPrice(productPrice);
        System.out.print("상품의 변경 된 카테고리를 입력하세요(식사/음료/주류/위생용품/기타) : ");
        sc.nextLine();
        String productCategory = sc.nextLine();
        convenienceStoreDTO.setCategory(productCategory);
        System.out.print("상품의 변경된 재고 여부를 입력하세요(O/X) : ");
        String productStatus = sc.nextLine();
        convenienceStoreDTO.setProductStatus(productStatus);

        try {
            ptsmt = con.prepareStatement(query);

            ptsmt.setString(1,convenienceStoreDTO.getName());
            ptsmt.setInt(2,convenienceStoreDTO.getPrice());
            ptsmt.setString(3,convenienceStoreDTO.getCategory());
            ptsmt.setString(4,convenienceStoreDTO.getProductStatus());
            ptsmt.setInt(5,convenienceStoreDTO.getCode());

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
        ConvenienceStoreDTO storeDTO = new ConvenienceStoreDTO();
        Scanner sc = new Scanner(System.in);
        System.out.print("삭제할 상품의 이름을 입력하세요 : ");
        String productName = sc.nextLine();
        storeDTO.setName(productName);
        String query = prop.getProperty("deleteProduct");
        try {
            ptsmt = con.prepareStatement(query);
            ptsmt.setString(1,storeDTO.getName());

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
