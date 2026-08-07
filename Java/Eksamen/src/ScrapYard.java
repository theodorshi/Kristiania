public class ScrapYard {
    private int scrapyardID;
    private String name;
    private String address;
    private String phoneNumber;

    public ScrapYard(int scrapyardID, String name, String address, String phoneNumber) {
        this.scrapyardID = scrapyardID;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getScrapyardID() {
        return scrapyardID;
    }

    public void setScrapyardID(int scrapyardID) {
        this.scrapyardID = scrapyardID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "ScrapYard{" +
                "scrapyardID=" + scrapyardID +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
