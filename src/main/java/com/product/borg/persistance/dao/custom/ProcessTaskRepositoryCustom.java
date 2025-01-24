package com.product.borg.persistance.dao.custom;

import com.product.borg.model.ProcessTaskFilter;
import com.product.borg.persistance.entity.ProcessTask;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessTaskRepositoryCustom {

    List<ProcessTask> findByFilter(ProcessTaskFilter processTaskFilter);

//    Integer findMaxId();
}
