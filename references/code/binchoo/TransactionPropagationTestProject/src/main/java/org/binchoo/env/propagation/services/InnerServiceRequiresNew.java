package org.binchoo.env.propagation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("innerServiceRequiresNew")
public class InnerServiceRequiresNew implements InnerService {

    @Autowired
    private InnerServiceImpl impl;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void updateColumn(Long id, ExceptionLocation eLocation) {
        impl.updateColumn(id, eLocation);
    }
}
