package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.DTO;


import java.util.List;


public class OrderRequest {

    private Long phoneNumber;
    private String typeOfProduct;
    private String numberOfServings;
    private List<String> listOfTastes;
    private String description;
    private String exampleLink;


    public OrderRequest() {
    }

    public OrderRequest(Long phoneNumber,
                        String typeOfProduct,
                        String numberOfServings,
                        List<String> listOfTastes,
                        String description,
                        String exampleLink) {
        this.phoneNumber = phoneNumber;
        this.typeOfProduct = typeOfProduct;
        this.numberOfServings = numberOfServings;
        this.listOfTastes = listOfTastes;
        this.description = description;
        this.exampleLink = exampleLink;
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

    public String getExampleLink() {
        return exampleLink;
    }

    public void setExampleLink(String exampleLink) {
        this.exampleLink = exampleLink;
    }
}
