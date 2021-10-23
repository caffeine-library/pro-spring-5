package org.binchoo.env.propagation.services;

public interface OuterService {
    void setInnerService(InnerService innerService);
    void updateColumn(Long id, boolean outerException, ExceptionLocation innerExceptionLocation);
}
