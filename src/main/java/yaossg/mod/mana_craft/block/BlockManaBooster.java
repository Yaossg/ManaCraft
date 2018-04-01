package yaossg.mod.mana_craft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yaossg.mod.Util;
import yaossg.mod.mana_craft.ManaCraft;
import yaossg.mod.mana_craft.inventory.ManaCraftGUIs;
import yaossg.mod.mana_craft.tile.TileManaBooster;

import java.util.List;

public class BlockManaBooster extends BlockContainer {
    public static final PropertyBool BURNING = PropertyBool.create("burning");

    public BlockManaBooster() {
        super(Material.IRON);
        this.setUnlocalizedName("mana_booster");
        this.setHardness(3);
        this.setLightLevel(Util.getLightLevel(9));
        this.setCreativeTab(ManaCraft.tabMana);
        this.setHarvestLevel("pickaxe", Util.IRON_PICKAXE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BURNING, Boolean.FALSE));
    }
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BURNING);
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(BURNING, meta != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BURNING) ? 1 : 0;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, List<ItemStack> subItems) {
        subItems.add(new ItemStack(itemIn));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileManaBooster();
    }
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos,
                                    IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.openGui(ManaCraft.instance, ManaCraftGUIs.GUIs.mana_booster.getId(), worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileManaBooster tile = (TileManaBooster) worldIn.getTileEntity(pos);
        Block.spawnAsEntity(worldIn, pos, tile.fuel.getStackInSlot(0));
        super.breakBlock(worldIn, pos, state);}
}
