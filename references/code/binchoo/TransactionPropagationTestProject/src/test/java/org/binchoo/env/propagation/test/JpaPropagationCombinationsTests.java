package org.binchoo.env.propagation.test;

import org.binchoo.env.propagation.entities.SimpleData;
import org.binchoo.env.propagation.repos.SimpleDataRepository;
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
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaPropagationCombinationsTests {

    @Autowired
    ApplicationContext ctx;

    @Autowired
    SimpleDataRepository repository;

    @Autowired
    @Qualifier("jpaOuterService")
    OuterService outerService;

    @After
    public void cleanDB() {
        SimpleData data = repository.findById(1L).get();

        System.out.println(data);

        data.setOuterCommit(false);
        data.setInnerCommit(false);
        repository.save(data);
    }

    private void assertOuterCommit() {
        assertThat(repository.findById(1L).get().getOuterCommit()).isTrue();
    }

    private void assertInnerCommit() {
        assertThat(repository.findById(1L).get().getInnerCommit()).isTrue();
    }

    private void assertOuterRollback() {
        assertThat(repository.findById(1L).get().getOuterCommit()).isFalse();
    }

    private void assertInnerRollback() {
        assertThat(repository.findById(1L).get().getInnerCommit()).isFalse();
    }

    @Test
    public void 예외_없으면_둘_다_갱신된다() {
        InnerService innerService = ctx.getBean("innerServiceRequired", InnerService.class);
        outerService.setInnerService(innerService);

        try {
            outerService.updateColumn(1L, false, ExceptionLocation.NONE);
        } catch (RuntimeException e) {

        }
        assertOuterCommit();
        assertInnerCommit();
    }

    @Test
    public void REQUIRED_나의_장애에_모두_롤백된다() {
        InnerService innerService = ctx.getBean("innerServiceRequired", InnerService.class);
        outerService.setInnerService(innerService);

        try {
            outerService.updateColumn(1L, false, ExceptionLocation.AFTER_UPDATE);
        } catch (RuntimeException e) {

        }
        assertOuterRollback();
        assertInnerRollback();
    }

    @Test
    public void REQUIRED_부모의_장애에_모두_롤백된다() {
        InnerService innerService = ctx.getBean("innerServiceRequired", InnerService.class);
        outerService.setInnerService(innerService);

        try {
            outerService.updateColumn(1L, true, ExceptionLocation.NONE);
        } catch (RuntimeException e) {

        }
        assertOuterRollback();
        assertInnerRollback();
    }

    @Test
    public void MANDATORY_나의_장애에_모두_롤백된다() {
        InnerService innerService = ctx.getBean("innerServiceMandatory", InnerService.class);
        outerService.setInnerService(innerService);

        try {
            outerService.updateColumn(1L, false, ExceptionLocation.AFTER_UPDATE);
        } catch (RuntimeException e) {

        }
        assertOuterRollback();
        assertInnerRollback();
    }

    @Test
    public void MANDATORY_부모의_장애에_모두_롤백된다() {
        InnerService innerService = ctx.getBean("innerServiceMandatory", InnerService.class);
        outerService.setInnerService(innerService);

        try {
            outerService.updateColumn(1L, true, ExceptionLocation.NONE);
        } catch (RuntimeException e) {

        }
        assertOuterRollback();
        assertInnerRollback();
    }

    @Test
    public void SUPPORTS_나의_장애에_모두_롤백된다() {
        InnerService innerService = ctx.getBean("innerServiceSupports", InnerService.class);
        outerService.setInnerService(innerService);

        try {
            outerService.updateColumn(1L, false, ExceptionLocation.AFTER_UPDATE);
        } catch (RuntimeException e) {

        }
        assertOuterRollback();
        assertInnerRollback();
    }

    @Test
    public void SUPPORTS_부모의_장애에_모두_롤백된다() {
        InnerService innerService = ctx.getBean("innerServiceSupports", InnerService.class);
        outerService.setInnerService(innerService);

        try {
            outerService.updateColumn(1L, true, ExceptionLocation.NONE);
        } catch (RuntimeException e) {

        }
        assertOuterRollback();
        assertInnerRollback();
    }

    @Test
    public void REQUIRES_NEW_나의_장애에_부모는_롤백하지_않는다() {
        InnerService innerService = ctx.getBean("innerServiceRequiresNew", InnerService.class);
        outerService.setInnerService(innerService);

        try {
            outerService.updateColumn(1L, false, ExceptionLocation.AFTER_UPDATE);
        } catch (RuntimeException e) {

        }
        assertOuterCommit();
        assertInnerRollback();
    }

    @Test
    public void REQUIRES_NEW_부모의_장애에_롤백되지_않는다() {
        InnerService innerService = ctx.getBean("innerServiceRequiresNew", InnerService.class);
        outerService.setInnerService(innerService);

        try {
            outerService.updateColumn(1L, true, ExceptionLocation.NONE);
        } catch (RuntimeException e) {

        }
        assertOuterRollback();
        assertInnerCommit(); // !!
    }

    @Test
    public void NOT_SUPPORTED_나의_갱신_후_장애는_부모를_롤백하지_않는다() {
        InnerService innerService = ctx.getBean("innerServiceNotSupported", InnerService.class);
        outerService.setInnerService(innerService);

        try {
            outerService.updateColumn(1L, false, ExceptionLocation.AFTER_UPDATE);
        } catch (RuntimeException e) {

        }
        assertOuterCommit();
        assertInnerCommit(); // InnerCommit은 true로 갱신 후에 예외가 발생하므로
    }

    @Test
    public void NOT_SUPPORTED_나의_갱신_전_장애는_부모를_롤백하지_않는다() {
        InnerService innerService = ctx.getBean("innerServiceNotSupported", InnerService.class);
        outerService.setInnerService(innerService);

        try {
            outerService.updateColumn(1L, false, ExceptionLocation.BEFORE_UPDATE);
        } catch (RuntimeException e) {

        }
        assertOuterCommit();
        assertInnerRollback(); //갱신하기 전 오류로 갱신 로직에 도달하지 못 했음
    }

    @Test
    public void NOT_SUPPORTED_나는_부모의_장애에_롤백되지_않는다() {
        InnerService innerService = ctx.getBean("innerServiceNotSupported", InnerService.class);
        outerService.setInnerService(innerService);

        try {
            outerService.updateColumn(1L, true, ExceptionLocation.AFTER_UPDATE);
        } catch (RuntimeException e) {

        }

        assertOuterRollback();
        assertInnerCommit(); // 비트랜잭션이므로
    }
}
