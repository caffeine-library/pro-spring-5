package org.binchoo.env.propagation.services.outer;

import org.binchoo.env.propagation.services.ExceptionLocation;
import org.binchoo.env.propagation.services.InnerService;
import org.binchoo.env.propagation.services.OuterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("jdbcOuterService")
public class JdbcOuterService implements OuterService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private InnerService innerService;

    @Override
    public void setInnerService(InnerService innerService) {
        this.innerService = innerService;
    }

    @Transactional("dataSourceTransactionManager")
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
        jdbcTemplate.update("Update SimpleData set outerCommit = true where id = ?", id);
    }
}
