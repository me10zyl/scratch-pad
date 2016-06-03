package logic;

import ui.Tab;
import ui.TabContent;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;

/**
 * Created by ZyL on 2016/6/3.
 */
public class LocalStorage {

    public static void init(Tab tab){
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
        for(File file : files){
            String name = file.getName();
            if(name.length() >= 6){
                String indexStr = name.substring(name.length() - 6, name.length() - 5);
                if(indexStr.matches("\\d+")){
                    int index = Integer.parseInt(indexStr);
                    if(index > maxIndex){
                        maxIndex = index;
                        IndexManager.setIndex(maxIndex + 1);
                    }
                }
            }
            try {
                TabContent tabContent = new TabContent(file.toURL(), file.getName().replaceAll("\\..+", ""));
                tab.add(tabContent);
                tab.setSelectedComponent(tabContent);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public static String load(String tabName){
        File f = new File(getStorageDir() + "/" + tabName + ".html");
        return load(f);
    }

    public static String load(File f){
        BufferedReader br = null;
        String str = "";
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String tmp = null;
            while((str = br.readLine()) != null){
                str += tmp;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return str;
    }

    public static void save(Tab tab){
        int count = tab.getTabCount();
        for(int i = 0; i < count;i ++){
            TabContent content = (TabContent) tab.getComponentAt(i);
            save(content.getName() + ".html", content.getEditorPane().getText());
        }
    }

    public static String getStorageDir(){
        Object object = new Object();
        String dir = object.getClass().getResource("/files").getPath();
        return dir;
    }

    public static String saveMd(String tabName, String content) {
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
