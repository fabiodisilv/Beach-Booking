
package it.univaq.disim.sose.beachbooking.beachbooking.model.accuweather.location;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "ID",
    "LocalizedName",
    "EnglishName"
})
public class Country {

    @JsonProperty("ID")
    private String iD;
    @JsonProperty("LocalizedName")
    private String localizedName;
    @JsonProperty("EnglishName")
    private String englishName;

    @JsonProperty("ID")
    public String getID() {
        return iD;
    }

    @JsonProperty("ID")
    public void setID(String iD) {
        this.iD = iD;
    }

    @JsonProperty("LocalizedName")
    public String getLocalizedName() {
        return localizedName;
    }

    @JsonProperty("LocalizedName")
    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    @JsonProperty("EnglishName")
    public String getEnglishName() {
        return englishName;
    }

    @JsonProperty("EnglishName")
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

}
