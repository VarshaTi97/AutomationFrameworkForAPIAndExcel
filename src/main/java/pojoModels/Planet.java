package pojoModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Planet {
    private String name;
    @JsonProperty("rotation_period")
    private String rotationPeriod;
    @JsonProperty("orbital_period")
    private String orbitalPeriod;
    private String diameter;
    private String climate;
    private String gravity;
    private String terrain;
    @JsonProperty("surface_water")
    private String surfaceWater;
    private String population;
    private List<String> residents;
    private List<String> films;
    private String created;
    private String edited;
    private String url;
}
