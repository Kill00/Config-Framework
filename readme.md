# Config Framework

![Build](https://github.com/TeamVery/Config-Framework/workflows/Java%20CI%20with%20Gradle/badge.svg) [![](https://jitpack.io/v/TeamVery/Config-Framework.svg)](https://jitpack.io/#TeamVery/Config-Framework)
---
## 콘피그 관리 프레임워크
> * 예제 플러그인 : [깃허브](https://github.com/Kill00/config-framework-sample)

---
> * ## 기능
>   * 콘피그 파일을 플러그인에서 가져오기
>   * 콘피그 파일 비교 (String, Boolean 등...)
>   * ~~콘피그 리로드~~ [더 이상 사용하지 않음]
>   * 콘피그 실시간 수정
>   * 콘피그 실시간 저장
---
> * ## 적용법

> * #### Gradle
```groovy
allprojects {
    repositories {
        ...
        maven {
            name = 'jitpack'
            url = 'https://jitpack.io'
        }
    }
}
```
```groovy
dependencies {
    implementation 'com.github.TeamVery:Config-Framework:Tag'
}
```
---
> * #### Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
```xml
<dependency>
    <groupId>com.github.TeamVery</groupId>
    <artifactId>Config-Framework</artifactId>
    <version>Tag</version>
</dependency>
```
---
> ### 콘피그 파일 생성 예제
> #### **※주의※ 해당 예제를 이용하기전 `resources` 폴더에 `config.yml`등의 파일을 생성해주세요!**

```java
public final class ConfigFrameworkSample extends JavaPlugin {
    
    public static String plugin_name = "Example-Plugin";
    public static String conf = "config.yml";
    
    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        cfg.makeData(plugin_name, conf);
    }
}
```
---
> ### Boolean 사용 예제

config.yml 파일은 아래와 같이 작성되어있습니다.
```yaml
활성화: true
```
```java
public final class ConfigFrameworkSample extends JavaPlugin {

    public static String plugin_name = "Example-Plugin";
    public static String conf = "config.yml";
    
    @Override
    public void onEnable() {

        if (cfg.get(plugin_name, conf).getBoolean("활성화")) { // True
            getLogger().info("Config Framework Sample Plugin WORK!");
        }
    }
}
```
---
> ### 콘피그 실시간 수정기능 사용 예제
config.yml 파일은 아래와 같이 작성되어있습니다.
```yaml
활성화: true
```
```java
public final class ConfigFrameworkSample extends JavaPlugin {

    public static String plugin_name = "Example-Plugin";
    public static String conf = "config.yml";

    @Override
    public void onEnable() {

        cfg.get(plugin_name, conf).set("활성화", false);
    }
}
```
※주의※ 해당 기능만 사용하면 서버가 종료되었을 때 콘피그가 이전으로 복구됩니다. 반드시 아래 `실시간 저장` 예제를 같이 이용해주세요.

---
> ### 콘피그 실시간 저장기능 사용 예제
config.yml 파일은 아래와 같이 작성되어있습니다.
```yaml
# 주석 1
활성화: true
# 주석 2
```
```java
public final class ConfigFrameworkSample extends JavaPlugin {

    public static String plugin_name = "Example-Plugin";
    public static String conf = "config.yml";

    @Override
    public void onEnable() {

        cfg.get(plugin_name, conf).set("활성화", false);
        cfg.save(plugin_name, conf, true);
    }
}
```
해당 예제 입력후 config.yml 내용
```yaml
# 주석 1
활성화: false
# 주석 2
```