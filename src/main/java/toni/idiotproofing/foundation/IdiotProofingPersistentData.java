package toni.idiotproofing.foundation;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class IdiotProofingPersistentData {
    public static final File FILE = new File("idiotproofing.json");
    public static Date timeStarted = new Date();

    public static final Codec<IdiotProofingPersistentData> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.INT.fieldOf("playtime").forGetter((data) -> data.playtime),
            Codec.STRING.listOf().optionalFieldOf("seenWarnings", new ArrayList<>()).forGetter(data -> data.seenWarnings.stream().toList())
        ).apply(instance, IdiotProofingPersistentData::new)
    );

    public IdiotProofingPersistentData(Integer playtime, List<String> seenTips) {
        this.playtime = playtime;
        this.seenWarnings = new HashSet<>(seenTips);
    }

    public int playtime;
    public Set<String> seenWarnings;

    public void save() {
        long millis = new Date().getTime() - timeStarted.getTime();
        playtime += (int) (millis / 1000);
        timeStarted = new Date();

        try (FileWriter writer = new FileWriter(FILE)) {
            JsonElement jsonElement = CODEC.encodeStart(JsonOps.INSTANCE, this)
                .getOrThrow(#if mc < 211 false, #endif error -> {
                    throw new RuntimeException("Failed to encode IdiotProofing PersistentData: " + error);
                });
            writer.write(jsonElement.toString());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save IdiotProofing PersistentData! ", e);
        }
    }

    public static IdiotProofingPersistentData load() {
        if (!FILE.exists())
            return new IdiotProofingPersistentData(0, new ArrayList<>());

        try (FileReader reader = new FileReader(FILE)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            return CODEC.parse(JsonOps.INSTANCE, jsonElement)
                .getOrThrow(#if mc < 211 false, #endif error -> {
                    throw new RuntimeException("Failed to decode IdiotProofing PersistentData: " + error);
                });
        } catch (IOException e) {
            throw new RuntimeException("Failed to load IdiotProofing PersistentData! ", e);
        }
    }
}