package pojoModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    private String name;
    private String model;
    private String manufacturer;
    @JsonProperty("cost_in_credits")
    private String costInCredits;
    private String length;
    @JsonProperty("max_atmosphering_speed")
    private String maxAtmospheringSpeed;
    private String crew;
    private String passengers;
    @JsonProperty("cargo_capacity")
    private String cargoCapacity;
    private String consumables;
    @JsonProperty("vehicle_class")
    private String vehicleClass;
    private List<String> pilots;
    private List<String> films;
    private String created;
    private String edited;
    private String url;
}
