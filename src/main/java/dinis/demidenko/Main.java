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
        System.out.println("Add client - 1");
        System.out.println("Add goods - 2");
        System.out.println("Create order - 3");
        System.out.println("Show clients - 4");
        System.out.println("Show goods - 5");
        System.out.println("Show orders - 6");
        System.out.println("Exit - 0");
       while (true){
           Scanner sc = new Scanner(System.in);
           int p = sc.nextInt();
           switch (p){
               case 1:
                   addClient();
                   break;
               case 2:
                   addGoods();
                   break;
               case 3:
                   addOrder();
                   break;
               case 4:
                   viem(1);
                   break;
               case 5:
                   viem(2);
                   break;
               case 6:
                   showOrders();
                   break;
               case 0:
                   return;
               default:
                   System.out.println("Error comand");
                   break;
           }
       }
    }
    private static void showOrders(){
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM order2");
            PreparedStatement psCl = con.prepareStatement("SELECT * FROM clients");
            PreparedStatement psGo = con.prepareStatement("SELECT * FROM goods");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                ResultSet rsCl = psCl.executeQuery();
                while (rsCl.next()){
                    if(rs.getInt(1) == rsCl.getInt(1)){
                        System.out.print(rsCl.getString(4) + " " + rsCl.getString(3) + " - ");

                    }
                }
                ResultSet rsGo = psGo.executeQuery();
                while (rsGo.next()){
                    if(rs.getInt(2) == rsGo.getInt(1)){
                        System.out.println(rsGo.getString(2) + " " + rsGo.getString(4));
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void addOrder(){
        int tempCl = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter email");
        String email = sc.nextLine();
        System.out.println("Enter goods");
        String goods = sc.nextLine();
        try {
            Statement st = con.createStatement();
            PreparedStatement psCl = con.prepareStatement("SELECT * FROM clients");
            PreparedStatement psGo = con.prepareStatement("SELECT * FROM goods");
            ResultSet rsCl = psCl.executeQuery();
            ResultSet rsGo = psGo.executeQuery();
            while (rsCl.next()) {
                if(rsCl.getString(4).equals(email)){
                    tempCl = rsCl.getInt(1);
                }
            }
            while (rsGo.next()){
                if(rsGo.getString(2).equals(goods)){
                    System.out.println(rsGo.getString(1));
                    st.execute("INSERT INTO order2 VALUES(" + tempCl + ", " + rsGo.getInt(1) + ");");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
