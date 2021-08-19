# ConfigAPI

[![Java CI with Gradle](https://github.com/Kill00/ConfigAPI/actions/workflows/action.yml/badge.svg)](https://github.com/Kill00/ConfigAPI/actions/workflows/action.yml) [![](https://jitpack.io/v/kill00/ConfigAPI.svg)](https://jitpack.io/#kill00/ConfigAPI)
---
## 콘피그 관리 애플리케이션 프로그래밍 인터페이스(API)
> * JavaDocs : [링크](https://kill00.github.io/ConfigAPI/)

---
> * ## 기능
>   * 콘피그 파일을 플러그인에서 가져오기
>   * 콘피그 파일 비교 (String, Boolean 등...)
>   * ~~콘피그 리로드~~ [Deprecated]
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
    implementation 'com.github.kill00.ConfigAPI:configapi-core:<Version>'
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
    <groupId>com.github.kill00.ConfigAPI</groupId>
    <artifactId>configapi-core</artifactId>
    <version>Version</version>
</dependency>
```
---
> ### 콘피그 파일 생성 예제
> #### **※주의※ 해당 예제를 이용하기전 `resources` 폴더에 `config.yml`등의 파일을 생성해주세요!**

```java
public final class ConfigAPISample extends JavaPlugin {
    
    public static String conf = "config.yml";
    
    @Override
    public void onEnable() {
        cfg.register(this);
        cfg.makeData(conf);
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
public final class ConfigAPISample extends JavaPlugin {

    public static String conf = "config.yml";
    
    @Override
    public void onEnable() {

        cfg.register(this);
        if (cfg.get(conf).getBoolean("활성화")) { // True
            getLogger().info("ConfigAPI Sample Plugin WORK!");
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
public final class ConfigAPISample extends JavaPlugin {

    public static String conf = "config.yml";

    @Override
    public void onEnable() {

        cfg.register(this);
        cfg.get(conf).set("활성화", false);
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
public final class ConfigAPISample extends JavaPlugin {

    public static String conf = "config.yml";

    @Override
    public void onEnable() {

        cfg.register(this);
        cfg.get(conf).set("활성화", false);
        cfg.save(conf, true);
    }
}
```
해당 예제 입력후 config.yml 내용
```yaml
# 주석 1
활성화: false
# 주석 2
```
