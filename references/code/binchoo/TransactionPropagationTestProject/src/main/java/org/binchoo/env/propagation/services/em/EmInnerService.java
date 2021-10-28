package org.binchoo.env.propagation.services.em;

import org.binchoo.env.propagation.services.ExceptionLocation;
import org.binchoo.env.propagation.services.InnerService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
public class EmInnerService implements InnerService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void updateColumn(Long id, ExceptionLocation exLocation) {
        Query updateQuery = entityManager.createQuery("update SimpleData set innerCommit = true where id = ?1").setParameter(1, id);

        if (ExceptionLocation.BEFORE_UPDATE == exLocation)
            exLocation.throwException();

        updateQuery.executeUpdate();

        if (ExceptionLocation.AFTER_UPDATE == exLocation)
            exLocation.throwException();
    }
}
