package com.proizvo.editor.remote;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeMap;

import javax.tools.ToolProvider;

import com.google.gson.JsonObject;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import oshi.software.os.OperatingSystemVersion;
import oshi.util.FormatUtil;

import static com.proizvo.editor.util.Maps.getOrOther;

class EnterHelper {

    static JsonObject toJsonObject(Map<?, ?> raw) {
        Map<?, ?> map = new TreeMap<>(raw);
        JsonObject json = new JsonObject();
        for (Entry<?, ?> e : map.entrySet()) {
            String[] keys = (e.getKey() + "").split("\\.");
            String val = e.getValue() + "";
            JsonObject current = json;
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                JsonObject child = current.has(key) ? current.getAsJsonObject(key) : new JsonObject();
                if (i == keys.length - 1) {
                    current.addProperty(key, val);
                } else {
                    current.add(key, child);
                }
                current = child;
            }
        }
        return json;
    }

    static Properties getSystemInfo() {
        // Inputs...
        Map<String, String> env = System.getenv();
        Properties prp = System.getProperties();
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        OperatingSystemVersion ver = os.getVersion();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor cpu = hal.getProcessor();
        GlobalMemory mem = hal.getMemory();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        Version clv = new Version();
        // Extract useful information
        Properties map = new Properties();
        map.put("user.name", getOrOther(prp, "user.name", getOrOther(env, "USER", env.get("LOGNAME"))));
        map.put("user.dir.home", getOrOther(prp, "user.home", env.get("HOME")));
        map.put("vm.vendor", prp.get("java.vm.vendor"));
        map.put("vm.name", prp.get("java.vm.name"));
        map.put("vm.version", prp.get("java.vm.version"));
        map.put("user.locale.country",
                getNiceCountry(getOrOther(prp, "user.country.format", prp.get("user.country"))));
        map.put("user.dir.work", prp.get("user.dir"));
        map.put("java.version", prp.get("java.runtime.version"));
        map.put("os.vendor", os.getManufacturer());
        map.put("os.name", getOrOther(prp, "os.name", os.getFamily()));
        map.put("os.arch", getOrOther(prp, "os.arch", prp.get("sun.arch.data.model")));
        map.put("os.version.short", getOrOther(prp, "os.version", ver.getVersion()));
        map.put("user.encoding", getOrOther(prp, "file.encoding", prp.get("sun.jnu.encoding")));
        map.put("java.jre", ToolProvider.getSystemJavaCompiler() == null);
        map.put("java.spec", prp.get("java.specification.version"));
        map.put("user.locale.lang", getNiceLang(getOrOther(prp, "user.language.format", prp.get("user.language"))));
        map.put("cpu.info.endian", prp.get("sun.cpu.endian"));
        map.put("os.version.build", ver.getBuildNumber());
        map.put("os.version.code", ver.getCodeName());
        map.put("cpu.count.logical", cpu.getLogicalProcessorCount());
        map.put("cpu.count.physical", cpu.getPhysicalProcessorCount());
        map.put("cpu.vendor", cpu.getVendor());
        map.put("cpu.name", cpu.getName());
        map.put("cpu.info.serial", cpu.getSystemSerialNumber());
        map.put("mem.total", FormatUtil.formatBytes(mem.getTotal()));
        map.put("mem.free", FormatUtil.formatBytes(mem.getAvailable()));
        map.put("glx.count", hal.getDisplays().length);
        map.put("glx.width", dim.getWidth());
        map.put("glx.height", dim.getHeight());
        map.put("client.id", clv.getGroupId() + "." + clv.getArtifactId());
        map.put("client.version", clv.getVersion());
        map.put("client.branch", clv.getScmBranch());
        map.put("client.rev", clv.getScmRevision());
        map.put("client.date", clv.getBuildDate());
        return map;
    }

    private static String getNiceLang(Object lang) {
        Locale locale = new Locale(lang + "", "US");
        return locale.getDisplayLanguage();
    }

    private static String getNiceCountry(Object country) {
        Locale locale = new Locale("en", country + "");
        return locale.getDisplayCountry();
    }
}
