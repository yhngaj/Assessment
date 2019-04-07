import java.util.*; //for the use of array list

public class Product{
  private String SKU;
  private String name;
  private double price;
  
  public Product(String SKU, String name, double price){
    this.SKU = SKU;
    this.name = name;
    this.price = price;
  }
  
  public String getSKU() {
    return SKU;
  }
  
  public String getName() {
    return name;
  }
  
  public double getPrice() {
    return price;
  }
  

  
}