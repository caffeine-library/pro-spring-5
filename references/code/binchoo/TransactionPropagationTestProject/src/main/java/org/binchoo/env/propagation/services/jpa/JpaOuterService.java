package org.binchoo.env.propagation.services.jpa;

import org.binchoo.env.propagation.entities.SimpleData;
import org.binchoo.env.propagation.services.AbstractOuterService;
import org.binchoo.env.propagation.services.ExceptionLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.binchoo.env.propagation.repos.SimpleDataRepository;
import org.springframework.transaction.annotation.Transactional;

@Service("jpaOuterService")
public class JpaOuterService extends AbstractOuterService {

    @Autowired
    private SimpleDataRepository repository;

    @Transactional
    @Override
    public void updateColumn(Long id, boolean outerException, ExceptionLocation innerExceptionLocation) {
        super.updateColumn(id, outerException, innerExceptionLocation);
    }

    @Transactional
    @Override
    protected void updateOuterColumn(Long id) {
        SimpleData data = repository.findById(id).orElseThrow(()-> new IllegalArgumentException());

        data.setOuterCommit(true);

        repository.save(data);
    }
}
