package com.evo.infrastructure.persistent.dao;

import com.evo.infrastructure.persistent.po.StrategyRule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description 策略规则 DAO
 */
@Mapper
public interface IStrategyRuleDao {

    List<StrategyRule> queryStrategyRuleList();

    StrategyRule queryStrategyRule(StrategyRule strategyRuleReq);

    String queryStrategyRuleValue(StrategyRule strategyRule);
}
