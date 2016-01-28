package org.demis.comics.data;

import java.util.List;
import java.util.stream.Collectors;

public class SortConverter {

    private SortConverter() {
    }

    public static org.springframework.data.domain.Sort convert(List<Sort> sorts) {
        List<org.springframework.data.domain.Sort.Order> dataSorts = sorts.stream().map(sort -> new org.springframework.data.domain.Sort.Order(sort.isAscending() ? org.springframework.data.domain.Sort.Direction.ASC : org.springframework.data.domain.Sort.Direction.DESC, sort.getProperty())).collect(Collectors.toList());
        if (dataSorts.isEmpty()) {
            return null;
        }
        else {
            return new org.springframework.data.domain.Sort(dataSorts);
        }
    }
}
