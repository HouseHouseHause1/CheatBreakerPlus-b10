/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.google.common.reflect.TypeToken
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.google.gson.JsonPrimitive
 */
package me.travis.wurstplus.wurstplustwo.manager;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.components.WurstplusFrame;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.manager.WurstplusCommandManager;
import me.travis.wurstplus.wurstplustwo.util.WurstplusDrawnUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEnemyUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEzMessageUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;

public class WurstplusConfigManager {
    private final String MAIN_FOLDER = "CBPLUS/";
    private final String CONFIGS_FOLDER = "CBPLUS/configs/";
    private String ACTIVE_CONFIG_FOLDER = "CBPLUS/configs/default/";
    private final String CLIENT_FILE = "client.json";
    private final String CONFIG_FILE = "config.txt";
    private final String DRAWN_FILE = "drawn.txt";
    private final String EZ_FILE = "ez.txt";
    private final String ENEMIES_FILE = "enemies.json";
    private final String FRIENDS_FILE = "friends.json";
    private final String HUD_FILE = "hud.json";
    private final String BINDS_FILE = "binds.txt";
    private final String SPAMMER_FILE = "spammer.txt";
    private final String CLIENT_DIR = "CBPLUS/client.json";
    private final String CONFIG_DIR = "CBPLUS/config.txt";
    private final String DRAWN_DIR = "CBPLUS/drawn.txt";
    private final String EZ_DIR = "CBPLUS/ez.txt";
    private final String ENEMIES_DIR = "CBPLUS/enemies.json";
    private final String FRIENDS_DIR = "CBPLUS/friends.json";
    private final String HUD_DIR = "CBPLUS/hud.json";
    private final String SPAMMER_DIR = "CBPLUS/spammer.txt";
    private String CURRENT_CONFIG_DIR = "CBPLUS/CBPLUS/configs/" + this.ACTIVE_CONFIG_FOLDER;
    private String BINDS_DIR = this.CURRENT_CONFIG_DIR + "binds.txt";
    private final Path MAIN_FOLDER_PATH = Paths.get("CBPLUS/", new String[0]);
    private final Path CONFIGS_FOLDER_PATH = Paths.get("CBPLUS/configs/", new String[0]);
    private Path ACTIVE_CONFIG_FOLDER_PATH = Paths.get(this.ACTIVE_CONFIG_FOLDER, new String[0]);
    private final Path CLIENT_PATH = Paths.get("CBPLUS/client.json", new String[0]);
    private final Path CONFIG_PATH = Paths.get("CBPLUS/config.txt", new String[0]);
    private final Path DRAWN_PATH = Paths.get("CBPLUS/drawn.txt", new String[0]);
    private final Path EZ_PATH = Paths.get("CBPLUS/ez.txt", new String[0]);
    private final Path ENEMIES_PATH = Paths.get("CBPLUS/enemies.json", new String[0]);
    private final Path FRIENDS_PATH = Paths.get("CBPLUS/friends.json", new String[0]);
    private final Path HUD_PATH = Paths.get("CBPLUS/hud.json", new String[0]);
    private Path BINDS_PATH = Paths.get(this.BINDS_DIR, new String[0]);
    private Path CURRENT_CONFIG_PATH = Paths.get(this.CURRENT_CONFIG_DIR, new String[0]);

    public boolean set_active_config_folder(String folder) {
        if (folder.equals(this.ACTIVE_CONFIG_FOLDER)) {
            return false;
        }
        this.ACTIVE_CONFIG_FOLDER = "CBPLUS/configs/" + folder;
        this.ACTIVE_CONFIG_FOLDER_PATH = Paths.get(this.ACTIVE_CONFIG_FOLDER, new String[0]);
        this.CURRENT_CONFIG_DIR = "CBPLUS/CBPLUS/configs/" + this.ACTIVE_CONFIG_FOLDER;
        this.CURRENT_CONFIG_PATH = Paths.get(this.CURRENT_CONFIG_DIR, new String[0]);
        this.BINDS_DIR = this.CURRENT_CONFIG_DIR + "binds.txt";
        this.BINDS_PATH = Paths.get(this.BINDS_DIR, new String[0]);
        this.load_settings();
        return true;
    }

    private void save_ezmessage() throws IOException {
        FileWriter writer = new FileWriter("CBPLUS/ez.txt");
        try {
            writer.write(WurstplusEzMessageUtil.get_message());
        }
        catch (Exception ignored) {
            writer.write("test message");
        }
        writer.close();
    }

    private void save_spammermessages() throws IOException {
        FileWriter writer = new FileWriter("CBPLUS/ez.txt");
        writer.close();
    }

    private void load_ezmessage() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (String s : Files.readAllLines(this.EZ_PATH)) {
            sb.append(s);
        }
        WurstplusEzMessageUtil.set_message(sb.toString());
    }

    private void save_drawn() throws IOException {
        FileWriter writer = new FileWriter("CBPLUS/drawn.txt");
        for (String s : WurstplusDrawnUtil.hidden_tags) {
            writer.write(s + System.lineSeparator());
        }
        writer.close();
    }

    private void load_drawn() throws IOException {
        WurstplusDrawnUtil.hidden_tags = Files.readAllLines(this.DRAWN_PATH);
    }

    private void save_friends() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(WurstplusFriendUtil.friends);
        OutputStreamWriter file = new OutputStreamWriter((OutputStream)new FileOutputStream("CBPLUS/friends.json"), StandardCharsets.UTF_8);
        file.write(json);
        file.close();
    }

    private void load_friends() throws IOException {
        Gson gson = new Gson();
        BufferedReader reader = Files.newBufferedReader(Paths.get("CBPLUS/friends.json", new String[0]));
        WurstplusFriendUtil.friends = (ArrayList)gson.fromJson((Reader)reader, new TypeToken<ArrayList<WurstplusFriendUtil.Friend>>(){}.getType());
        ((Reader)reader).close();
    }

    private void save_enemies() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(WurstplusEnemyUtil.enemies);
        OutputStreamWriter file = new OutputStreamWriter((OutputStream)new FileOutputStream("CBPLUS/enemies.json"), StandardCharsets.UTF_8);
        file.write(json);
        file.close();
    }

    private void load_enemies() throws IOException {
        Gson gson = new Gson();
        BufferedReader reader = Files.newBufferedReader(Paths.get("CBPLUS/enemies.json", new String[0]));
        WurstplusEnemyUtil.enemies = (ArrayList)gson.fromJson((Reader)reader, new TypeToken<ArrayList<WurstplusEnemyUtil.Enemy>>(){}.getType());
        ((Reader)reader).close();
    }

    private void save_hacks() throws IOException {
        for (WurstplusHack hack : Wurstplus.get_hack_manager().get_array_hacks()) {
            String file_name = this.ACTIVE_CONFIG_FOLDER + hack.get_tag() + ".txt";
            Path file_path = Paths.get(file_name, new String[0]);
            this.delete_file(file_name);
            this.verify_file(file_path);
            File file = new File(file_name);
            BufferedWriter br = new BufferedWriter(new FileWriter(file));
            for (WurstplusSetting setting : Wurstplus.get_setting_manager().get_settings_with_hack(hack)) {
                switch (setting.get_type()) {
                    case "button": {
                        br.write(setting.get_tag() + ":" + setting.get_value(true) + "\r\n");
                        break;
                    }
                    case "combobox": {
                        br.write(setting.get_tag() + ":" + setting.get_current_value() + "\r\n");
                        break;
                    }
                    case "label": {
                        br.write(setting.get_tag() + ":" + setting.get_value("") + "\r\n");
                        break;
                    }
                    case "doubleslider": {
                        br.write(setting.get_tag() + ":" + setting.get_value(1.0) + "\r\n");
                        break;
                    }
                    case "integerslider": {
                        br.write(setting.get_tag() + ":" + setting.get_value(1) + "\r\n");
                    }
                }
            }
            br.close();
        }
    }

    private void load_hacks() throws IOException {
        for (WurstplusHack hack : Wurstplus.get_hack_manager().get_array_hacks()) {
            String line;
            String file_name = this.ACTIVE_CONFIG_FOLDER + hack.get_tag() + ".txt";
            File file = new File(file_name);
            FileInputStream fi_stream = new FileInputStream(file.getAbsolutePath());
            DataInputStream di_stream = new DataInputStream(fi_stream);
            BufferedReader br = new BufferedReader(new InputStreamReader(di_stream));
            ArrayList<String> bugged_lines = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                try {
                    String colune = line.trim();
                    String tag = colune.split(":")[0];
                    String value = colune.split(":")[1];
                    WurstplusSetting setting = Wurstplus.get_setting_manager().get_setting_with_tag(hack, tag);
                    try {
                        switch (setting.get_type()) {
                            case "button": {
                                setting.set_value(Boolean.parseBoolean(value));
                                break;
                            }
                            case "label": {
                                setting.set_value(value);
                                break;
                            }
                            case "doubleslider": {
                                setting.set_value(Double.parseDouble(value));
                                break;
                            }
                            case "integerslider": {
                                setting.set_value(Integer.parseInt(value));
                                break;
                            }
                            case "combobox": {
                                setting.set_current_value(value);
                            }
                        }
                    }
                    catch (Exception e) {
                        bugged_lines.add(colune);
                        Wurstplus.send_minecraft_log("Error loading '" + value + "' to setting '" + tag + "'");
                        break;
                    }
                }
                catch (Exception exception) {
                }
            }
            br.close();
        }
    }

    private void save_client() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser parser = new JsonParser();
        JsonObject main_json = new JsonObject();
        JsonObject config = new JsonObject();
        JsonObject gui = new JsonObject();
        config.add("name", (JsonElement)new JsonPrimitive(Wurstplus.get_name()));
        config.add("version", (JsonElement)new JsonPrimitive(Wurstplus.get_version()));
        config.add("user", (JsonElement)new JsonPrimitive(Wurstplus.get_actual_user()));
        config.add("prefix", (JsonElement)new JsonPrimitive(WurstplusCommandManager.get_prefix()));
        for (WurstplusFrame frames_gui : Wurstplus.click_gui.get_array_frames()) {
            JsonObject frame_info = new JsonObject();
            frame_info.add("name", (JsonElement)new JsonPrimitive(frames_gui.get_name()));
            frame_info.add("tag", (JsonElement)new JsonPrimitive(frames_gui.get_tag()));
            frame_info.add("x", (JsonElement)new JsonPrimitive((Number)frames_gui.get_x()));
            frame_info.add("y", (JsonElement)new JsonPrimitive((Number)frames_gui.get_y()));
            gui.add(frames_gui.get_tag(), (JsonElement)frame_info);
        }
        main_json.add("configuration", (JsonElement)config);
        main_json.add("gui", (JsonElement)gui);
        JsonElement json_pretty = parser.parse(main_json.toString());
        String json = gson.toJson(json_pretty);
        OutputStreamWriter file = new OutputStreamWriter((OutputStream)new FileOutputStream("CBPLUS/client.json"), StandardCharsets.UTF_8);
        file.write(json);
        file.close();
    }

    private void load_client() throws IOException {
        InputStream stream = Files.newInputStream(this.CLIENT_PATH, new OpenOption[0]);
        JsonObject json_client = new JsonParser().parse((Reader)new InputStreamReader(stream)).getAsJsonObject();
        JsonObject json_config = json_client.get("configuration").getAsJsonObject();
        JsonObject json_gui = json_client.get("gui").getAsJsonObject();
        WurstplusCommandManager.set_prefix(json_config.get("prefix").getAsString());
        for (WurstplusFrame frames : Wurstplus.click_gui.get_array_frames()) {
            JsonObject frame_info = json_gui.get(frames.get_tag()).getAsJsonObject();
            WurstplusFrame frame_requested = Wurstplus.click_gui.get_frame_with_tag(frame_info.get("tag").getAsString());
            frame_requested.set_x(frame_info.get("x").getAsInt());
            frame_requested.set_y(frame_info.get("y").getAsInt());
        }
        stream.close();
    }

    private void save_hud() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser parser = new JsonParser();
        JsonObject main_json = new JsonObject();
        JsonObject main_frame = new JsonObject();
        JsonObject main_hud = new JsonObject();
        main_frame.add("name", (JsonElement)new JsonPrimitive(Wurstplus.click_hud.get_frame_hud().get_name()));
        main_frame.add("tag", (JsonElement)new JsonPrimitive(Wurstplus.click_hud.get_frame_hud().get_tag()));
        main_frame.add("x", (JsonElement)new JsonPrimitive((Number)Wurstplus.click_hud.get_frame_hud().get_x()));
        main_frame.add("y", (JsonElement)new JsonPrimitive((Number)Wurstplus.click_hud.get_frame_hud().get_y()));
        for (WurstplusPinnable pinnables_hud : Wurstplus.get_hud_manager().get_array_huds()) {
            JsonObject frame_info = new JsonObject();
            frame_info.add("title", (JsonElement)new JsonPrimitive(pinnables_hud.get_title()));
            frame_info.add("tag", (JsonElement)new JsonPrimitive(pinnables_hud.get_tag()));
            frame_info.add("state", (JsonElement)new JsonPrimitive(Boolean.valueOf(pinnables_hud.is_active())));
            frame_info.add("dock", (JsonElement)new JsonPrimitive(Boolean.valueOf(pinnables_hud.get_dock())));
            frame_info.add("x", (JsonElement)new JsonPrimitive((Number)pinnables_hud.get_x()));
            frame_info.add("y", (JsonElement)new JsonPrimitive((Number)pinnables_hud.get_y()));
            main_hud.add(pinnables_hud.get_tag(), (JsonElement)frame_info);
        }
        main_json.add("frame", (JsonElement)main_frame);
        main_json.add("hud", (JsonElement)main_hud);
        JsonElement pretty_json = parser.parse(main_json.toString());
        String json = gson.toJson(pretty_json);
        this.delete_file("CBPLUS/hud.json");
        this.verify_file(this.HUD_PATH);
        OutputStreamWriter file = new OutputStreamWriter((OutputStream)new FileOutputStream("CBPLUS/hud.json"), StandardCharsets.UTF_8);
        file.write(json);
        file.close();
    }

    private void load_hud() throws IOException {
        InputStream input_stream = Files.newInputStream(this.HUD_PATH, new OpenOption[0]);
        JsonObject main_hud = new JsonParser().parse((Reader)new InputStreamReader(input_stream)).getAsJsonObject();
        JsonObject main_frame = main_hud.get("frame").getAsJsonObject();
        JsonObject main_huds = main_hud.get("hud").getAsJsonObject();
        Wurstplus.click_hud.get_frame_hud().set_x(main_frame.get("x").getAsInt());
        Wurstplus.click_hud.get_frame_hud().set_y(main_frame.get("y").getAsInt());
        for (WurstplusPinnable pinnables : Wurstplus.get_hud_manager().get_array_huds()) {
            JsonObject hud_info = main_huds.get(pinnables.get_tag()).getAsJsonObject();
            WurstplusPinnable pinnable_requested = Wurstplus.get_hud_manager().get_pinnable_with_tag(hud_info.get("tag").getAsString());
            pinnable_requested.set_active(hud_info.get("state").getAsBoolean());
            pinnable_requested.set_dock(hud_info.get("dock").getAsBoolean());
            pinnable_requested.set_x(hud_info.get("x").getAsInt());
            pinnable_requested.set_y(hud_info.get("y").getAsInt());
        }
        input_stream.close();
    }

    private void save_binds() throws IOException {
        String file_name = this.ACTIVE_CONFIG_FOLDER + "BINDS.txt";
        Path file_path = Paths.get(file_name, new String[0]);
        this.delete_file(file_name);
        this.verify_file(file_path);
        File file = new File(file_name);
        BufferedWriter br = new BufferedWriter(new FileWriter(file));
        for (WurstplusHack modules : Wurstplus.get_hack_manager().get_array_hacks()) {
            br.write(modules.get_tag() + ":" + modules.get_bind(1) + ":" + modules.is_active() + "\r\n");
        }
        br.close();
    }

    private void load_binds() throws IOException {
        String line;
        String file_name = this.ACTIVE_CONFIG_FOLDER + "BINDS.txt";
        File file = new File(file_name);
        FileInputStream fi_stream = new FileInputStream(file.getAbsolutePath());
        DataInputStream di_stream = new DataInputStream(fi_stream);
        BufferedReader br = new BufferedReader(new InputStreamReader(di_stream));
        while ((line = br.readLine()) != null) {
            try {
                String colune = line.trim();
                String tag = colune.split(":")[0];
                String bind = colune.split(":")[1];
                String active = colune.split(":")[2];
                WurstplusHack module = Wurstplus.get_hack_manager().get_module_with_tag(tag);
                module.set_bind(Integer.parseInt(bind));
                module.set_active(Boolean.parseBoolean(active));
            }
            catch (Exception exception) {}
        }
        br.close();
    }

    public void save_settings() {
        try {
            this.verify_dir(this.MAIN_FOLDER_PATH);
            this.verify_dir(this.CONFIGS_FOLDER_PATH);
            this.verify_dir(this.ACTIVE_CONFIG_FOLDER_PATH);
            this.save_hacks();
            this.save_binds();
            this.save_friends();
            this.save_enemies();
            this.save_client();
            this.save_drawn();
            this.save_ezmessage();
            this.save_hud();
        }
        catch (IOException e) {
            Wurstplus.send_minecraft_log("Something has gone wrong while saving settings");
            Wurstplus.send_minecraft_log(e.toString());
        }
    }

    public void load_settings() {
        try {
            this.load_binds();
            this.load_client();
            this.load_drawn();
            this.load_enemies();
            this.load_ezmessage();
            this.load_friends();
            this.load_hacks();
            this.load_hud();
        }
        catch (IOException e) {
            Wurstplus.send_minecraft_log("Something has gone wrong while loading settings");
            Wurstplus.send_minecraft_log(e.toString());
        }
    }

    public boolean delete_file(String path) throws IOException {
        File f = new File(path);
        return f.delete();
    }

    public void verify_file(Path path) throws IOException {
        if (!Files.exists(path, new LinkOption[0])) {
            Files.createFile(path, new FileAttribute[0]);
        }
    }

    public void verify_dir(Path path) throws IOException {
        if (!Files.exists(path, new LinkOption[0])) {
            Files.createDirectory(path, new FileAttribute[0]);
        }
    }
}

