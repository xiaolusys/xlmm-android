package so.xiaolu.xiaolu.okhttp.request;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.util.Map;

/**
 * Author  : itxuye(itxuye@gmail.com)|(http://itxuye.com)
 * Date    : 2015-11-30
 * Time    : 22:14
 * FIXME
 */

public class OkHttpGetRequest extends OkHttpRequest {

  protected OkHttpGetRequest(String url, String tag, Map<String, String> params,
      Map<String, String> headers) {
    super(url, tag, params, headers);
  }

  @Override protected Request buildRequest() {
    url = appendParams(url, params);
    Request.Builder builder = new Request.Builder();
    appendHeaders(builder, headers);
    builder.url(url).tag(tag);
    return builder.build();
  }

  @Override protected RequestBody buildRequestBody() {
    return null;
  }

  private String appendParams(String url, Map<String, String> params) {
    StringBuilder sb = new StringBuilder();
    sb.append(url + "?");
    if (params != null && !params.isEmpty()) {
      for (String key : params.keySet()) {
        sb.append(key).append("=").append(params.get(key)).append("&");
      }
    }

    sb = sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }
}