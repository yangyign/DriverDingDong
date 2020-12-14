package net.onest.driverdingdong;

public class Driver {
    private int id;
    private String name;
    private int age;
    private String sex;
    private String car;
    private String style;
    private String experience;
    private String img;
    private String status;
    private String phone;
    private String idcard;
    private String carnumber;
    private String address;
    private String carnumberimage;
    public String getIdcard() {
        return idcard;
    }
    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
    public String getCarnumber() {
        return carnumber;
    }
    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCarnumberimage() {
        return carnumberimage;
    }
    public void setCarnumberimage(String carnumberimage) {
        this.carnumberimage = carnumberimage;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
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
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getCar() {
        return car;
    }
    public void setCar(String car) {
        this.car = car;
    }
    public String getStyle() {
        return style;
    }
    public void setStyle(String style) {
        this.style = style;
    }
    public String getExperience() {
        return experience;
    }
    public void setExperience(String experience) {
        this.experience = experience;
    }
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    @Override
    public String toString() {
        return "Driver [id=" + id + ", name=" + name + ", age=" + age + ", car=" + car + ", style=" + style
                + ", experience=" + experience + ", img=" + img + ", status=" + status + ", phone=" + phone + "]";
    }



}
