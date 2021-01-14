package dk.gruppea3moro.moroa3.model;

import java.io.Serializable;

public class AddressDTO implements Serializable {
    String area;
    String streetName;
    String streetNumber;
    String zipCode;

    //Optional information, could be null.
    String additionalText;
    String addressName;

    public AddressDTO(String addressName, String streetName, String streetNumber, String additionalText, String zipCode, String area) {
        if (additionalText == null) {
            additionalText = "";
        }
        if (area == null) {
            area = "";
        }
        this.area = area;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.addressName = addressName;
    }

    public AddressDTO() {
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAdditionalText() {
        return additionalText;
    }

    public void setAdditionalText(String additionalText) {
        this.additionalText = additionalText;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    @Override
    public String toString() {
        String result = "";
        if (addressName != null) {
            result += addressName + "\n";
        }
        result += streetName + " " + streetNumber;
        if (additionalText != null) {
            result += " " + additionalText;
        }
        result += "\n" + zipCode + " " + area;
        return result;
    }
}
