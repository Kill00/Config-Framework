package io.github.kill00.configapi;

import com.tchristofferson.configupdater.ConfigUpdater;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.Collections;
import java.util.Objects;

/**
 * ConfigAPI
 *
 * @author Kill00
 * @version Docs: 1.0.1
 *
 */

public class cfg {

    private static File file;
    private static Plugin plugin;
    public static FileConfiguration config;

    /**
     * fatJar 형태로 사용할 경우 해당 함수를 사용하여 플러그인을 활성화해 주세요
     *
     * @param plugin onEnable / onLoad 에서 'this' 를 입력하세요
     */
    public static void register(Plugin plugin) {
        cfg.plugin = plugin;
    }
    
    /**
     * 컨피그 파일을 플러그인 폴더에 생성합니다
     *
     * @param yml 컨피그 파일 이름(*.yml)
     */
    public static void makeData(String yml) {
        try {
            file = new File(plugin.getDataFolder(), yml);
            if (!file.exists()) {
                plugin.saveResource(yml, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * 컨피그 파일 이름을 변경합니다
     *
     * @param OriginalName 소스 폴더내 컨피그 파일 이름(*.yml)
     * @param NewName 변경할 컨피그 파일 이름(*.yml)
     */
    public static void renameTo(String OriginalName, String NewName) {
        try {
            File fileOld = new File(plugin.getDataFolder(), OriginalName);
            File fileNew = new File(plugin.getDataFolder(), NewName);

            if (fileNew.exists()) {
                fileOld.delete();
            } else {
                fileOld.renameTo(fileNew);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 컨피그 파일을 플러그인 폴더에 생성합니다
     *
     * @param yml 컨피그 파일 이름(*.yml)
     *
     * @deprecated {@link #makeData(String)}로 이용해 주세요
     */
    @Deprecated
    public static void setup(String yml) {
        file = new File(plugin.getDataFolder(), yml);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("알수 없는 오류로 컨피그 파일을 생성할수 없습니다.");
            }
            plugin.saveResource(yml, true);
        }
    }

    /**
     * 파일에 저장된 값을 가져옵니다
     *
     * @param yml 컨피그 파일 이름(*.yml)
     *
     * @return 컨피그내 값을 가져오거나 수정
     */
    public static FileConfiguration get(String yml) {
        file = new File(plugin.getDataFolder(), yml);

        config = YamlConfiguration.loadConfiguration(file);
        return config;
    }

    /**
     * 실시간으로 파일을 수정합니다
     *
     * @param yml 컨피그 파일 이름(*.yml)
     * @param Path 컨피그 파일내 변경할 값 경로
     * @param value 변경할 값
     *
     * @return 컨피그내 값 수정
     *
     * @deprecated {@link #get(String)}로 이용해 주세요
     */
    @Deprecated
    public static FileConfiguration set(String yml, String Path, Objects value) {
        file = new File(plugin.getDataFolder(), yml);

        YamlConfiguration.loadConfiguration(file).set(Path, value);
        return null;
    }

    /**
     * 실시간으로 수정된 파일을 저장합니다
     *
     * @param yml 컨피그 파일 이름(*.yml)
     *
     * @deprecated {@link #save(String, Boolean)}로 이용해 주세요
     */
    @Deprecated
    public static void save(String yml) {
        file = new File(plugin.getDataFolder(), yml);


        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 실시간으로 수정된 파일을 저장합니다
     *
     * @param yml 컨피그 파일 이름(*.yml)
     * @param NewType 이전 타입(주석 제거)으로 저장 하려면 'false' 아닐경우 'true'
     */
    public static void save(String yml, Boolean NewType) {
        file = new File(plugin.getDataFolder(), yml);

        if (NewType) {

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                ConfigUpdater.update(
                        plugin,
                        yml,
                        file,
                        Collections.singletonList("None")
                );
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 파일을 리로드합니다
     *
     * @param yml 컨피그 파일 이름(*.yml)
     *
     * @deprecated {@link #get(String)}을 사용할 때 자동으로 reload 되기 때문에 더 이상 사용하지 않습니다.
     */
    @Deprecated
    public static void reload(String yml){
        file = new File(plugin.getDataFolder(), yml);

        config = YamlConfiguration.loadConfiguration(file);
    }

    /* ---Legacy Support--- */

    /**
     * 컨피그 파일을 플러그인 폴더에 생성합니다
     *
     * @param Plugin_Name 플러그인 이름
     * @param yml 컨피그 파일 이름(*.yml)
     *
     * @deprecated {@link #makeData(String)}로 이용해 주세요
     */
    @Deprecated
    public static void makeData(String Plugin_Name, String yml) {
        try {
            file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(Plugin_Name)).getDataFolder(), yml);
            if (!file.exists()) {
                Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(Plugin_Name)).saveResource(yml, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * 컨피그 파일 이름을 변경합니다
     *
     * @param Plugin_Name 플러그인 이름
     * @param OriginalName 소스 폴더내 컨피그 파일 이름(*.yml)
     * @param NewName 변경할 컨피그 파일 이름(*.yml)
     *
     * @deprecated {@link #renameTo(String, String)}로 이용해 주세요
     */
    @Deprecated
    public static void renameTo(String Plugin_Name, String OriginalName, String NewName) {
        try {
            File fileOld = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(Plugin_Name)).getDataFolder(), OriginalName);
            File fileNew = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(Plugin_Name)).getDataFolder(), NewName);

            if (fileNew.exists()) {
                fileOld.delete();
            } else {
                fileOld.renameTo(fileNew);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 파일에 저장된 값을 가져옵니다
     *
     * @param Plugin_Name 플러그인 이름
     * @param yml 컨피그 파일 이름(*.yml)
     *
     * @return 컨피그내 값을 가져오거나 수정
     *
     * @deprecated {@link #get(String)}로 이용해 주세요
     */
    @Deprecated
    public static FileConfiguration get(String Plugin_Name, String yml) {
        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(Plugin_Name)).getDataFolder(), yml);

        config = YamlConfiguration.loadConfiguration(file);
        return config;
    }

    /**
     * 실시간으로 수정된 파일을 저장합니다
     *
     * @param Plugin_Name 플러그인 이름
     * @param yml 컨피그 파일 이름(*.yml)
     * @param NewType 이전 타입(주석 제거)으로 저장 하려면 'false' 아닐경우 'true'
     *
     * @deprecated {@link #save(String, Boolean)}로 이용해 주세요
     */
    @Deprecated
    public static void save(String Plugin_Name, String yml, Boolean NewType) {
        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(Plugin_Name)).getDataFolder(), yml);

        if (NewType) {

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                ConfigUpdater.update(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(Plugin_Name)), yml, file, Collections.singletonList("None"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
