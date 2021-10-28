package org.binchoo.env.propagation.services.propagational;

import org.binchoo.env.propagation.services.ExceptionLocation;
import org.binchoo.env.propagation.services.InnerService;
import org.binchoo.env.propagation.services.em.EmInnerService;
import org.binchoo.env.propagation.services.jpa.JpaInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Transactional(propagation = Propagation.NESTED)
@Service("innerServiceNested")
public class InnerServiceNested implements InnerService {

    @Autowired
    private EmInnerService innerService;

    @Override
    public void updateColumn(Long id, ExceptionLocation exLocation) {
        innerService.updateColumn(id, exLocation);
    }
}
