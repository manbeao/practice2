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

    public void checkManagerId(Connection con){
        Scanner sc = new Scanner(System.in);
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {
            System.out.print("== 관리자 코드를 입력해주세요 : ");
            int managerId = sc.nextInt();

            String query = prop.getProperty("checkManagerId");
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1,managerId);

            rset= pstmt.executeQuery();
            System.out.println("rset = " + rset);

            if (managerId == 1 || managerId ==2 || managerId ==3 || managerId ==4||managerId==5){
                System.out.println("환영합니다 " + managerId + "번 관리자님");
                System.out.println();
            }else {
                System.out.println("프로그램을 종료합니다");

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(pstmt);
            close(rset);
            close(con);
        }
    }
}
