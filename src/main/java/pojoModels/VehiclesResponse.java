package pojoModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehiclesResponse {
    private int count;
    private String next;
    private String previous;
    private List<Vehicle> results;
}
