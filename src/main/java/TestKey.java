import redis.clients.jedis.Jedis;
import util.JedisUtil;

/**
 * Created by vino on 16/8/21.
 */
public class TestKey {
    public static void main(String[] args){
        Jedis jedis = JedisUtil.getPool().getResource();
        System.out.println("=============key==========================");
        // 清空数据
        System.out.println(jedis.flushDB());
        System.out.println(jedis.echo("foo"));
        // 判断key否存在
        System.out.println(jedis.exists("foo"));
        jedis.set("key", "values");
        System.out.println(jedis.exists("key"));
    }
}
