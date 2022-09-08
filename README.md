# EasyLib
[![Release](https://jitpack.io/v/WindLeaf233/EasyLib.svg)](https://jitpack.io/#WindLeaf233/EasyLib)
![Build](https://img.shields.io/github/workflow/status/WindLeaf233/EasyLib/Java%20CI%20with%20Gradle)
![Commit](https://img.shields.io/github/last-commit/WindLeaf233/EasyLib)

![Size](https://img.shields.io/github/repo-size/WindLeaf233/EasyLib)
[![License](https://img.shields.io/github/license/WindLeaf233/EasyLib)](https://choosealicense.com/licenses/gpl-3.0/)


An API Library For Minecraft Plugins

---

## Uses
### Gradle
1. Add `jitpack` repositories
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```
2. Add dependency

**The `Tag` should be replaced by the latest version tag, such as `1.0.0`.**
**Also can be `master-SNAPSHOT` for the latest build.**
```groovy
dependencies {
    implementation 'com.github.WindLeaf233:EasyLib:Tag'
}
```

## Usages
### Logging
1. Import `PluginLogger` from `ml.windleaf.api.logging`
```java
import ml.windleaf.api.logging.PluginLogger;

// want to custom? import these:
import ml.windleaf.api.logging.format.NameFormat;
import ml.windleaf.api.logging.format.Separator;
```
2. Use the `PluginLogger`
```java
// name format defaults to `NameFormat.SQUARE_BRACKETS`
// separator defaults to `Separator.EMPTY`
PluginLogger logger = new PluginLogger("PluginName");

// or custom name format and separator
PluginLogger logger2 = new PluginLogger("PluginName", NameFormat.EMPTY, Separator.SINGLE_ARROW);
```

### Registering
1. Import `RegisterManager` from `ml.windleaf.api.register`
```java
import ml.windleaf.api.register.RegisterManager;

// you need import interfaces to use `RegisterManager`
import ml.windleaf.api.interfaces.ICommand;
import ml.windleaf.api.interfaces.IListener;
```
2. Use the `RegisterManager` to register commands, event listeners
```java
// initialize the `RegisterManager`
// it will automatically register command which implements `ICommand` and listener which implements `IListener`
// `plugin` in the parameters means `the plugin`, which implements the `JavaPlugin`
// `packagePath` in the parameters means `the package path of your plugin`, such as below
new RegisterManager(this, "ml.windleaf.testplugin");
```
3. Implement `ICommand`
```java
// THIS COMMAND CLASS WILL AUTOMATICALLY BE REGISTERED BY `RegisterManager`
// SO DO NOT REGISTER IT BY YOUR SELF
public class TestCommand implements ICommand {
    @Override
    public String command() {
        // this method must return a string refers to command name
        // such as this string, it means command `/test`
        return "test";
    }

    // we have simplified the `onCommand` method
    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull List<String> args) {
        // do something...
    }
}
```
4. Implement `IListener`
```java
// THIS LISTENER CLASS WILL AUTOMATICALLY BE REGISTERED BY `RegisterManager`
// SO DO NOT REGISTER IT BY YOUR SELF
public class TestListener implements IListener {
    // same as normal plugin, this shout be `EventHandler` annotation
    @EventHandler
    public void handler(PlayerJoinEvent event) {
        // do something...
    }
}
```
### Utils
1. Color the text
```java
// import the `ChatColorUtil`
import ml.windleaf.api.utils.ChatColorUtil;
// you also need import `ChatColor`
import org.bukkit.ChatColor;

...

String text = "hello, world!";
String coloredText = ChatColorUtil.getTextColored(text, ChatColor.RED);



// or do this
// import the `ColoredTextUtil`
import ml.windleaf.api.utils.ColoredTextUtil;

...

// this does not work
String text = "hello, world!";
String coloredText = ColoredTextUtil.color(text)

// this works
// P.S. `&c` means `§c`, aka. red
String uncoloredText = "&chello, world!"
String coloredText = ColoredTextUtil.color(text)
```
2. Get classes which implements another class
```java
// import the `ResolverUtil`
import ml.windleaf.api.utils.ClassUtil;

...

public interface Animal {
    void move();
}

public class Cat implements Animal {
    @Override
    public void move() {}

    public void meow() {
        System.out.println("meow~");
    }
}

...

ResolverUtil<Animal> ru = new ResolverUtil();
// `packagePath` in the parameters means `the package path of your plugin`, such as below
// this list will contains the class `Cat`
List<Class<? extends Animal>> classList = ru.getClassesBySuperclass("ml.windleaf.test");
// you can use for loop to get new instance
for (Class<? extends Animal> cls : classList) {
    Animal animal = ((Class<Animal>) cls).getDeclaredConstructor().newInstance();
    animal.move();
}
```
