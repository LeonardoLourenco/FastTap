package leonardolourenco.fasttap;

public class Users {
    private int id;
    private String UserName;
    private int GStar;
    private int BoughtSkin0;    //Alpha Skin Bought by default | 0 -Not bought 1 -Bought
    private int BoughtSkin1;    //Slimes Skin
    private int BoughtSkin2;    //Cookies Skin
    private int BoughtSkin3;    //Ghosts Skin
    private int BoughtSkin4;    //Knights Skin
    private int BoughtSkin5;    //Samurais Skin

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getGStar() {
        return GStar;
    }

    public void setGStar(int GStar) {
        this.GStar = GStar;
    }

    public int getBoughtSkin0() {
        return BoughtSkin0;
    }

    public void setBoughtSkin0(int boughtSkin0) {
        BoughtSkin0 = boughtSkin0;
    }

    public int getBoughtSkin1() {
        return BoughtSkin1;
    }

    public void setBoughtSkin1(int boughtSkin1) {
        BoughtSkin1 = boughtSkin1;
    }

    public int getBoughtSkin2() {
        return BoughtSkin2;
    }

    public void setBoughtSkin2(int boughtSkin2) {
        BoughtSkin2 = boughtSkin2;
    }

    public int getBoughtSkin3() {
        return BoughtSkin3;
    }

    public void setBoughtSkin3(int boughtSkin3) {
        BoughtSkin3 = boughtSkin3;
    }

    public int getBoughtSkin4() {
        return BoughtSkin4;
    }

    public void setBoughtSkin4(int boughtSkin4) {
        BoughtSkin4 = boughtSkin4;
    }

    public int getBoughtSkin5() {
        return BoughtSkin5;
    }

    public void setBoughtSkin5(int boughtSkin5) {
        BoughtSkin5 = boughtSkin5;
    }
}
