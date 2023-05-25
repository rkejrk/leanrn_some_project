# React

[Next.jsのアーキテクチャ](https://dev.classmethod.jp/articles/complete-nextjs-tutorial/#toc-11)

気になった記述を抜粋
>  - JSは命令型・Reactは宣言型
>  - Next.jsを利用すると以下の機能が自動化される
>    - ReactDOM.render
>    - BabelでJSX標準対応(HTML不要)
>  - 選べるレンダリング方式(CSR・SSR・SSG)


## ReactのWebアプリフレームワーク

 - [Next.jsとの比較](https://zenn.dev/taizo_pro/articles/9209eaabd1c359)
 - [GoogleトレンドでもNextjsは人気](https://trends.google.co.jp/trends/explore?date=now%207-d&geo=JP&q=React%20Remix,Nextjs&hl=ja)
 - [Remixチュートリアル](https://remix.run/docs/en/main/tutorials/jokes)
 - [セールスポイントが分かる公式サイト](https://remix.run/)

## スマホアプリの実装はReactNativeCLIがよさそう

 - [Expoはネイティブ機能が不完全](https://ncdc.co.jp/columns/6959/)
 - [NativeCLIのチュートリアル](https://reactnative.dev/docs/environment-setup?guide=native&os=windows)

    [Expo](https://reactnative.dev/docs/environment-setup?guidHTMLHTMLickstart&os=windows)のほうがスタートアップが楽）


## セットアップの事前準備
> Nodejsのバージョンが古いと[NOT FOUND MODULE]エラーになる

ReactのサイトにはNodeJSのバージョンが指定されていないため、(どこかに記載されていたのかもしれない)

Nextjs など使用するフレームワークに合わせて環境構築が必要(今回はNodejs環境を最新化することで解決)
