import redis.clients.jedis.Jedis;
import util.JedisUtil;

/**
 * Created by vino on 16/8/20.
 */
public class JedisPoolExample {
    public static void main(String[] args){
        Jedis jedis = JedisUtil.getPool().getResource();
        jedis.set("jedispool","this is jedis pool");
        System.out.println(jedis.get("jedispool"));
    }
}
