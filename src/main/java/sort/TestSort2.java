package sort;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;
import util.JedisUtil;

import java.util.List;

/**
 * Created by vino on 16/8/22.
 */
public class TestSort2 {
    /**
     * sort list
     * LIST结合hash的排序
     */
    public static void main(String[] args){
        Jedis jedis = JedisUtil.getPool().getResource();
        jedis.del("user:66", "user:55", "user:33", "user:22", "user:11",
                "userlist");
        jedis.lpush("userlist", "33");
        jedis.lpush("userlist", "22");
        jedis.lpush("userlist", "55");
        jedis.lpush("userlist", "11");

        jedis.hset("user:66", "name", "66");
        jedis.hset("user:55", "name", "55");
        jedis.hset("user:33", "name", "33");
        jedis.hset("user:22", "name", "79");
        jedis.hset("user:11", "name", "24");
        jedis.hset("user:11", "add", "beijing");
        jedis.hset("user:22", "add", "shanghai");
        jedis.hset("user:33", "add", "guangzhou");
        jedis.hset("user:55", "add", "chongqing");
        jedis.hset("user:66", "add", "xi'an");

        SortingParams sortingParameters = new SortingParams();
        // 符号 "->" 用于分割哈希表的键名(key name)和索引域(hash field)，格式为 "key->field" 。
        sortingParameters.get("user:*->name");
        sortingParameters.get("user:*->add");
//      sortingParameters.by("user:*->name");
//      sortingParameters.get("#");
        List<String> result = jedis.sort("userlist", sortingParameters);
        for (String item : result) {
            System.out.println("item...." + item);
        }
        /**
         * 对应的redis客户端命令是：sort ml get user*->name sort ml get user:*->name get
         * user:*->add
         */
        JedisUtil.getPool().returnResource(jedis);
    }
}
