package com.evo.middleware.domain.strategy.adapter.repository;


import com.evo.middleware.domain.strategy.model.entity.StrategyAwardEntity;
import com.evo.middleware.domain.strategy.model.entity.StrategyEntity;
import com.evo.middleware.domain.strategy.model.entity.StrategyRuleEntity;
import com.evo.middleware.domain.strategy.model.valobj.StrategyAwardRuleModelVO;

import java.util.List;
import java.util.Map;

/**
 * @description 策略服务仓储接口
 */
public interface IStrategyRepository {

    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    void storeStrategyAwardSearchRateTable(String key, Integer rateRange, Map<Integer, Integer> strategyAwardSearchRateTable);

    Integer getStrategyAwardAssemble(String key, Integer rateKey);

    int getRateRange(String key);

    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);

    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleWeight);

    String queryStrategyRuleValue(Long strategyId, String ruleModel);

    String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel);

    StrategyAwardRuleModelVO queryStrategyAwardRuleModelVO(Long strategyId, Integer awardId);
}
