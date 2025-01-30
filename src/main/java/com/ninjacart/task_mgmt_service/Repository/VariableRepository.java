package com.ninjacart.task_mgmt_service.Repository;

import com.ninjacart.task_mgmt_service.entity.Variable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VariableRepository extends JpaRepository<Variable, Integer> {
    List<Variable> findByNameInAndDeleted(List<String> names, byte deleted);

    Variable findByNameAndDeleted(String name, byte deleted);

    List<Variable> findByIdInAndDeleted(List<Integer> variableIds, byte deleted);
}
