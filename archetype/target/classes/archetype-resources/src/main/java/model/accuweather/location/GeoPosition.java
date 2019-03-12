#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.model.accuweather.location;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Latitude",
    "Longitude",
    "Elevation"
})
public class GeoPosition {

    @JsonProperty("Latitude")
    private Float latitude;
    @JsonProperty("Longitude")
    private Float longitude;
    @JsonProperty("Elevation")
    private Elevation elevation;

    @JsonProperty("Latitude")
    public Float getLatitude() {
        return latitude;
    }

    @JsonProperty("Latitude")
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("Longitude")
    public Float getLongitude() {
        return longitude;
    }

    @JsonProperty("Longitude")
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("Elevation")
    public Elevation getElevation() {
        return elevation;
    }

    @JsonProperty("Elevation")
    public void setElevation(Elevation elevation) {
        this.elevation = elevation;
    }

}
