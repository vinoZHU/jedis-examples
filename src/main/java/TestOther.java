import redis.clients.jedis.Jedis;
import util.JedisUtil;

/**
 * Created by vino on 16/8/21.
 */
public class TestOther {
    public static void main(String[] args){
        Jedis jedis = JedisUtil.getPool().getResource();

        try {
            // keys中传入的可以用通配符
            System.out.println(jedis.keys("*")); // 返回当前库中所有的key [sose, sanme,
            // name, jarorwar, foo,
            // sname, java framework,
            // user, braand]
            System.out.println(jedis.keys("*name"));// 返回的sname [sname, name]
            System.out.println(jedis.del("sanmdde"));// 删除key为sanmdde的对象 删除成功返回1
            // 删除失败（或者不存在）返回 0
            System.out.println(jedis.ttl("sname"));// 返回给定key的有效时间，如果是-1则表示永远有效
            jedis.setex("timekey", 10, "min");// 通过此方法，可以指定key的存活（有效时间） 时间为秒
            Thread.sleep(5000);// 睡眠5秒后，剩余时间将为<=5
            System.out.println(jedis.ttl("timekey")); // 输出结果为5
            jedis.setex("timekey", 1, "min"); // 设为1后，下面再看剩余时间就是1了
            System.out.println(jedis.ttl("timekey")); // 输出结果为1
            System.out.println(jedis.exists("key"));// 检查key是否存在
            System.out.println(jedis.rename("timekey", "time"));
            System.out.println(jedis.get("timekey"));// 因为移除，返回为null
            System.out.println(jedis.get("time")); // 因为将timekey 重命名为time
            // 所以可以取得值 min
            // jedis 排序
            // 注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）
            jedis.del("a");// 先清除数据，再加入数据进行测试
            jedis.rpush("a", "1");
            jedis.lpush("a", "6");
            jedis.lpush("a", "3");
            jedis.lpush("a", "9");
            System.out.println(jedis.lrange("a", 0, -1));// [9, 3, 6, 1]
            System.out.println(jedis.sort("a")); // [1, 3, 6, 9] //输入排序后结果
            System.out.println(jedis.lrange("a", 0, -1));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisUtil.getPool().returnResource(jedis);
        }
    }
}
