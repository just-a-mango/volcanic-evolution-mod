
package com.justamango.volcanicevolution.block;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.event.ModelRegistryEvent;

import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.NonNullList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.block.Block;

import java.util.Random;

import com.justamango.volcanicevolution.item.ItemAmethystGem;
import com.justamango.volcanicevolution.creativetab.TabVolcanicEvolution;
import com.justamango.volcanicevolution.ElementsVolcanicevolutionMod;

@ElementsVolcanicevolutionMod.ModElement.Tag
public class BlockAmethystOre extends ElementsVolcanicevolutionMod.ModElement {
	@GameRegistry.ObjectHolder("volcanicevolution:amethystore")
	public static final Block block = null;
	public BlockAmethystOre(ElementsVolcanicevolutionMod instance) {
		super(instance, 44);
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new BlockCustom().setRegistryName("amethystore"));
		elements.items.add(() -> new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
				new ModelResourceLocation("volcanicevolution:amethystore", "inventory"));
	}

	@Override
	public void generateWorld(Random random, int chunkX, int chunkZ, World world, int dimID, IChunkGenerator cg, IChunkProvider cp) {
		boolean dimensionCriteria = false;
		if (dimID == 0)
			dimensionCriteria = true;
		if (!dimensionCriteria)
			return;
		for (int i = 0; i < 2; i++) {
			int x = chunkX + random.nextInt(16);
			int y = random.nextInt(14) + 1;
			int z = chunkZ + random.nextInt(16);
			(new WorldGenMinable(block.getDefaultState(), 1, new com.google.common.base.Predicate<IBlockState>() {
				public boolean apply(IBlockState blockAt) {
					boolean blockCriteria = false;
					IBlockState require;
					if (blockAt.getBlock() == Blocks.STONE.getDefaultState().getBlock())
						blockCriteria = true;
					return blockCriteria;
				}
			})).generate(world, random, new BlockPos(x, y, z));
		}
	}
	public static class BlockCustom extends Block {
		public BlockCustom() {
			super(Material.ROCK);
			setUnlocalizedName("amethystore");
			setSoundType(SoundType.STONE);
			setHarvestLevel("pickaxe", 9);
			setHardness(13.799999999999999F);
			setResistance(16.950256309730264F);
			setLightLevel(0F);
			setLightOpacity(255);
			setCreativeTab(TabVolcanicEvolution.tab);
		}

		@Override
		public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
			drops.add(new ItemStack(ItemAmethystGem.block, (int) (1)));
		}
	}
}
