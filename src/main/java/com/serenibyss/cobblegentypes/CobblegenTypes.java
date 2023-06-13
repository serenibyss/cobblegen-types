package com.serenibyss.cobblegentypes;

import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Tags.MODID, version = Tags.VERSION, name = Tags.MODNAME, acceptedMinecraftVersions = "[1.12.2]")
public class CobblegenTypes {

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public void onFluidPlaceBlockEvent(BlockEvent.FluidPlaceBlockEvent event) {
        if (event.getOriginalState().getBlock() == Blocks.FLOWING_LAVA) {
            BlockPos lavaPos = event.getLiquidPos();
            IBlockState waterState = null;
            World world = event.getWorld();

            for (var pos : new BlockPos[]{lavaPos.east(), lavaPos.west(), lavaPos.north(), lavaPos.south()}) {
                IBlockState checkedState = world.getBlockState(pos);
                if (checkedState.getMaterial() == Material.WATER) {
                    waterState = checkedState;
                    break;
                }
            }

            if (waterState != null) {
                switch (waterState.getBlock().getMetaFromState(waterState)) {
                    case 2, 3 ->
                            event.setNewState(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE));
                    case 4, 5 ->
                            event.setNewState(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE));
                    case 6, 7 ->
                            event.setNewState(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE));
                    default ->
                            event.setNewState(Blocks.COBBLESTONE.getDefaultState());
                }
            }
        }
    }
}
