package org.binchoo.env.propagation.services;

import org.binchoo.env.propagation.entities.SimpleData;
import org.binchoo.env.propagation.repos.SimpleDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractOuterService implements OuterService {

    private InnerService innerService = null;

    @Override
    public void setInnerService(InnerService innerService) {
        this.innerService = innerService;
    }

    @Override
    public void updateColumn(Long id, boolean outerException, ExceptionLocation innerExceptionLocation) {
        try {
            innerService.updateColumn(id, innerExceptionLocation);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        updateOuterColumn(id);
        if (outerException) {
            throw new RuntimeException();
        }
    }

    protected abstract void updateOuterColumn(Long id);
}
