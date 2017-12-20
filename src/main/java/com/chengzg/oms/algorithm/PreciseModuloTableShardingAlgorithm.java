package com.chengzg.oms.algorithm;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * Created by chengzg3 on 2017/12/5.
 */
public final class PreciseModuloTableShardingAlgorithm implements PreciseShardingAlgorithm<String> {
    @Override
    public String doSharding(final Collection<String> availableTargetNames, final PreciseShardingValue<String> shardingValue) {
        for (String each : availableTargetNames) {
            if (each.endsWith(shardingValue.getValue() + "")) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }
}
