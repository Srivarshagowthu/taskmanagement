package com.ninjacart.task_mgmt_service.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class MapperUtils {

    @Autowired
    private ModelMapper modelMapper;

    public <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    public <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        if (CollectionUtils.isEmpty(entityList)) {
            return Collections.emptyList();
        }
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }

    public <S, D> D map(final S source, D destination) {
        modelMapper.map(source, destination);
        return destination;
    }

    public <S, D> D mapNonNull(final S source, D destination) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(source, destination);
        modelMapper.getConfiguration().setSkipNullEnabled(false);
        return destination;
    }

    public <D, T> List<D> mapAllStrict(final Collection<T> entityList, Class<D> outCLass) {
        if (CollectionUtils.isEmpty(entityList)) {
            return Collections.emptyList();
        }
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<D> destination = entityList.stream().map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return destination;
    }
}
