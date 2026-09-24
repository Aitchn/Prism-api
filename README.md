# Prism API (0.9.61 preview)

The public Java API for writing addons for the Prism Minecraft plugin. This repository contains only the `com.aitchn.prism.api` source and contract tests, its build files, and addon-facing documentation. It does not contain Prism's implementation or a server plugin.

## Choose a matching version

| Prism server | API source | API artifact |
| --- | --- | --- |
| Prism 0.9.60 (released) | `main` | `3.21` |
| Prism 0.9.61 (in development) | `0.9.61` | `3.23` preview |

Compile against the API matching the Prism version installed on your server. The constants in [`PrismApi`](src/main/java/com/aitchn/prism/api/PrismApi.java) are authoritative. The `0.9.61` branch may change until that release is promoted.

## Build the API JAR

Install JDK 25, then run `./gradlew build` (or `gradlew.bat build` on Windows). The artifact is `build/libs/Prism-api-<API version>.jar`. Releases also provide a ready-to-use JAR. Use it as a **compile-only** dependency; the running Prism plugin provides the API classes.

For a Gradle addon project with a downloaded API JAR in `libs/`:

```kotlin
plugins { java }

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(files("libs/Prism-api-3.23.jar"))
    compileOnly("dev.folia:folia-api:26.2.build.5-beta")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}
```

Do not shade or bundle Prism API classes into the addon JAR. Add `depend: [Prism]` to the addon's `plugin.yml`, and use the server's matching Paper/Folia API. If your addon supports Folia, its own world and player access must follow Folia ownership rules.

## Access and register services

Prism publishes `PrismApi` through Bukkit's service manager during `onLoad`. A dependent addon can register its types there:

```java
package example;

import com.aitchn.prism.api.PrismApi;
import com.aitchn.prism.api.PrismKey;
import java.util.Objects;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExampleAddon extends JavaPlugin {
    @Override
    public void onLoad() {
        PrismApi prism = Objects.requireNonNull(
                getServer().getServicesManager().load(PrismApi.class),
                "Prism API is unavailable"
        );
        if (!prism.supports(3, 23)) {
            throw new IllegalStateException("Prism API 3.23 or newer is required");
        }
        prism.structureBehaviors().register(
                this,
                PrismKey.parse("example:hello"),
                interaction -> interaction.player().sendMessage("Hello from an addon")
        );
    }
}
```

```yaml
name: ExampleAddon
version: '1.0.0'
main: example.ExampleAddon
api-version: '26.2'
depend: [Prism]
```

Registration makes a behavior type available; content must still declare and bind that type before players can use it. Prism validates declarations, publishes immutable registry snapshots, and dispatches callbacks only to enabled owners. Check the interfaces under [`src/main/java/com/aitchn/prism/api`](src/main/java/com/aitchn/prism/api) for available services, contexts, and registry methods.

## Updating the mirror

Prism's source tree remains authoritative. Every public API change must be copied into the matching branch here, compiled independently, and published with a matching version. The private Prism repository contains a mirror check and an export script so a later API change cannot silently leave this repository stale. API version tags use `v<version>` for released contracts; preview branches are not a guarantee of runtime availability.

The API source in this repository is MIT licensed. Prism itself and any addon retain their own licensing and distribution terms.
