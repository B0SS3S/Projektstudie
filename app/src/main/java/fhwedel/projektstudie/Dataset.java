package fhwedel.projektstudie;

public class Dataset {

    private String product;
    private String restaurant;
    private long id;


    public Dataset(String product, String restaurant, long id) {
        this.product = product;
        this.restaurant = restaurant;
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }


    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        String output = restaurant + " - " + product;

        return output;
    }

    public void insertData() {
        Dataset data = new Dataset("Currywurst", "CurrywurstPalace", 1);
    }
}