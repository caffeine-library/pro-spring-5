package org.binchoo.env.propagation.services.propagational;

import org.binchoo.env.propagation.services.ExceptionLocation;
import org.binchoo.env.propagation.services.InnerService;
import org.binchoo.env.propagation.services.inner.JpaInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.NOT_SUPPORTED, transactionManager = "transactionManager")
@Service("innerServiceNotSupported")
public class InnerServiceNotSupported implements InnerService {

    @Autowired
    private JpaInnerService impl;

    @Override
    public void updateColumn(Long id, ExceptionLocation eLocation) {
        impl.updateColumn(id, eLocation);
    }
}
