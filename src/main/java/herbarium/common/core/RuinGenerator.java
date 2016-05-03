package herbarium.common.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import herbarium.api.ruins.IRuin;
import herbarium.api.ruins.IRuinContext;
import herbarium.common.Herbarium;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.io.InputStreamReader;

public final class RuinGenerator{
    public static void generate(IRuin ruin, World world, BlockPos start){
        Minecraft mc = Herbarium.proxy.getClient();
        try(JsonReader reader = new JsonReader(new InputStreamReader(mc.getResourceManager().getResource(ruin.template()).getInputStream()))){
            JsonArray levels = new JsonParser().parse(reader).getAsJsonArray();
            IRuinContext context = ruin.context();
            int y = start.getY();
            int z = start.getZ();
            int x = start.getX();
            for(JsonElement l : levels){
                if(!l.isJsonArray()) throw new IllegalStateException("Error");
                JsonArray level = l.getAsJsonArray();
                for(JsonElement r : level){
                    String row = r.getAsString();
                    for(char c : row.toCharArray()){
                        world.setBlockState(new BlockPos(x, y, z), context.map(c).getDefaultState());
                        z++;
                    }
                    z = start.getZ();
                    x++;
                }
                x = start.getX();
                y++;
            }
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}