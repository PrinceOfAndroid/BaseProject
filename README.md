# BaseProject
mvp基类以及retrofit2+rxJava2+okHttp网络请求的封装

### 网络请求使用方法
* 添加请求接口
``` java
    /**
     * 登录
     *
     * @return
     */
    @GET(ApiUrl.ARTICLE_LIST)
    Observable<HttpResponse<List<String>>> getList(@QueryMap Map<String, String> map);
```

* 获取实例进行网络请求
``` java
     Map<String, String> map = new HashMap<>();
     map.put("is_index", "true");
     map.put("rows", String.valueOf("3"));

     addSubscribe(iModel.getList(map)
                   .compose(RxUtils.<HttpResponse<List<String>>>applyFSchedulers())
                    .compose(RxUtils.<List<String>>handleResult())
                    .subscribeWith(new CommonSubscriber<List<String>>() {
                        @Override
                        public void onSuccess(List<String> strings) {

                        }
                    }));
```
