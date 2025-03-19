package com.evo.test.infrastructure;

import com.alibaba.fastjson2.JSON;
import com.evo.infrastructure.persistent.dao.IAwardDao;
import com.evo.infrastructure.persistent.po.Award;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


/**
 * @description 奖品持久化单元测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AwardDaoTest {

    @Autowired
    private IAwardDao awardDao;

    @Test
    public void test_queryAwardList() {
        List<Award> awards = awardDao.queryAwardList();
        log.info("测试结果：{}", JSON.toJSONString(awards));
    }

}
