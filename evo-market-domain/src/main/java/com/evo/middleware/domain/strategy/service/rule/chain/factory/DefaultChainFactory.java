package com.evo.middleware.domain.strategy.service.rule.chain.factory;

import com.evo.middleware.domain.strategy.adapter.repository.IStrategyRepository;
import com.evo.middleware.domain.strategy.model.entity.StrategyEntity;
import com.evo.middleware.domain.strategy.service.rule.chain.ILogicChain;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 工厂
 */
@Service
public class DefaultChainFactory implements ApplicationContextAware {

    private final Map<String, ILogicChain> logicChainGroup;
    protected IStrategyRepository repository;
    private ApplicationContext applicationContext;

    public DefaultChainFactory(Map<String, ILogicChain> logicChainGroup, IStrategyRepository repository) {
        this.logicChainGroup = logicChainGroup;
        this.repository = repository;
    }

    /**
     * 通过策略ID，构建责任链
     *
     * @param strategyId 策略ID
     * @return LogicChain
     */
    public ILogicChain openLogicChain(Long strategyId) {
        StrategyEntity strategy = repository.queryStrategyEntityByStrategyId(strategyId);
        String[] ruleModels = strategy.ruleModels();

        // 如果未配置策略规则，则只装填一个默认责任链
        if (null == ruleModels || 0 == ruleModels.length) return logicChainGroup.get("default");

        // 按照配置顺序装填用户配置的责任链；rule_blacklist、rule_weight 「注意此数据从Redis缓存中获取，如果更新库表，记得在测试阶段手动处理缓存」
        ILogicChain logicChain = applicationContext.getBean(ruleModels[0], ILogicChain.class);
        ILogicChain current = logicChain;
        for (int i = 1; i < ruleModels.length; i++) {
            ILogicChain nextChain = applicationContext.getBean(ruleModels[i], ILogicChain.class);
            current = current.appendNext(nextChain);
        }

        // 责任链的最后装填默认责任链
        current.appendNext(applicationContext.getBean("RULE_DEFAULT", ILogicChain.class));

        return logicChain;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
