package com.product.borg.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessTaskFilter {

    private List<Integer> processIds;
    private List<Integer> taskIds;
}
