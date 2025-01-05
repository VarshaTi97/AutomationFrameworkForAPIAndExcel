package pojoModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmsResponse {
    private int count;
    private String next;
    private String previous;
    private List<Films> results;
}
