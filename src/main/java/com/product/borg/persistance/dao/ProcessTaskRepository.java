package com.product.borg.persistance.dao;

import com.product.borg.model.ProcessTaskFilter;
import com.product.borg.persistance.dao.custom.ProcessTaskRepositoryCustom;
import com.product.borg.persistance.entity.ProcessTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ProcessTaskRepository extends JpaRepository<ProcessTask, Integer>, ProcessTaskRepositoryCustom {

    ProcessTask findByIdAndDeleted(int id, byte deleted);

    ProcessTask findByProcessIdAndOrderAndDeleted(Integer processId, Integer order, byte deleted);

    List<ProcessTask> findByProcessIdAndDeleted(Integer processId, byte deleted);

     List<ProcessTask> findByFilter(ProcessTaskFilter processTaskFilter);

    List<ProcessTask> findAll();
}
