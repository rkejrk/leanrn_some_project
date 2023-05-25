# 仕様

## DBの設定
[Docker設定](https://zenn.dev/junki555/articles/de2c9844a1d101)
 - ユーザーテーブル
 - お気に入りテーブル
 - 物件テーブル

## バッチ機能
 - バッチひな形[参考](https://spring.io/guides/gs/batch-processing/)
 - 10秒ごとに固定のURLからデータを取得、件数と処理にかかった時間を出力
    [参考](https://qiita.com/takahiroSakamoto/items/c2b269c07e15a04f5861)
    https://suumo.jp/hokkaido_/sc_sapporoshichuo/pnz12.html
　

## API機能
 - DBから取得または登録をし、ユーザー情報を返却する(user/)
 - お気に入りテーブルにuserIDとpropertyIDを登録
 - お気に入りテーブルのIDをもとに論理削除
 - 物件情報とお気に入り

## Android
 - 物件リスト画面
     - お気に入りボタン
 - ユーザー画面
 - スワイプで更新
    https://developer.android.com/training/swipe/add-swipe-interface?hl=ja

# Springの仕組み

@Beanなどのアノテーションを追加すると、SpringFrameworkが自動的にsrc/配下を読み込み利用できる状態にしてくれる。
@Beanは追加可能

## 利用している主なアノテーション

 - @SpringBootApplication 
    - 使用箇所：*Application.java
    - 意味：@Configuration、@EnableAutoConfiguration、@ComponentScanと同義

 - @RestController,@Controller
    ‐ 使用箇所：*Controller.java
    - 意味：クラスをURLから実行出来るように設定してくれる
    
 - @Repository
    ‐ 使用箇所：*Repository.java
    - 意味：SQLクエリの実行などDBを操作するクラス(TODO:Joinの方法はーー学習)

 - @Entity
    ‐ 使用箇所：モデルクラス
    - 意味：DBのテーブルとの紐づけ

 - @Column
    ‐ 使用箇所：モデルクラス>class>variable
    - 意味：カラムとの紐づけ

 - @Id
    ‐ 使用箇所：モデルクラス>class>variable(id)
    - 意味：@Unique、@Not Null

 - @GeneratedValue(strategy = GenerationType.Auto)
    ‐ 使用箇所：モデルクラス>class>variable(id)
    - 意味：値の自動生成。AutoだとDBに合わせたAutoIncrementをしてくれる

 - @Autowrite
    - 使用箇所：いろいろ(当ソースでは  [PropertyController.java](./spring\src\main\java\com\example\demo\property\PropertyController.java))でBatch処理内容を実装
    - 
    - 意味：他クラスのインスタント作成処理を行う

## Jobの実装
 1. [Processorクラス](./spring\src\main\java\com\example\demo\property\PropertyItemProcessor.java)で処理内容を実装

 2. Stepを追加([BatchConfiguration.java](spring\src\main\java\com\example\demo\property\BatchConfiguration.java))
    - Readerを実装: ファイルなどからデータを取得する
    - writerを実装: DBにデータを書き込む
    - Stepを実装: reader()/processor()/writer()で処理の流れを設定しbuild()する

3. jobを追加
    - incrementerを実装: 初期化する
    - listenerを実装: 処理の前後で実行される処理の設定
    - stepの紐づけ：flow,next？などでStepを紐づけ
    ‐ 処理の終了：end().build()で処理の流れを終了する。

 - お好みでトリガーを実装 ([PropertyController.java](./spring\src\main\java\com\example\demo\property\PropertyController.java))
     >  jobLauncher.run(job, new JobParameters());
 
   



# エラーメモ

## Javaxが見つかりません

Javaが有償化前の古い状態だった。OpenJDKを追加pluginをApacheから取得

最新はjakartaらしい[参考](https://blog1.mammb.com/entry/2023/03/09/203845)


## Consider defining a bean of type

Parameter 0 of constructor in com.example.rest_api.user_app.UserController required a bean of type 'com.example.rest_api.user_app.UserRepository' that could not be found.

Consider defining a bean of type 'com.example.rest_api.user_app.UserRepository' in your configuration.

 - 命名規則に沿っていない(Repository.findById()などのメソッドもfindBy(UpperCaseのプロパティ名っぽい))
 	[命名規則](https://b1san-blog.com/post/spring/spring-jpa/#%E3%82%AB%E3%82%B9%E3%82%BF%E3%83%A0%E3%82%AF%E3%82%A8%E3%83%AA)

## .jarにアクセス出来ません
ビルドがうまくいってないため、ファイルが生成されてない



# 参考サイト

[アーキテクチャ](https://www.aruse.net/entry/2019/09/01/181420)

[リアクティブプログラミング](https://qiita.com/kilvis/items/fb18be963da6cac03ee9) | スレッドをタスクごとに決めるのではなく、スレッドが空いたらタスクを挿入出来るしくみ
