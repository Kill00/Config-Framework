package com.teamverymc.configframework;

import com.tchristofferson.configupdater.ConfigUpdater;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.Collections;
import java.util.Objects;

public class cfg {

    private static File file;
    public static FileConfiguration config;

    /**
     * 파일을 플러그인 폴더에 추가합니다
     */
    public static void makeData(String Plugin_Name, String yml) {
        try {
            file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(Plugin_Name)).getDataFolder(), yml);
            if (!file.exists()) {
                Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(Plugin_Name)).saveResource(yml, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 파일 이름을 변경합니다
     */
    public static void renameTo(String Plugin_Name, String OldName, String NewName) {
        try {
            var fileOld = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(Plugin_Name)).getDataFolder(), OldName);
            var fileNew = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(Plugin_Name)).getDataFolder(), NewName);

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
     * 파일을 플러그인 폴더에 추가합니다
     *
     * @deprecated {@link #makeData(String, String)} 을 사용해 주세요
     */
    @Deprecated
    public static void setup(String Plugin_Name, String yml) {
        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(Plugin_Name)).getDataFolder(), yml);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("알수 없는 오류로 콘피그 파일을 생성할수 없습니다.");
            }
            Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(Plugin_Name)).saveResource(yml, true);
        }
    }

    /**
     * 파일에 저장된 값을 가져옵니다
     */
    public static FileConfiguration get(String Plugin_Name, String yml) {
        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(Plugin_Name)).getDataFolder(), yml);

        config = YamlConfiguration.loadConfiguration(file);
        return config;
    }

    /**
     * 실시간으로 파일을 수정합니다
     */
    @Deprecated
    public static FileConfiguration set(String Plugin_Name, String yml, String Path, Objects value) {
        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(Plugin_Name)).getDataFolder(), yml);

        YamlConfiguration.loadConfiguration(file).set(Path, value);
        return null;
    }

    /**
     * 실시간으로 수정된 파일을 저장합니다
     *
     * @deprecated {@link #save(String, String, Boolean)} 을 사용해 주세요
     */
    @Deprecated
    public static void save(String Plugin_Name, String yml) {
        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(Plugin_Name)).getDataFolder(), yml);


        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 실시간으로 수정된 파일을 저장합니다
     */
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

    /**
     * 파일을 리로드합니다
     *
     * @deprecated {@link #get(String, String)} 을 사용할 때 자동으로 reload 되기 때문에 더 이상 사용하지 않습니다.
     */
    @Deprecated
    public static void reload(String Plugin_Name, String yml){
        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(Plugin_Name)).getDataFolder(), yml);

        config = YamlConfiguration.loadConfiguration(file);
    }
}
