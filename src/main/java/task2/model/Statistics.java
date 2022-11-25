package task2.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@JsonAutoDetect
@JacksonXmlRootElement(localName = "statistics")
public class Statistics {
    @JacksonXmlElementWrapper( useWrapping = false)
    @JacksonXmlProperty(localName = "fine")
    private List<TypeAndSum> fines = new ArrayList<>();


    public void addFines(TypeAndSum fine) {
        fines.add(fine);
    }

    public void groupAndSort() {
        fines = fines.stream()
                .collect(Collectors.groupingBy(TypeAndSum::getType, Collectors.summingDouble(TypeAndSum::getSum_fine_amount)))
                .entrySet()
                .stream()
                .map(a -> {
                            if (a.getValue() != Double.POSITIVE_INFINITY) {
                                return new TypeAndSum(a.getKey(), a.getValue());
                            } else {
                                throw new IllegalArgumentException("too big value for calculate sum");
                            }
                        }
                )
                .collect(Collectors.toList());
        fines.sort(Comparator.comparing(TypeAndSum::getSum_fine_amount).reversed());

    }
}
