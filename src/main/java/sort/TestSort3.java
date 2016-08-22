package sort;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;
import util.JedisUtil;

import java.util.List;

/**
 * Created by vino on 16/8/22.
 */
public class TestSort3 {
    /**
     * sort set
     * SET结合String的排序
     */
    public static void main(String[] args){
        Jedis jedis = JedisUtil.getPool().getResource();
        jedis.del("tom:friend:list", "score:uid:123", "score:uid:456",
                "score:uid:789", "score:uid:101", "uid:123", "uid:456",
                "uid:789", "uid:101");

        jedis.sadd("tom:friend:list", "123"); // tom的好友列表
        jedis.sadd("tom:friend:list", "456");
        jedis.sadd("tom:friend:list", "789");
        jedis.sadd("tom:friend:list", "101");

        jedis.set("score:uid:123", "1000"); // 好友对应的成绩
        jedis.set("score:uid:456", "6000");
        jedis.set("score:uid:789", "100");
        jedis.set("score:uid:101", "5999");

        jedis.set("uid:123", "{'uid':123,'name':'lucy'}"); // 好友的详细信息
        jedis.set("uid:456", "{'uid':456,'name':'jack'}");
        jedis.set("uid:789", "{'uid':789,'name':'jay'}");
        jedis.set("uid:101", "{'uid':101,'name':'jolin'}");

        SortingParams sortingParameters = new SortingParams();

        sortingParameters.desc();
        // sortingParameters.limit(0, 2);
        // 注意GET操作是有序的，GET user_name_* GET user_password_*
        // 和 GET user_password_* GET user_name_*返回的结果位置不同
        sortingParameters.get("#");// GET 还有一个特殊的规则—— "GET #"
        // ，用于获取被排序对象(我们这里的例子是 user_id )的当前元素。
        sortingParameters.get("uid:*");
        sortingParameters.get("score:uid:*");
        sortingParameters.by("score:uid:*");
        // 对应的redis 命令是./redis-cli sort tom:friend:list by score:uid:* get # get
        // uid:* get score:uid:*
        List<String> result = jedis.sort("tom:friend:list", sortingParameters);
        for (String item : result) {
            System.out.println("item..." + item);
        }

        JedisUtil.getPool().returnResource(jedis);
    }
}
