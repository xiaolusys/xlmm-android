package com.jimei.xiaolumeimei.okhttp.request;


import com.jimei.xiaolumeimei.okhttp.body.CountingRequestBody;
import com.jimei.xiaolumeimei.okhttp.callback.OkHttpCallback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.util.Map;



/**
 * Author  : itxuye(itxuye@gmail.com)|(http://itxuye.com)
 * Date    : 2015-11-30
 * Time    : 22:14
 * FIXME
 */
public class OkHttpPostRequest extends OkHttpRequest {

  private static final int TYPE_PARAMS = 1;
  private static final int TYPE_STRING = 2;
  private static final int TYPE_BYTES = 3;
  private static final int TYPE_FILE = 4;
  private final MediaType MEDIA_TYPE_STREAM =
      MediaType.parse("application/octet-stream;charset=utf-8");
  private final MediaType MEDIA_TYPE_STRING = MediaType.parse("text/plain;charset=utf-8");
  private String content;
  private byte[] bytes;
  private File file;
  private int type = 0;

  protected OkHttpPostRequest(String url, String tag, Map<String, String> params,
      Map<String, String> headers, String content, byte[] bytes, File file) {
    super(url, tag, params, headers);
    this.content = content;
    this.bytes = bytes;
    this.file = file;
  }

  protected void validParams() {

    int count = 0;
    if (params != null && !params.isEmpty()) {
      type = TYPE_PARAMS;
      count++;
    }
    if (content != null) {
      type = TYPE_STRING;
      count++;
    }
    if (bytes != null) {
      type = TYPE_BYTES;
      count++;
    }
    if (file != null) {
      type = TYPE_FILE;
      count++;
    }

    if (count <= 0 || count > 1) {
      throw new IllegalArgumentException(
          "the params , content , file , bytes must has one and only one .");
    }
  }

  @Override protected Request buildRequest() {
    Request.Builder builder = new Request.Builder();
    appendHeaders(builder, headers);
    builder.url(url).tag(tag).post(requestBody);
    return builder.build();
  }

  @Override protected RequestBody buildRequestBody() {
    validParams();
    RequestBody requestBody = null;
    switch (type) {
      case TYPE_PARAMS:
        FormEncodingBuilder builder = new FormEncodingBuilder();
        addParams(builder, params);
        requestBody = builder.build();
        break;
      case TYPE_BYTES:
        requestBody = RequestBody.create(MEDIA_TYPE_STREAM, bytes);
        break;
      case TYPE_FILE:
        requestBody = RequestBody.create(MEDIA_TYPE_STREAM, file);
        break;
      case TYPE_STRING:
        requestBody = RequestBody.create(MEDIA_TYPE_STRING, content);
        break;
    }
    return requestBody;
  }

  @Override protected RequestBody wrapRequestBody(RequestBody requestBody,
      final OkHttpCallback callback) {
    CountingRequestBody countingRequestBody =
        new CountingRequestBody(requestBody, new CountingRequestBody.Listener() {
          @Override public void onRequestProgress(final long bytesWritten,
              final long contentLength) {

            mOkHttpClientManager.getDelivery().post(new Runnable() {
              @Override public void run() {
                callback.onProgress(bytesWritten * 1.0f / contentLength);
              }
            });
          }
        });
    return countingRequestBody;
  }

  private void addParams(FormEncodingBuilder builder, Map<String, String> params) {
    if (builder == null) {
      throw new IllegalArgumentException("builder can not be null .");
    }

    if (params != null && !params.isEmpty()) {
      for (String key : params.keySet()) {
        builder.add(key, params.get(key));
      }
    }
  }
}