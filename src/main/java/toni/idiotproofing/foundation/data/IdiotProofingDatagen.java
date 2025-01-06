package toni.idiotproofing.foundation.data;

#if FABRIC
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import toni.idiotproofing.IdiotProofing;

public class IdiotProofingDatagen  implements DataGeneratorEntrypoint {

    @Override
    public String getEffectiveModId() {
        return IdiotProofing.ID;
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();
        pack.addProvider(ConfigLangDatagen::new);
    }
}
#endif