package so.xiaolu.xiaolu.jsonbean;

import java.util.List;

/**
 * Created by yann on 15-11-18.
 */
public class PosterBean {
    public String id;
    public String url;
    public List<poster> wem_posters;
    public List<poster> chd_posters;

    public class poster {
        public String item_link;
        public String pic_link;
        public List<String> subject;
    }

    String active_time;
}
