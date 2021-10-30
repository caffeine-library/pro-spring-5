package org.binchoo.env.propagation.test;

import org.binchoo.env.propagation.entities.SimpleData;
import org.binchoo.env.propagation.services.ExceptionLocation;
import org.binchoo.env.propagation.services.InnerService;
import org.binchoo.env.propagation.services.OuterService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcNestedPropagationTests {

    @Autowired
    ApplicationContext ctx;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("jdbcOuterService")
    OuterService outerService;

    @After
    public void cleanDB() {
        System.out.println(loadSimpleData());
        jdbcTemplate.update("Update SimpleData set innerCommit = false, outerCommit = false where id = 1L ");
    }

    private SimpleData loadSimpleData() {
        SimpleData simpleData = jdbcTemplate.queryForObject("Select * from SimpleData where id = 1L", (rs, rowNum)-> {
            SimpleData data = new SimpleData();
            data.setId(rs.getLong("id"));
            data.setInnerCommit(rs.getBoolean("innerCommit"));
            data.setOuterCommit(rs.getBoolean("outerCommit"));
            return data;
        });

        return simpleData;
    }

    private void assertOuterCommit(SimpleData simpleData) {
        assertThat(simpleData.getOuterCommit()).isTrue();
    }

    private void assertInnerCommit(SimpleData simpleData) {
        assertThat(simpleData.getInnerCommit()).isTrue();
    }

    private void assertOuterRollback(SimpleData simpleData) {
        assertThatThrownBy(()->assertOuterCommit(simpleData));
    }

    private void assertInnerRollback(SimpleData simpleData) {
        assertThatThrownBy(()->assertInnerCommit(simpleData));
    }

    @Test
    public void NESTED_예외_없으면_모두_갱신된다() {
        InnerService innerService = ctx.getBean("innerServiceNested", InnerService.class);
        outerService.setInnerService(innerService);

        try {
            outerService.updateColumn(1L, false, ExceptionLocation.NONE);
        } catch (RuntimeException e) {

        }

        SimpleData simpleData = loadSimpleData();
        assertOuterCommit(simpleData);
        assertInnerCommit(simpleData);
    }

    @Test
    public void NESTED_나는_부모의_장애에_롤백된다() {
        InnerService innerService = ctx.getBean("innerServiceNested", InnerService.class);
        outerService.setInnerService(innerService);

        try {
            outerService.updateColumn(1L, true, ExceptionLocation.NONE);
        } catch (RuntimeException e) {

        }

        SimpleData simpleData = loadSimpleData();
        assertOuterRollback(simpleData);
        assertInnerRollback(simpleData);
    }

    @Test
    public void NESTED_나는_부모를_롤백시키지_않는다() {
        InnerService innerService = ctx.getBean("innerServiceNested", InnerService.class);
        outerService.setInnerService(innerService);

        try {
            outerService.updateColumn(1L, false, ExceptionLocation.AFTER_UPDATE);
        } catch (RuntimeException e) {

        }

        SimpleData simpleData = loadSimpleData();
        assertOuterCommit(simpleData);
        assertInnerRollback(simpleData);
    }
}
