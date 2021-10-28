package org.binchoo.env.propagation.services.em;

import org.binchoo.env.propagation.services.AbstractOuterService;
import org.binchoo.env.propagation.services.ExceptionLocation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service("emOuterService")
public class EmOuterService extends AbstractOuterService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void updateColumn(Long id, boolean outerException, ExceptionLocation innerExceptionLocation) {
        super.updateColumn(id, outerException, innerExceptionLocation);
    }

    @Transactional
    @Override
    protected void updateOuterColumn(Long id) {
        Query updateQuery = entityManager.createQuery("update SimpleData set outerCommit = true where id = ?1").setParameter(1, id);
        updateQuery.executeUpdate();
    }
}
