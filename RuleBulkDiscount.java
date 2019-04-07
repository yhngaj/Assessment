public class RuleBulkDiscount{
  private String SKU;
  private int BuyMoreThan;
  private double DiscountedPrice;
  
  public RuleBulkDiscount(String SKU, int BuyMoreThan, double DiscountedPrice){
    this.SKU = SKU;
    this.BuyMoreThan = BuyMoreThan;
    this.DiscountedPrice = DiscountedPrice;
  }
  
  public String getSKU() {
    return SKU;
  }
  
  public int BuyMoreThan() {
    return BuyMoreThan;
  }
  
  public double DiscountedPrice() {
    return DiscountedPrice;
  }
  

  
}