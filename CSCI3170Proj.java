import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
public class CSCI3170Proj {
    public static String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db24?autoReconnect=true&useSSL=false";
    public static String dbUsername = "Group24";
    public static String dbPassword = "CSCI3170";

    public static Connection connectToMySQL(){
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
        } catch (ClassNotFoundException e){
            System.out.println("[Error]: Java MySQL DB Driver not found!!");
            System.exit(0);
        } catch (SQLException e){
            System.out.println(e);
        }
        return con;
    }


    public static void main(String[] args){

        Connection mySQLDB =connectToMySQL();
        boolean running =true;
        while(running){
            System.out.println("Welcome to sales system!");
            System.out.println("-----Main menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. Operations for administrator");
            System.out.println("2. Operations for salesperson");
            System.out.println("3. Operations for manager");
            System.out.println("4. Exit this program");
            System.out.print("Enter your choice: ");
            Scanner sc = new Scanner(System.in);
            int choice=-1;
            try {
                choice = sc.nextInt();
            }
            catch (Exception e){
                System.out.println("Invalid input");
                sc.nextLine();
                continue;
            }
            if (choice==4){
                running=false;
                break;
            }
            else if(choice==1){
                boolean running1 =true;
            while(running1){
                System.out.println();
                System.out.println("-----Operations for administrator menu-----");
                System.out.println("What kind of operation would you like to perform?");
                System.out.println("1. Create all tables");
                System.out.println("2. Delete all tables");
                System.out.println("3. Load from datafile");
                System.out.println("4. Show content of a table");
                System.out.println("5. Return to the main menu");
                System.out.print("Enter Your Choice: ");
                int choice1=-1;
                try {
                    choice1 = sc.nextInt();
                }
                catch (Exception e){
                    System.out.println("Invalid input");
                    sc.nextLine();
                    continue;
                }
                if(choice1==5)
                {   running1 = false;
                    break;
                }
                else if(choice1==1)
                {   System.out.print("Processing...");
                    try {
                        Administrator.createAllTables(mySQLDB);
                    } catch (Exception e) {
                        System.out.println("fail to create tables.");
                        continue;
                    }
                    System.out.println("Done. Database is initialized.");
                }
                else if(choice1==2)
                {   System.out.print("Processing...");
                    try {
                        Administrator.deleteAllTables(mySQLDB);
                    } catch (Exception e) {
                        System.out.println("fail to delete tables");
                        continue;
                    }
                    System.out.println("Done. Database is removed");
                }
                else if (choice1==3){
                    System.out.println();
                    System.out.print("Type in the Source Data Folder Path: ");
                    sc.nextLine();
                    String path = sc.nextLine();
                    System.out.print("Processing...");
                    try {
                        Administrator.loaddata(mySQLDB, path);
                    } catch (Exception e) {
                        System.out.println("Error: fail to load data.");
                        continue;
                    }
                    System.out.println("Done. Data is inputted to the database.");

                }
                else if (choice1==4){
                    System.out.print("Which table would you like to show: ");
                    sc.nextLine();
                    String tablename = sc.nextLine();
                    System.out.println("Content of table "+tablename+":");
                    try {
                        Administrator.printATable(mySQLDB,tablename);
                    } catch (Exception e){
                        System.out.println("Error: Table doesn't exist");

                    }
                }
                else {System.out.println("Invalid input");}

            }

            }
            else if(choice==2){
                boolean running2 = true;
                while (running2){
                    System.out.println();
                    System.out.println("-----Operations for salesperson menu-----");
                    System.out.println("What kind of operation would you like to perform?");
                    System.out.println("1. Search for parts");
                    System.out.println("2. Sell a part");
                    System.out.println("3. Return to the main menu");
                    System.out.print("Enter Your Choice: ");
                    int choice2=-1;
                    try {
                        choice2 = sc.nextInt();
                    }
                    catch (Exception e){
                        System.out.println("Invalid input");
                        sc.nextLine();
                        continue;
                    }
                    if(choice2==3){
                        running2=false;
                        break;

                    }
                    else if(choice2==1){
                        while(true){
                        System.out.println("Choose the Search criterion:");
                        System.out.println("1. Part Name");
                        System.out.println("2. Manufacturer Name");
                        System.out.print("Choose the Search criterion: ");
                        int choice21=-1;
                        try {
                            choice21 = sc.nextInt();
                        }
                        catch (Exception e){
                            System.out.println("Invalid input");
                            sc.nextLine();
                            continue;
                        }
                        if(choice21>2||choice21<1){
                            System.out.println("Invalid input");
                            continue;
                        }
                        System.out.print("Type in the search keyword: ");
                        sc.nextLine();
                        String keyword =sc.nextLine();
                        System.out.println("Choose ordering:");
                        System.out.println("1. By price, ascending order");
                        System.out.println("2. By price, descending order");
                        System.out.print("Choose the search criterion: ");
                        int choice22=-1;
                        try {
                            choice22 = sc.nextInt();
                        }
                        catch (Exception e){
                            System.out.println("Invalid input");
                            sc.nextLine();
                            continue;
                        }
                        if(choice22>2||choice22<1){
                                System.out.println("Invalid input");
                                sc.nextLine();
                                continue;
                            }
                        try {
                            Salesperson.searchForParts(mySQLDB,keyword,choice21,choice22);
                        } catch (Exception e) {
                            System.out.println("Error: fail to search for parts.");
                            continue;
                        }
                        System.out.println("End of Query");
                        break;
                    }}
                    else if(choice2==2){
                        while (true){
                        System.out.print("Enter the Part ID: ");
                        int PartID =-1;
                        try {
                            PartID = sc.nextInt();
                        }
                        catch (Exception e){
                            System.out.println("Invalid input");
                            sc.nextLine();
                            continue;
                        }
                        System.out.print("Enter the Salesperson ID: ");
                        int SalespersonID =-1;
                        try {
                           SalespersonID = sc.nextInt();
                        }
                        catch (Exception e){
                            System.out.println("Invalid input");
                            sc.nextLine();
                            continue;
                        }
                        String pattern = "dd/MM/yyyy";
                        String Date = new SimpleDateFormat(pattern).format(new Date());
                        try {
                            Salesperson.sellAPart(mySQLDB,PartID,SalespersonID,Date);
                        } catch (Exception e) {
                            System.out.println("Error: fail to sell the part.");
                            break;
                        }
                        break;
                    }}
                    else{System.out.println("Invalid Input");
                    }


                }





            }
            else if(choice ==3){
                boolean running3 =true;
                while (running3){
                    System.out.println("-----Operations for manager menu-----");
                    System.out.println("What kinds of operation would you like to perform?");
                    System.out.println("1. List all salespersons");
                    System.out.println("2. Count the no. of sales record of each salesperson under a specific range on years of experience");
                    System.out.println("3. Show the total sales value of each manufacturer");
                    System.out.println("4. Show the N most popular part");
                    System.out.println("5. Return to the main menu");
                    System.out.print("Enter Your Choice: ");
                    int choice3=-1;
                    try {
                        choice3 = sc.nextInt();
                    }
                    catch (Exception e){
                        System.out.println("Invalid input");
                        sc.nextLine();
                        continue;
                    }
                    if (choice3==5){
                        running3 =false;
                        break;
                    }
                    else if(choice3==1){
                        while(true){
                        System.out.println("Choose ordering:");
                        System.out.println("1. By ascending order");
                        System.out.println("2. By descending order");
                        System.out.print("Choose the list of ordering: ");
                        int choice31=-1;
                        try {
                            choice31 = sc.nextInt();
                        }
                        catch (Exception e){
                            System.out.println("Invalid input");
                            sc.nextLine();
                            continue;
                        }
                        if(choice31<1||choice31>2){
                            System.out.println("Invalid input");
                            sc.nextLine();
                            continue;}
                        try {
                            Manager.listAllSalespersons(mySQLDB,choice31);
                        } catch (Exception e) {
                            System.out.println("Error: fail to list all the salesperson.");
                            continue;
                        }break;
                    }
                    }
                    else if(choice3 ==2){
                      while (true){
                          System.out.print("Type in the lower bound for years of experience: ");
                          int lowerbound=-1;
                          try{
                              lowerbound=sc.nextInt();
                          }
                          catch(Exception e){
                              System.out.println("Invalid input");
                              sc.nextLine();
                              continue;
                          }
                           sc.nextLine();
                          System.out.print("Type in the upper bound for years of experience: ");
                          int upperbound=-1;
                          try{
                              upperbound=sc.nextInt();
                          }
                          catch(Exception e){
                              System.out.println("Invalid input");
                              sc.nextLine();
                              continue;
                          }
                          if(lowerbound<0||upperbound<0||lowerbound>upperbound){
                              System.out.println("Invalid input");
                              sc.nextLine();
                              continue;
                          }
                          try{
                              Manager.listSalesPersonsWithTransactionNumber(mySQLDB,lowerbound,upperbound);
                          }
                          catch(Exception e){
                              System.out.println("Error: fail to count the number");
                              continue;
                          }
                          System.out.println("End of Query");
                       break;
                      }


                    }
                    else if(choice3==3){
                       try{
                           Manager.listManufacturersWithTotalSalesValue(mySQLDB);
                       }
                       catch(Exception e){
                           System.out.println("Error: fail to sort and list the manufacturers");
                           continue;
                       }
                       System.out.println("End of Query");
                    }
                    else if(choice3==4){
                        while(true){
                            System.out.print("Type in the number of parts: ");
                            int Num=-1;
                            try{
                                Num=sc.nextInt();
                            }
                            catch(Exception e){
                                System.out.println("Invalid input");
                                sc.nextLine();
                                continue;
                            }
                            if(Num<0){
                                System.out.println("Invalid input");
                                sc.nextLine();
                                continue;
                            }
                            try{
                                Manager.showNMostPopularParts(mySQLDB,Num);
                            }
                            catch(Exception e){
                                System.out.println("Error: fail to show the N most popular parts");
                                continue;
                            }
                            System.out.println("End of Query");
                            break;

                        }


                    }

                }




            }


        }


    }



}