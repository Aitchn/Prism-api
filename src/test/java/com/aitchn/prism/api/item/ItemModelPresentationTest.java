package com.aitchn.prism.api.item;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemModelPresentationTest {
    @Test
    void profileUsesDeclaredStateAndRejectsInvalidDefaultNames() {
        var key = com.aitchn.prism.api.PrismKey.parse("sample:skin");
        var profile = new ItemModelPresentation.StateProfile(key, "Steve");
        assertEquals("Dinnerbone", profile.name(new ItemInstanceState(java.util.Map.of(key, "Dinnerbone"))));
        assertEquals("Steve", profile.name(ItemInstanceState.empty()));
        for (String value : java.util.List.of("", "bad name", "../skin", "12345678901234567")) {
            assertThrows(IllegalArgumentException.class, () -> new ItemModelPresentation.StateProfile(key, value));
            assertEquals("Steve", profile.name(new ItemInstanceState(java.util.Map.of(key, value))));
        }
        var legacy = new ItemModelPresentation.Split(key, new ItemModelPresentation.Resource(
                com.aitchn.prism.api.PrismKey.parse("minecraft:paper")), java.util.List.of(), null, java.util.List.of());
        assertNull(legacy.profile());
    }

    @Test
    void spriteUsesSeparateItemIdentityAndResourcePathSyntax() {
        var id = com.aitchn.prism.api.PrismKey.parse("sample:hammer");
        var sprite = new ItemModelPresentation.Sprite(id, "sample:item/tools/hammer", true);
        assertEquals(id, sprite.javaModel());
        assertTrue(sprite.handheld());
        for (String invalid : java.util.List.of("hammer", "sample:../private", "sample:item/hammer.png",
                "sample:/hammer", "sample:item//hammer", "sample:item/")) {
            assertThrows(IllegalArgumentException.class, () -> new ItemModelPresentation.Sprite(id, invalid, false));
        }
        assertThrows(IllegalArgumentException.class, () -> new ItemModelPresentation.Sprite(
                com.aitchn.prism.api.PrismKey.parse("minecraft:paper"), "sample:item/hammer", false));
    }

    @Test
    void validatesTextureHashAndCreatesStaticProfileWithoutChangingItemIdentity() {
        String hash = "984f9e0052bacae2f42a12db529fef8d4ae93a7badd724d7aecd5c61329f2c8b";
        var model = new ItemModelPresentation.Head(hash);
        assertEquals("minecraft:player_head", model.javaModel().toString());
        assertEquals("{\"textures\":{\"SKIN\":{\"url\":\"https://textures.minecraft.net/texture/" + hash + "\"}}}",
                new String(Base64.getDecoder().decode(model.textureProperty()), StandardCharsets.UTF_8));
        for (String invalid : java.util.List.of("40832", "../texture", "https://textures.minecraft.net/texture/" + hash, hash.toUpperCase())) {
            assertThrows(IllegalArgumentException.class, () -> new ItemModelPresentation.Head(invalid));
        }
    }
}
