import toni.blahaj.setup.modRuntimeOnly
import toni.blahaj.setup.runtimeOnly

plugins {
	id("toni.blahaj")
}

blahaj {
	config { }
	setup {
		txnilib("1.0.23")
		forgeConfig()
		conditionalMixin()

		deps.modImplementation("toni.immersivemessages:${mod.loader}-${mod.mcVersion}:1.0.18") { isTransitive = false }
		deps.modImplementation("toni.immersivetips:${mod.loader}-${mod.mcVersion}:1.0.1") { isTransitive = false }


		when (mod.projectName) {
			"1.21.1-fabric" -> {
				deps.modImplementation("curse.maven:ftb-quests-fabric-438496:5882271")
				deps.modImplementation("curse.maven:architectury-api-419699:5786326")
				deps.modImplementation("curse.maven:ftb-library-fabric-438495:6874537")
				deps.modImplementation("curse.maven:ftb-teams-fabric-438497:5882218")
				deps.modRuntimeOnly("teamreborn:energy:4.1.0")
			}
			"1.20.1-fabric" -> {
				deps.modImplementation("curse.maven:item-filters-309674:4838265")
				deps.modImplementation("curse.maven:ftb-quests-fabric-438496:5543954")
				deps.modImplementation("curse.maven:ftb-library-fabric-438495:5567590")
				deps.modImplementation("curse.maven:architectury-api-419699:5137936")
				deps.modImplementation("curse.maven:ftb-teams-fabric-438497:5267188")
				deps.modRuntimeOnly("teamreborn:energy:3.0.0")
			}


			"1.21.1-neoforge" -> {
				deps.modImplementation("curse.maven:architectury-api-419699:5786327")
				deps.modImplementation("curse.maven:ftb-quests-forge-289412:6874989")
				deps.modImplementation("curse.maven:ftb-library-forge-404465:6874538")
				deps.modImplementation("curse.maven:ftb-teams-forge-404468:5882217")
			}
			"1.20.1-forge" -> {
				deps.modImplementation("curse.maven:ftb-quests-forge-289412:6829212")
				deps.modImplementation("curse.maven:item-filters-309674:4838266")
				deps.modImplementation("curse.maven:ftb-library-forge-404465:6807424")

				deps.modRuntimeOnly("curse.maven:architectury-api-419699:5137938")
				deps.modRuntimeOnly("curse.maven:ftb-teams-forge-404468:5267190")

				deps.compileOnly(deps.annotationProcessor("io.github.llamalad7:mixinextras-common:0.4.1")!!)
				deps.implementation(deps.include("io.github.llamalad7:mixinextras-forge:0.4.1")!!)
			}
		}
	}
}