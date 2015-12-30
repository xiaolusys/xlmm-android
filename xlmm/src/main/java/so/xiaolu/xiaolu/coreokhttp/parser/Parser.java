package so.xiaolu.xiaolu.coreokhttp.parser;

import com.squareup.okhttp.Response;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-10-08
 * Time: 11:08
 */
public interface Parser<T> {
    T parse(Response response);
}
