package com.evo.infrastructure.persistent.redis;

import com.evo.middleware.redis.utils.RedisOperatorClient;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RBucket;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RPermitExpirableSemaphore;
import org.redisson.api.RQueue;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RSet;
import org.redisson.api.RSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * Redis 服务 - Redisson
 */
@Service("redissonService")
public class RedissonService implements IRedisService {

    @Autowired
    private RedisOperatorClient redisOperatorClient;

    public <T> void setValue(String key, T value) {
        redisOperatorClient.redissonClient().<T>getBucket(key).set(value);
    }

    @Override
    public <T> void setValue(String key, T value, long expired) {
        RBucket<T> bucket = redisOperatorClient.redissonClient().getBucket(key);
        bucket.set(value, Duration.ofMillis(expired));
    }

    public <T> T getValue(String key) {
        return redisOperatorClient.redissonClient().<T>getBucket(key).get();
    }

    @Override
    public <T> RQueue<T> getQueue(String key) {
        return redisOperatorClient.redissonClient().getQueue(key);
    }

    @Override
    public <T> RBlockingQueue<T> getBlockingQueue(String key) {
        return redisOperatorClient.redissonClient().getBlockingQueue(key);
    }

    @Override
    public <T> RDelayedQueue<T> getDelayedQueue(RBlockingQueue<T> rBlockingQueue) {
        return redisOperatorClient.redissonClient().getDelayedQueue(rBlockingQueue);
    }

    @Override
    public long incr(String key) {
        return redisOperatorClient.redissonClient().getAtomicLong(key).incrementAndGet();
    }

    @Override
    public long incrBy(String key, long delta) {
        return redisOperatorClient.redissonClient().getAtomicLong(key).addAndGet(delta);
    }

    @Override
    public long decr(String key) {
        return redisOperatorClient.redissonClient().getAtomicLong(key).decrementAndGet();
    }

    @Override
    public long decrBy(String key, long delta) {
        return redisOperatorClient.redissonClient().getAtomicLong(key).addAndGet(-delta);
    }

    @Override
    public void remove(String key) {
        redisOperatorClient.redissonClient().getBucket(key).delete();
    }

    @Override
    public boolean isExists(String key) {
        return redisOperatorClient.redissonClient().getBucket(key).isExists();
    }

    public void addToSet(String key, String value) {
        RSet<String> set = redisOperatorClient.redissonClient().getSet(key);
        set.add(value);
    }

    public boolean isSetMember(String key, String value) {
        RSet<String> set = redisOperatorClient.redissonClient().getSet(key);
        return set.contains(value);
    }

    public void addToList(String key, String value) {
        RList<String> list = redisOperatorClient.redissonClient().getList(key);
        list.add(value);
    }

    public String getFromList(String key, int index) {
        RList<String> list = redisOperatorClient.redissonClient().getList(key);
        return list.get(index);
    }

    @Override
    public <K, V> RMap<K, V> getMap(String key) {
        return redisOperatorClient.redissonClient().getMap(key);
    }

    public void addToMap(String key, String field, String value) {
        RMap<String, String> map = redisOperatorClient.redissonClient().getMap(key);
        map.put(field, value);
    }

    public String getFromMap(String key, String field) {
        RMap<String, String> map = redisOperatorClient.redissonClient().getMap(key);
        return map.get(field);
    }

    @Override
    public <K, V> V getFromMap(String key, K field) {
        return redisOperatorClient.redissonClient().<K, V>getMap(key).get(field);
    }

    public void addToSortedSet(String key, String value) {
        RSortedSet<String> sortedSet = redisOperatorClient.redissonClient().getSortedSet(key);
        sortedSet.add(value);
    }

    @Override
    public RLock getLock(String key) {
        return redisOperatorClient.redissonClient().getLock(key);
    }

    @Override
    public RLock getFairLock(String key) {
        return redisOperatorClient.redissonClient().getFairLock(key);
    }

    @Override
    public RReadWriteLock getReadWriteLock(String key) {
        return redisOperatorClient.redissonClient().getReadWriteLock(key);
    }

    @Override
    public RSemaphore getSemaphore(String key) {
        return redisOperatorClient.redissonClient().getSemaphore(key);
    }

    @Override
    public RPermitExpirableSemaphore getPermitExpirableSemaphore(String key) {
        return redisOperatorClient.redissonClient().getPermitExpirableSemaphore(key);
    }

    @Override
    public RCountDownLatch getCountDownLatch(String key) {
        return redisOperatorClient.redissonClient().getCountDownLatch(key);
    }

    @Override
    public <T> RBloomFilter<T> getBloomFilter(String key) {
        return redisOperatorClient.redissonClient().getBloomFilter(key);
    }


}
