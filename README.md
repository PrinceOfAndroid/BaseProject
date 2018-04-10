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
    @POST(ApiUrl.LOGIN)
    Observable<HttpResponse<UserInfo>> login(@QueryMap Map<String, String> map);
```

* 获取实例进行网络请求
``` java
    Map<String, String> map = new HashMap<>();
    map.put("account", userAccount);
    map.put("password", userPwd);
    RetrofitFactory.getInstance()
        .login(map)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new HttpObserver<UserInfo>() {
            @Override
            public void onSuccess(final UserInfo s) {
                    
            }

            @Override
            public void onFailure(int code, String message) {
                super.onFailure(code, message);
               }
           });
```
