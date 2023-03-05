# EasyLib
[![Release](https://jitpack.io/v/WindLeaf233/EasyLib.svg)](https://jitpack.io/#WindLeaf233/EasyLib)
![Build](https://img.shields.io/github/actions/workflow/status/WindLeaf233/EasyLib/build.yml?branch=master)
![Commit](https://img.shields.io/github/last-commit/WindLeaf233/EasyLib)

![Size](https://img.shields.io/github/repo-size/WindLeaf233/EasyLib)
[![License](https://img.shields.io/github/license/WindLeaf233/EasyLib)](https://choosealicense.com/licenses/gpl-3.0/)


:four_leaf_clover: 旨在让创建 `Minecraft` 插件更简单的支持库，易于使用。

---

## 使用方法
**版本号可为 `master-SNAPSHOT` 表示最新构建版**

### Gradle
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.WindLeaf233:EasyLib:1.0.3'
}
```

### Maven
```xml
<repositories>
    <repository>
        <id>jitpack</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.WindLeaf233</groupId>
        <artifactId>EasyLib</artifactId>
        <version>1.0.3</version>
    </dependency>
</dependencies>
```

## TODOs
* 添加国际化
* 多个 `EasyLibPlugin` 实例兼容性
* 优化更新时插件名提及
