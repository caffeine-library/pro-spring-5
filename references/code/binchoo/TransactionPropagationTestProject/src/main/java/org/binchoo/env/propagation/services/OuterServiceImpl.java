package org.binchoo.env.propagation.services;

import org.binchoo.env.propagation.entities.SimpleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.binchoo.env.propagation.repos.SimpleDataRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OuterServiceImpl implements OuterService {

    @Autowired
    private SimpleDataRepository repository;

    private InnerService innerService = null;

    @Override
    public void setInnerService(InnerService innerService) {
        this.innerService = innerService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateColumn(Long id, boolean outerException, ExceptionLocation innerExceptionLocation) {
        innerService.updateColumn(id, innerExceptionLocation);

        updateOuterColumn(id);
        if (outerException) {
            throw new RuntimeException();
        }
    }

    private void updateOuterColumn(Long id) {
        SimpleData data = repository.findById(id).orElseGet(()->{ throw new IllegalArgumentException(); });

        data.setOuterCommit(true);

        repository.save(data);
    }
}
