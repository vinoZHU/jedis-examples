package sort;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;
import util.JedisUtil;

import java.util.List;

/**
 * Created by vino on 16/8/22.
 */
public class TestSort1 {
    /**
     * 时间复杂度：
     O(N+M*log(M))， N 为要排序的列表或集合内的元素数量， M 为要返回的元素数量。
     如果只是使用 SORT 命令的 GET 选项获取数据而没有进行排序，时间复杂度 O(N)。
     */
    public static void main(String[] args){
        // 排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较
        Jedis redis = JedisUtil.getPool().getResource();
        // 一般SORT用法 最简单的SORT使用方法是SORT key。
        redis.lpush("mylist", "1");
        redis.lpush("mylist", "4");
        redis.lpush("mylist", "6");
        redis.lpush("mylist", "3");
        redis.lpush("mylist", "0");
        // List<String> list = redis.sort("sort");// 默认是升序
        SortingParams sortingParameters = new SortingParams();
        sortingParameters.desc();
        // sortingParameters.alpha();//当数据集中保存的是字符串值时，你可以用 ALPHA
        // 修饰符(modifier)进行排序。
        sortingParameters.limit(0, 2);// 可用于分页查询
        List<String> list = redis.sort("mylist", sortingParameters);// 默认是升序
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        redis.flushDB();
        JedisUtil.getPool().returnResource(redis);
    }
}
