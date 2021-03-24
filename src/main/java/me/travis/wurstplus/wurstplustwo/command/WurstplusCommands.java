/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.text.Style
 */
package me.travis.wurstplus.wurstplustwo.command;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import me.travis.turok.values.TurokString;
import me.travis.wurstplus.wurstplustwo.command.WurstplusCommand;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusAlert;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusBind;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusConfig;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusDrawn;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusEnemy;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusEzMessage;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusFriend;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusHelp;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusPrefix;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusSettings;
import me.travis.wurstplus.wurstplustwo.command.commands.WurstplusToggle;
import net.minecraft.util.text.Style;

public class WurstplusCommands {
    public static ArrayList<WurstplusCommand> command_list = new ArrayList();
    static HashMap<String, WurstplusCommand> list_command = new HashMap();
    public static final TurokString prefix = new TurokString("Prefix", "Prefix", ".");
    public final Style style;

    public WurstplusCommands(Style style_) {
        this.style = style_;
        WurstplusCommands.add_command(new WurstplusBind());
        WurstplusCommands.add_command(new WurstplusPrefix());
        WurstplusCommands.add_command(new WurstplusSettings());
        WurstplusCommands.add_command(new WurstplusToggle());
        WurstplusCommands.add_command(new WurstplusAlert());
        WurstplusCommands.add_command(new WurstplusHelp());
        WurstplusCommands.add_command(new WurstplusFriend());
        WurstplusCommands.add_command(new WurstplusDrawn());
        WurstplusCommands.add_command(new WurstplusEzMessage());
        WurstplusCommands.add_command(new WurstplusEnemy());
        WurstplusCommands.add_command(new WurstplusConfig());
        command_list.sort(Comparator.comparing(WurstplusCommand::get_name));
    }

    public static void add_command(WurstplusCommand command) {
        command_list.add(command);
        list_command.put(command.get_name().toLowerCase(), command);
    }

    public String[] get_message(String message) {
        String[] arguments = new String[]{};
        if (this.has_prefix(message)) {
            arguments = message.replaceFirst(prefix.get_value(), "").split(" ");
        }
        return arguments;
    }

    public boolean has_prefix(String message) {
        return message.startsWith(prefix.get_value());
    }

    public void set_prefix(String new_prefix) {
        prefix.set_value(new_prefix);
    }

    public String get_prefix() {
        return prefix.get_value();
    }

    public static ArrayList<WurstplusCommand> get_pure_command_list() {
        return command_list;
    }

    public static WurstplusCommand get_command_with_name(String name) {
        return list_command.get(name.toLowerCase());
    }
}

