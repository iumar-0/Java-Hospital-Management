package Pharmacy;


public class Pharmacy {
    public String product_name;
    private int batch_id;
    public int quantity;
    public float price;
    public String expiry_date;


    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public Pharmacy(int batch_id, String expiry_date, float price, String product_name, int quantity) {
        this.batch_id = batch_id;
        this.expiry_date = expiry_date;
        this.price = price;
        this.product_name = product_name;
        this.quantity = quantity;
    }

    public int getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(int batch_id) {
        this.batch_id = batch_id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getProduct_name() {
        return product_name;
    }


    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void print_medicine() {
        System.out.println("Product name : " + getProduct_name());
        System.out.println("Product Batch ID : " + getBatch_id());
        System.out.println("Product Quantity : " + getQuantity());
        System.out.println("Product Price : " + getPrice());
        System.out.println("Product Expiry Date : " + getExpiry_date());
        System.out.println();
    }

    public void print_receipt(int quantity) {
        System.out.println("--- Printing Receipt ---");
        System.out.println("Product name : " + getProduct_name());
        System.out.println("Product Batch ID : " + getBatch_id());
        int i = getQuantity() - quantity;
        setQuantity(i);
        System.out.println("Product Quantity : " + quantity);
        System.out.println("Product Price : " + getPrice());
        System.out.println("Product Expiry Date : " + getExpiry_date());
        System.out.println();
    }
}
