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
    public void updateColumn(Long id, ExceptionLocation eLocation) {
        SimpleData data = repository.findById(id).orElseGet(()->{ throw new IllegalArgumentException(); });

        data.setInnerCommit(true);

        if (ExceptionLocation.BEFORE_UPDATE == eLocation)
            eLocation.throwExcpetion();

        repository.save(data);

        if (ExceptionLocation.AFTER_UPDATE == eLocation)
            eLocation.throwExcpetion();
    }
}
