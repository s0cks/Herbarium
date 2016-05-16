package herbarium.common.tiles.brewing;

import herbarium.api.brew.IBrew;
import herbarium.api.brew.effects.SuctionConstants;
import herbarium.api.brew.piping.IBrewStack;
import herbarium.api.brew.piping.IBrewTransport;
import herbarium.common.core.brew.piping.BrewPipingHelper;
import herbarium.common.core.brew.piping.BrewStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public final class TileEntityCoalescer
extends TileEntity
implements IBrewTransport,
           ITickable {
  private static final EnumFacing[] INPUTS = {
  EnumFacing.WEST,
  EnumFacing.EAST,
  EnumFacing.NORTH,
  };

  private boolean rotating;
  private float rotation;

  private BrewStack[] inputs = new BrewStack[3];
  private BrewStack output;

  @Override
  public int add(IBrew brew, int amount, EnumFacing facing) {
    if (brew == null) return 0;
    if (!this.canInputFrom(brew, amount, facing)) return 0;

    BrewStack in;
    switch (facing) {
      case WEST:
        in = this.inputs[0];
        break;
      case EAST:
        in = this.inputs[1];
        break;
      case NORTH:
        in = this.inputs[2];
        break;
      default:
        return 0;
    }

    if (in == null) {
      switch (facing) {
        case WEST:
          return (this.inputs[0] = new BrewStack(brew, amount)).amount();
        case EAST:
          return (this.inputs[1] = new BrewStack(brew, amount)).amount();
        case NORTH:
          return (this.inputs[2] = new BrewStack(brew, amount)).amount();
      }
    }

    if (!BrewStack.brewsEqual(in.brew(), brew)) return 0;

    int filled = IBrewStack.MAX_AMOUNT - amount;
    if (amount < filled) {
      in.setAmount(in.amount() + amount);
    } else {
      in.setAmount(IBrewStack.MAX_AMOUNT);
    }
    return filled;
  }

  @Override
  public int suction(EnumFacing facing) {
    BrewStack input = this.getInput(facing);
    if ((input == null) || (input.amount() < IBrewStack.MAX_AMOUNT)) {
      return SuctionConstants.CONSUMER_SUCTION;
    }

    return SuctionConstants.NO_SUCTION;
  }

  @Override
  public int minimumSuction() {
    return SuctionConstants.CONSUMER_SUCTION;
  }

  @Override
  public boolean canInputFrom(IBrew brew, int amount, EnumFacing facing) {
    return facing == EnumFacing.NORTH
           || facing == EnumFacing.EAST
           || facing == EnumFacing.WEST;
  }

  @Override
  public IBrew brew() {
    return this.output == null
           ? null
           : this.output.brew();
  }

  @Override
  public int amount() {
    return this.output == null
           ? 0
           : this.output.amount();
  }

  @Override
  public int extract(IBrew brew, int amount, EnumFacing facing) {
    return 0;
  }

  @Override
  public boolean canConnectTo(EnumFacing facing) {
    return facing == EnumFacing.EAST
           || facing == EnumFacing.WEST
           || facing == EnumFacing.NORTH
           || facing == EnumFacing.SOUTH;
  }

  @Override
  public boolean canOutputTo(EnumFacing facing) {
    return facing == EnumFacing.SOUTH;
  }

  @Override
  public void update() {
    for (EnumFacing facing : INPUTS) {
      if (!this.getWorld().isRemote && (this.getInput(facing) == null || this.getInput(facing)
                                                                             .amount() < IBrewStack.MAX_AMOUNT)) {
        BrewStack input = this.getInput(facing);
        TileEntity tile = BrewPipingHelper.getConnected(this.getWorld(), this.getPos(), facing);
        if (tile != null) {
          IBrewTransport transport = ((IBrewTransport) tile);
          if (!transport.canOutputTo(facing.getOpposite())) return;

          IBrew brew = null;
          if ((input != null && input.brew() != null && input.amount() > 0)) {
            brew = input.brew();
          } else if ((transport.amount() > 0) && (transport.suction(facing.getOpposite()) < this.suction(facing)) && (this.suction(facing) >= transport.minimumSuction())) {
            brew = transport.brew();
          }

          if ((brew != null) && (transport.suction(facing.getOpposite()) < this.suction(facing))) {
            this.add(brew, transport.extract(brew, 1, facing.getOpposite()), facing);
          }
        }
      }
    }
  }

  public BrewStack getInput(EnumFacing facing) {
    BrewStack in;
    switch (facing) {
      case WEST:
        in = this.inputs[0];
        break;
      case EAST:
        in = this.inputs[1];
        break;
      case NORTH:
        in = this.inputs[2];
        break;
      default:
        in = null;
    }
    return in;
  }
}