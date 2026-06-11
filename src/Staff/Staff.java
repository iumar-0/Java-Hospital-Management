package Staff;

public class Staff {
    public String name;
    private int id;
    public String staff;
    private String shift = "Morning (8:00 AM - 4:00 PM)";

    public Staff(String name, int id, String staff, String shift) {
        this.name = name;
        this.id = id;
        this.staff = staff;
        this.shift = shift;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    //    public void print() {
//        System.out.println("--- Laboratory Staff ---");
//        System.out.println("Staff name : " + getName());
//        System.out.println("Staff id : " + getId());
//    }
    public void print() {
        System.out.println("--- Staff details ---");
        System.out.println("Staff name : " + getName());
        System.out.println("Staff id : " + getId());
        System.out.println("Staff member : " + this.staff);
        System.out.println("Shift : " +getShift());
    }

}
