package com.evo.test.domian;

import com.evo.middleware.domain.strategy.service.armory.IStrategyArmory;
import com.evo.middleware.domain.strategy.service.rule.chain.ILogicChain;
import com.evo.middleware.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.evo.middleware.domain.strategy.service.rule.chain.impl.RuleWeightLogicChain;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * 抽奖责任链测试，验证不同的规则走不同的责任链
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class LogicChainTest {

    @Autowired
    private IStrategyArmory strategyArmory;
    @Autowired
    private RuleWeightLogicChain ruleWeightLogicChain;
    @Autowired
    private DefaultChainFactory defaultChainFactory;

    @Before
    public void setUp() {
        // 策略装配 100001、100002、100003
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100001L));
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100002L));
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100003L));
    }

    @Test
    public void test_LogicChain_rule_blacklist() {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(100003L);
        Integer awardId = logicChain.logic("user001", 100003L);
        log.info("测试结果：{}", awardId);
    }

    @Test
    public void test_LogicChain_rule_weight() {
        // 通过反射 mock 规则中的值
        ReflectionTestUtils.setField(ruleWeightLogicChain, "userScore", 4900L);

        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        Integer awardId = logicChain.logic("xiaofuge", 100001L);
        log.info("测试结果：{}", awardId);
    }

    @Test
    public void test_LogicChain_rule_default() {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        Integer awardId = logicChain.logic("xiaofuge", 100001L);
        log.info("测试结果：{}", awardId);
    }

}
