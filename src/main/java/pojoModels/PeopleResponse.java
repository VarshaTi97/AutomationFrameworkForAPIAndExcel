package pojoModels;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PeopleResponse {
    private int count;
    private String next;
    private String previous;
    private List<People> results;
}
