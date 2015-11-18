package so.xiaolu.xiaolu.asynctask;

/**
 * Created by yann on 15-11-17.
 */
//模拟网络环境
public class NetOperator {

    public void operator() {
        try {
            //休眠1秒
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}