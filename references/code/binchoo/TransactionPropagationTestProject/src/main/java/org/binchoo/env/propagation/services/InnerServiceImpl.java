package org.binchoo.env.propagation.services;

import org.binchoo.env.propagation.entities.SimpleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.binchoo.env.propagation.repos.SimpleDataRepository;

@Service
public class InnerServiceImpl implements InnerService {

    @Autowired
    private SimpleDataRepository repository;

    @Override
    public void updateColumn(Long id, ExceptionLocation exLocation) {
        SimpleData data = repository.findById(id).orElseThrow(()-> new IllegalArgumentException());

        data.setInnerCommit(true);

        if (ExceptionLocation.BEFORE_UPDATE == exLocation)
            exLocation.throwException();

        repository.save(data);

        if (ExceptionLocation.AFTER_UPDATE == exLocation)
            exLocation.throwException();
    }
}
