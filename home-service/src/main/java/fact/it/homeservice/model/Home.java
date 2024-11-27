package fact.it.homeservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "home")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Home {
    private String id;
    private String name;
    private String location;
}
