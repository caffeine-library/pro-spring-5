package org.binchoo.env.propagation.services.outer;

import org.binchoo.env.propagation.entities.SimpleData;
import org.binchoo.env.propagation.services.ExceptionLocation;
import org.binchoo.env.propagation.services.InnerService;
import org.binchoo.env.propagation.services.OuterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.binchoo.env.propagation.repos.SimpleDataRepository;
import org.springframework.transaction.annotation.Transactional;

@Service("jpaOuterService")
public class JpaOuterService implements OuterService {

    @Autowired
    private SimpleDataRepository repository;

    private InnerService innerService;

    @Override
    public void setInnerService(InnerService innerService) {
        this.innerService = innerService;
    }

    @Transactional("transactionManager")
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

    private void updateOuterColumn(Long id) {
        SimpleData data = repository.findById(id).orElseThrow(()-> new IllegalArgumentException());

        data.setOuterCommit(true);

        repository.save(data);
    }
}
