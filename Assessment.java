import java.util.*;
import java.io.*;
import java.text.*;

public class Assessment{
  
  public static void main(String[] args) throws Exception{
    
    //import products
    File file = new File("productList.txt");
    ArrayList<Product> ProductArrayList = new ArrayList<>();
    Scanner scanner = new Scanner(file);  // define a Scanner object to read the file
    String line;
    scanner.nextLine(); 
    scanner.nextLine(); //will start at the 3rd line
    
    String[] parts = new String [4];
    
    // write a loop to get the product data, and store to corresponding array lists with objects inside
    while(scanner.hasNextLine()){
      line = scanner.nextLine();
      
      line = line.replaceAll("\\s","").replace("$","");
      parts = line.split("\\|");
      
      ProductArrayList.add(new Product(parts[1],parts[2],Double.parseDouble(parts[3])));
    }
    
    //import the pricing rules
    //for BuyXPayY
    ArrayList<RuleBuyXPayY> RuleBuyXPayYArrayList = new ArrayList<>();
    File file1 = new File("Rule1 - BuyXPayY.txt");    
    Scanner scanner1 = new Scanner(file1);
    String line1;
    line1 = scanner1.nextLine(); 
    line1 = scanner1.nextLine(); 
    
    Arrays.fill(parts, null);
    
    // write a loop to get the BuyXPayY rule data
    while(scanner1.hasNextLine()){
      line1 = scanner1.nextLine();
      
      line1 = line1.replaceAll("\\s","");
      parts = line1.split("\\|");
      
      RuleBuyXPayYArrayList.add(new RuleBuyXPayY(parts[1],Integer.parseInt(parts[2]),Integer.parseInt(parts[3])));
    }
    
    //for BulkDiscount
    ArrayList<RuleBulkDiscount> RuleBulkDiscountArrayList = new ArrayList<>();
    File file2 = new File("Rule2 - BulkDiscount.txt");    
    Scanner scanner2 = new Scanner(file2);
    String line2;
    line2 = scanner2.nextLine(); 
    line2 = scanner2.nextLine(); 
    
    Arrays.fill(parts, null);
    
    // write a loop to get the bulk discount rule data
    while(scanner2.hasNextLine()){
      line2 = scanner2.nextLine();
      line2 = line2.replaceAll("\\s","").replace("$","");
      parts = line2.split("\\|");
      RuleBulkDiscountArrayList.add(new RuleBulkDiscount(parts[1],Integer.parseInt(parts[2]),Double.parseDouble(parts[3])));
    }
    
    //for Bundle
    ArrayList<RuleBundle> RuleBundleArrayList = new ArrayList<>();
    File file3 = new File("Rule3 - Bundle.txt");    
    Scanner scanner3 = new Scanner(file3);
    String line3;
    line3 = scanner3.nextLine(); 
    line3 = scanner3.nextLine(); 
    
    Arrays.fill(parts, null);
    
    // write a loop to get the bundle rule data
    while(scanner3.hasNextLine()){
      line3 = scanner3.nextLine();
      line3 = line3.replaceAll("\\s","").replace("$","");
      parts = line3.split("\\|");
      RuleBundleArrayList.add(new RuleBundle(parts[1],parts[2]));
    }
    
    //start the item input
    String item;
    Scanner input = new Scanner(System.in);
    ArrayList<String> SKUName = new ArrayList<>();
    ArrayList<Integer> Quantity = new ArrayList<>();
    int repeatQuantity;
    boolean productExist;
    
    System.out.print("Enter the SKU (directly press enter if finished): ");
    item = input.nextLine();
    
    //input the items into a order
    while (!item.equals("")) {
      
      productExist = false;
      for (Product p: ProductArrayList) { //check if SKU is typed correctly
        if (item.equals(p.getSKU())){
          productExist = true;
        }
      }
      if (!productExist){
        System.out.println("Typo: SKU does not exist! Please try again.");
        System.out.print("Enter the SKU (directly press enter if finished): ");
        item = input.nextLine();
        continue;  
      }
      
      if (SKUName.contains(item)){ //if the item is already in the order list
        repeatQuantity = Quantity.get(SKUName.indexOf(item));
        Quantity.set(SKUName.indexOf(item), repeatQuantity + 1);
      }
      else{ //the item is new in the whole order
        SKUName.add(item);
        Quantity.add(1);
      }
      System.out.print("Enter the SKU (directly press enter if finished): "); //get ready for the next item
      item = input.nextLine();
    }
    
    //notify the ordered SKU and ordered quantity
    System.out.println("SKUs Scanned: " + SKUName);
    System.out.println("The Quantity: " + Quantity);
    
    //compute the Total
    double Total = 0;
    int q1 = 0; //for quantity of bundled expensive item
    int q2 = 0; //for quantity of bundled free item
    double price = 0; 
    int quantity = 0; //quantity
    
    for(String s: SKUName){
      
      for (Product p: ProductArrayList) { //grab the original price & quantity
        if (s.equals(p.getSKU())){
          quantity = Quantity.get(SKUName.indexOf(s));
          price = p.getPrice();
        }
      }
      
      //check bulk discount
      for(RuleBulkDiscount bulk: RuleBulkDiscountArrayList){
        if (s.equals(bulk.getSKU())){
          if (quantity >= bulk.BuyMoreThan())
            price = bulk.DiscountedPrice();
        }
      }
      
      //check BuyXPayY
      for(RuleBuyXPayY buy: RuleBuyXPayYArrayList){
        if (s.equals(buy.getSKU())){
          if (quantity >= buy.getBuyX()){
            quantity = ((quantity / buy.getBuyX()) * buy.getPayY()) + quantity % buy.getBuyX();
          }
        }
      }
      
      //check bundle sale
      for(RuleBundle bundle: RuleBundleArrayList){
        if (s.equals(bundle.getSKU2())){
          if (SKUName.contains(bundle.getSKU1())){
            q1 = Quantity.get(SKUName.indexOf(bundle.getSKU1()));
            q2 = Quantity.get(SKUName.indexOf(bundle.getSKU2()));
            if (q1 >= q2)
              quantity = 0; //all items of SKU2 are free of charge
            else
              quantity = q2 - q1;
          }
        }
      }
      
      Total = Total + quantity * price;
      
    }
    
    System.out.println("The expected total: $" + Total + ".");
    
  }
  
}
