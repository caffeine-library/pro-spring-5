package org.binchoo.env.propagation.services.inner;

import org.binchoo.env.propagation.services.ExceptionLocation;
import org.binchoo.env.propagation.services.InnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JdbcInnerService implements InnerService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void updateColumn(Long id, ExceptionLocation exLocation) {

        if (ExceptionLocation.BEFORE_UPDATE == exLocation)
            exLocation.throwException();

        jdbcTemplate.update("Update SimpleData set innerCommit = true where id = ?", id);

        if (ExceptionLocation.AFTER_UPDATE == exLocation)
            exLocation.throwException();
    }
}
