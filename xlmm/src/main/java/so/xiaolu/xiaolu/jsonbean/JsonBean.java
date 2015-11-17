package so.xiaolu.xiaolu.jsonbean;

import java.util.List;

/**
 * Created by root on 15-11-16.
 */
public class JsonBean {
    public String a;
    public List<B> b;
    public C c;

    public static class B {
        public String b1;
        public String b2;
    }

    public static class C {
        public String c1;
        public String c2;
    }
}

