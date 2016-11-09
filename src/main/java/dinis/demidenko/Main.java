package dinis.demidenko;

import java.sql.*;
import java.util.Scanner;

/**
 * Created by dende on 09.11.2016.
 */
public class Main {
    static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/shop";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "0000";
    static Connection con;

    public static void main(String[] args) {

        try {
            con = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //addClient();
        addGoods();
        viem(2);




    }
    private static void addGoods(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name");
        String name = sc.nextLine();
        System.out.println("Enter weight");
        int weight = sc.nextInt();
        System.out.println("Enter price");
        int price = sc.nextInt();
        try {
            Statement st = con.createStatement();
            st.execute("INSERT INTO goods VALUES(0, '" + name + "', '" + weight + "' ,'" + price + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void addClient(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name");
        String name = sc.nextLine();
        System.out.println("Enter surname");
        String surname = sc.nextLine();
        System.out.println("Enter email");
        String email = sc.nextLine();
        try {
            Statement st = con.createStatement();
            st.execute("INSERT INTO clients VALUES(0, '" + name + "', '" + surname + "' ,'" + email + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void viem(int v){
        String table = "clients";
        if( v == 2){
            table = "goods";
        }
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM " + table);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for(int i = 1;i <= 4; i++){
                    System.out.print(rs.getString(i) + ", ");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
