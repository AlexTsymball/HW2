package task2.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@JsonAutoDetect
@JsonPropertyOrder({"type", "sumFineAmount"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeAndSum {
    private String type;
    @JsonProperty("sum")
    private Double sumFineAmount;

}
