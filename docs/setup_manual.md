# オークションWEBアプリケーション セットアップマニュアル
## インストールから実行まで 
- 以下のコマンドを入力し、 アプリケーションをインストールする。  
```git clone https://github.com/ryota6869/auction.git```
- アプリケーションで利用するポート番号は80番である。Linuxでは1024番ポート以下は管理者権限を持つアプリケーションのみ実行が可能である。そのため、`java`にコマンドに以下のコマンドで、1024番ポート以下のポートが利用できる設定を加える必要がある。  
```sudo setcap CAP_NET_BIND_SERVICE+ep /usr/lib/jvm/java-11-amazon-corretto/bin/java ```
- インストールしたリポジトリに移動する。  
```cd auction```
- `gradle`を`bash`を利用して実行するために以下のコマンドを実行する  
```bash ./gradlew ```
- アプリケーションを実行する  
```bash ./gradlew bootrun ```
- アプリケーションの終了は`Ctrl + C`で行う
