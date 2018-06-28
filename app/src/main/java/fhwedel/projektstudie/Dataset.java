//TODO ALT -> LÖSCHEN

package fhwedel.projektstudie;

public class Dataset {

    private String products;
    private String restaurant;
    private long id;

    /*
    private double Latitude;
    private double Longitude;
    */

    //public Dataset(String product, String restaurant, long id, double Latitude, double Longitude) {
    public Dataset(String product, String restaurant, long id) {
        this.products = product;
        this.restaurant = restaurant;
        this.id = id;
/*
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        */
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
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

    /*
    public double getLatitude() { return Latitude; }

    public void setLatitude(double Latitude) {this.Latitude = Latitude;}

    public double getLongitude() { return Longitude; }

    public void setLongitude(double Longitude) {this.Longitude = Longitude;}
    */

    @Override
    public String toString() {
        String output = restaurant + " - " + products;

        return output;
    }

    public void insertData() {
        //Dataset data = new Dataset("Currywurst", "CurrywurstPalace", 1, 9.993682, 53.551085);
        Dataset data = new Dataset("Currywurst", "CurrywurstPalace", 1);
    }
}