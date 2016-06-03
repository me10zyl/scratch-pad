package logic;

import ui.Tabs;
import ui.Tab;

import java.io.*;
import java.util.*;

/**
 * Created by ZyL on 2016/6/3.
 */
public class LocalStorage {

    public static void init(Tabs tab) {
        File f = new File(getStorageDir());
        int maxIndex = 0;
        File[] files = f.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith(".html")) {
                    return true;
                }
                return false;
            }
        });
        for (File file : files) {
            String name = file.getName();
            if (name.length() >= 6) {
                String indexStr = name.substring(name.length() - 6, name.length() - 5);
                if (indexStr.matches("\\d+")) {
                    int index = Integer.parseInt(indexStr);
                    if (index > maxIndex) {
                        maxIndex = index;
                        IndexManager.setIndex(maxIndex);
                    }
                }
            }
            Tab tabContent = new Tab(file, file.getName().replaceAll("\\..+", ""));
            tab.add(tabContent);
            tab.setSelectedComponent(tabContent);
        }
        String selectedIndex = getMeta("selectedIndex");
        if (selectedIndex != null) {
            if (Integer.parseInt(selectedIndex) < tab.getTabCount()) {
                tab.setSelectedIndex(Integer.parseInt(selectedIndex));
            }
        }
    }

    public static String getMeta(String key) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(getStorageDir() + "/meta"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props.getProperty(key);
    }

    public static void saveMeta(Map<String, Object> map) {
        Properties props = new Properties();
        Set<String> keySet = map.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            props.put(next, map.get(next));
        }
        try {
            props.store(new FileOutputStream(getStorageDir() + "/meta"), new Date().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String load(String tabName) {
        File f = new File(getStorageDir() + "/" + tabName + ".html");
        return load(f);
    }

    public static String load(File f) {
        BufferedReader br = null;
        String str = "";
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf-8"));
            String tmp = null;
            while ((tmp = br.readLine()) != null) {
                str += tmp;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return str;
    }

    public static void save(Tabs tab) {
        int count = tab.getTabCount();
        for (int i = 0; i < count; i++) {
            Tab content = (Tab) tab.getComponentAt(i);
            String text = content.getEditorPane().getText();
            save(content.getName() + ".html", text);
        }
    }

    public static String getStorageDir() {
        Object object = new Object();
        String dir = object.getClass().getResource("/files").getPath();
        return dir;
    }

    public static String saveMd(String tabName, String content) {
        System.out.println(tabName);
        save(tabName + ".md", content);
        return save(tabName + ".html", Markdown.convert(content));
    }

    public static String save(String tabName, String content) {
        FileOutputStream fos = null;
        BufferedWriter bw = null;

        String path = null;
        try {
            String dir = getStorageDir();
            path = dir + "/" + tabName;
            fos = new FileOutputStream(new File(path));
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(content);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return path;
    }
}
