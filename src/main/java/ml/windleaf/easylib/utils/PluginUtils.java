package ml.windleaf.easylib.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import static ml.windleaf.easylib.plugin.EasyLibPlugin.*;

/**
 * 和插件相关的工具类
 */
@SuppressWarnings({"unchecked", "unused"})
public class PluginUtils {
    /**
     * 调用事件
     *
     * @param event 事件
     */
    public static void callEvent(@NotNull Event event) {
        Bukkit.getScheduler().runTask(instance, () -> instance.getServer().getPluginManager().callEvent(event));
    }

    public static void registerEvent(@NotNull Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, instance);
    }

    /**
     * 根据所给的条件在集合中找到对应的元素
     *
     * @param collection 集合
     * @param predicate  判断匿名函数
     * @param <T>        元素泛型
     * @return 元素，不存在则为 `null`
     */
    @Nullable
    public static <T> T find(@NotNull Collection<T> collection, @NotNull Predicate<T> predicate) {
        AtomicReference<T> result = new AtomicReference<>();
        collection.forEach(any -> {
            if (predicate.test(any)) result.set(any);
        });
        return result.get();
    }

    /**
     * 获取配置对象
     *
     * @param path         配置路径
     * @param defaultValue 默认值
     * @param <T>          对象泛型
     * @return 获取到的配置对象
     */
    @NotNull
    public static <T> T getConfig(@NotNull @Nls String path, @NotNull T defaultValue) {
        T result;
        try {
            result = (T) instance.getConfig().get(path);
        } catch (Exception ignore) {
            result = defaultValue;
        }
        return result == null ? defaultValue : result;
    }

    /**
     * 获取插件的绝对工作目录
     *
     * @return 绝对工作目录
     * @exmaple C:\\Some Server\\plugins\\SomePlugin
     */
    @NotNull
    @Nls
    public static String getWorkingPath() {
        return instance.getDataFolder().getAbsolutePath();
    }

    /**
     * 使用 `GitHub API` 检查插件的版本, 如果有更新则提示并自动更新
     *
     * @param version    当前的版本
     * @param githubRepo `GitHub` 的仓库地址
     */
    @SuppressWarnings("all")
    public static void checkUpdate(@NotNull @Nls String version, @NotNull @Nls String githubRepo) {
        try {
            // 1.2.0 -> 120
            String parsedVersion = version.replace(".", "");
            // 获取 api
            URL api = new URL(String.format("https://api.github.com/repos/%s/releases/latest", githubRepo));
            URLConnection conn = api.openConnection();
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            JSONObject json = JSON.parseObject(new InputStreamReader(conn.getInputStream()));
            // 获取最新的 tag_name
            String tagName = json.getString("tag_name");
            String parsedTagName = tagName.replace(".", "");
            // 判断是否是最新版
            if (Integer.parseInt(parsedTagName) > Integer.parseInt(parsedVersion)) {
                pLogger.logConsole("#GREEN#Found new version: ", tagName);
                // 下载最新版
                List<Map> assets = json.getList("assets", Map.class);
                for (Map assetMap : assets) {
                    String name = (String) assetMap.get("name");
                    if (!name.contains("source") && !name.contains("original") && name.contains(".jar")) {
                        pLogger.logConsole("#GREEN#Downloading the latest plugin file...");
                        URL downloadUrl = new URL((String) assetMap.get("url"));
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                try {
                                    InputStream is = downloadUrl.openStream();
                                    if (!updateFolder.exists()) updateFolder.mkdir();
                                    File src = new File(updateFolder, name);
                                    Files.copy(is, src.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                    // 尝试替换
                                    try {
                                        String outdatedFilePath = instance.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
                                        File outdatedFile = new File(outdatedFilePath);
                                        FileOutputStream fos = new FileOutputStream(outdatedFile, false);
                                        FileUtils.copyFile(outdatedFile, fos);
                                        fos.close();
                                        pLogger.logConsole("#GREEN#Successfully updated plugin, please restart or reload server!");
                                    } catch (Exception ioe) {
                                        pLogger.logConsole("#RED#Could not replace file, please replace it manually: ", ioe.getMessage());
                                    }
                                } catch (Exception error) {
                                    pLogger.logConsole("#RED#Could not download the latest plugin file: ", error.getMessage());
                                }
                            }
                        }.runTaskLaterAsynchronously(instance, 0);
                        return;
                    }
                }
            }
        } catch (Exception error) {
            pLogger.logConsole("#RED#Could not get the lastest version of plugin (15s timed out), please check the network connection: ", error);
        }
    }
}
