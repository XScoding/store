## 全局数据管理

### 介绍

开发几个H5的项目，接触React和Vue，Store有时还挺好用的，于是就有想法实现一个android可用的
，虽然已经有Android Redux了，还是想自己实现一个类似功能的库。

### 使用

具体请参考demo

```
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeObserver = new StoreObserver();
        storeObserver.onCreate();
        
        storeObserver.refresh(new Call<AStore>() {
        
            @Override
            public void handle(AStore testStore) {

                tvName.setText(testStore.getName());
                tvAge.setText(testStore.getAge() + "");
                if (testStore.getIdea() != null) {
                    tvMessage.setText(testStore.getIdea().getMessage());
                }
            }
        })
    }
    
    
    public void onClick(View view) {
        storeObserver.handAciton(AAction.class, new In("AA"), new StoreCallback<In, Out>() {
            @Override
            public void success(In in, Out o) {
               
            }

            @Override
            public void error(int code, String message, Throwable e) {

            }
        });
    }
    
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        storeObserver.onDestroy();
    }
```