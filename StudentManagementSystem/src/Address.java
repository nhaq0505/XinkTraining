import java.util.Objects;

public class Address {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    // Constructor đầy đủ
    public Address(String street, String city, String state, String postalCode, String country) {
        setStreet(street);
        setCity(city);
        setState(state);
        setPostalCode(postalCode);
        setCountry(country);
    }

    // Constructor rút gọn (chỉ cần đường + thành phố)
    public Address(String street, String city) {
        this(street, city, "", "", "");
    }

    // Getters & Setters (có validation cơ bản)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        if (street == null || street.trim().isEmpty()) {
            throw new IllegalArgumentException("Street cannot be empty");
        }
        this.street = street.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be empty");
        }
        this.city = city.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = (state == null) ? "" : state.trim();
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = (postalCode == null) ? "" : postalCode.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be empty");
        }
        this.country = country.trim();
    }

    // Override toString để in địa chỉ
    @Override
    public String toString() {
        return street + ", " + city +
                (state.isEmpty() ? "" : ", " + state) +
                (postalCode.isEmpty() ? "" : " " + postalCode) +
                ", " + country;
    }

    // Override equals & hashCode để so sánh 2 địa chỉ
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) &&
                Objects.equals(city, address.city) &&
                Objects.equals(state, address.state) &&
                Objects.equals(postalCode, address.postalCode) &&
                Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city, state, postalCode, country);
    }
}
