package wildCaves.generation.structureGen;

import java.util.Random;

import net.minecraft.init.Blocks;
import wildCaves.Utils;
import wildCaves.WildCaves;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import wildCaves.WorldGenWildCaves;

public class GenerateSandstoneStalactites {
	public static void generate(World world, Random random, int x, int y, int z, int distance, int maxLength) {
		boolean stalagmiteGenerated = false;
		Block blockId = WildCaves.blockSandStalactite;
		if (distance <= 1) {
            if (!world.isAirBlock(x, y + 1, z)) {
                world.setBlock(x, y, z, blockId, 0, 2);
            }
		} else {
			int j = 0; // blocks placed
			int topY = y;
			int botY = y - distance + 1;
			int aux;
			//stalactite base
			if (!world.isAirBlock(x, topY + 1, z)) {
				world.setBlock(x, topY, z, blockId, Utils.randomChoise(1, 2, 3, 3), 2);
				Utils.convertToSandType(world, random, x, topY, z);
				j++;
			}
			// stalagmite base
			if (!world.getBlock(x, botY, z).getMaterial().isLiquid() && WorldGenWildCaves.isWhiteListed(world.getBlock(x, botY - 1, z))) {
				aux = Utils.randomChoise(-1, 8, 9, 10);
				if (aux != -1) {
					if (world.getBlock(x, botY - 1, z) == Blocks.stone)
						world.setBlock(x, botY - 1, z, Blocks.sandstone, 0, 2);
					world.setBlock(x, botY, z, blockId, aux, 2);
					j++;
					stalagmiteGenerated = true;
					Utils.convertToSandType(world, random, x, botY, z);
				}
			}
			if (j==2) {
                int k = 0; // counter
                int topMetadata, bottomMetadata;
				while (k < maxLength && topY >= botY && j < distance && !world.getBlock(x, topY - 1, z).getMaterial().isLiquid()) {
					k++;
					topMetadata = world.getBlockMetadata(x, topY, z);
					bottomMetadata = world.getBlockMetadata(x, botY, z);
					topY--;
					botY++;
					// Expand downwards
					if (world.isAirBlock(x, topY, z) && topMetadata > 2 && topMetadata < 6) {
						aux = random.nextInt(5);
						if (aux != 4)
							world.setBlock(x, topY, z, blockId, Utils.randomChoise(4, 5, 7, 11), 2);
						else
							world.setBlock(x, topY, z, blockId, Utils.randomChoise(7, 11), 2);
						j++;
					}
					// Expand upwards
					if (world.isAirBlock(x, botY, z) && (bottomMetadata > 3 && bottomMetadata < 5 || bottomMetadata == 8) && j < distance && stalagmiteGenerated) {
						aux = random.nextInt(5);
						if (aux != 4)
							world.setBlock(x, botY, z, blockId, Utils.randomChoise(4, 5, 6, 12), 2);
						else
							world.setBlock(x, botY, z, blockId, Utils.randomChoise(12, 6), 2);
						j++;
					}
				}
			}
		}
	}
}
