package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO;


import java.util.List;


public class OrderRequest {

    private Long phoneNumber;
    private String eventDate;
    private String typeOfProduct;
    private String numberOfServings;
    private List<String> listOfTastes;
    private String description;

    private JsonMultipartFile jsonMultipartFile;



    public OrderRequest() {
    }

    public OrderRequest(Long phoneNumber,
                        String eventDate,
                        String typeOfProduct,
                        String numberOfServings,
                        List<String> listOfTastes,
                        String description,
                        JsonMultipartFile jsonMultipartFile) {
        this.phoneNumber = phoneNumber;
        this.eventDate = eventDate;
        this.typeOfProduct = typeOfProduct;
        this.numberOfServings = numberOfServings;
        this.listOfTastes = listOfTastes;
        this.description = description;
        this.jsonMultipartFile = jsonMultipartFile;

    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTypeOfProduct() {
        return typeOfProduct;
    }

    public void setTypeOfProduct(String typeOfProduct) {
        this.typeOfProduct = typeOfProduct;
    }

    public String getNumberOfServings() {
        return numberOfServings;
    }

    public void setNumberOfServings(String numberOfServings) {
        this.numberOfServings = numberOfServings;
    }

    public List<String> getListOfTastes() {
        return listOfTastes;
    }

    public void setListOfTastes(List<String> listOfTastes) {
        this.listOfTastes = listOfTastes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public JsonMultipartFile getJsonMultipartFile() {
        return jsonMultipartFile;
    }

    public void setJsonMultipartFile(JsonMultipartFile jsonMultipartFile) {
        this.jsonMultipartFile = jsonMultipartFile;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "phoneNumber=" + phoneNumber +
                ", eventDate='" + eventDate + '\'' +
                ", typeOfProduct='" + typeOfProduct + '\'' +
                ", numberOfServings='" + numberOfServings + '\'' +
                ", listOfTastes=" + listOfTastes +
                ", description='" + description + '\'' +
                ", jsonMultipartFile=" + jsonMultipartFile.toString() +
                '}';
    }
}
